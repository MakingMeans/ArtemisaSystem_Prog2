package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;

import co.edu.unbosque.model.CategoryDTO;
import co.edu.unbosque.service.CategoryService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("categoryBean")
@SessionScoped
public class CategoryBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
    private CategoryService categoryService;

    /**
     * Lista de categorias mostradas en la tabla.
     */
    private List<CategoryDTO> categoriesInTable;

    /**
     * Categoria seleccionada actualmente.
     */
    private CategoryDTO selectedCategories;

    /**
     * Lista de categorias seleccionadas.
     */
    private List<CategoryDTO> selectedCategoriesVarious;

    /**
     * Inicializa el bean. Carga la lista de categorias al iniciar la sesión.
     */
    @PostConstruct
    public void init() {
        this.categoriesInTable = new ArrayList<>();
        this.categoriesInTable = categoryService.getCategories();
        this.selectedCategoriesVarious = new ArrayList<CategoryDTO>();
    }

    /**
     * Abre un nuevo formulario para agregar una categoria.
     */
    public void openNew() {
        this.selectedCategories = new CategoryDTO();
    }

    /**
     * Guarda una categoria en la base de datos.
     */
    public void saveCategory() {
        if (this.selectedCategories.getId() == 0) {
            this.selectedCategories.setId(0);
            categoryService.create(selectedCategories);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria agregada"));
        } else {
            categoryService.update(selectedCategories.getId(), selectedCategories);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria actualizada"));
        }
        this.categoriesInTable = categoryService.getCategories();
        PrimeFaces.current().executeScript("PF('manageCodeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
    }

    /**
     * Elimina una categoria seleccionada.
     */
    public void deleteCategory() {
        categoryService.delete(this.selectedCategories.getId());
        this.selectedCategoriesVarious.remove(this.selectedCategories);
        this.selectedCategories = null;
        this.categoriesInTable = categoryService.getCategories();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }

    /**
     * Obtiene el mensaje para el botón de eliminar.
     */
    public String getDeleteButtonMessage() {
        if (hasSelectedCategories()) {
            int size = this.selectedCategoriesVarious.size();
            return size > 1 ? size + " categorias seleccionadas" : "1 categoria seleccionada";
        }
        return "Eliminada";
    }

    /**
     * Verifica si hay categorias seleccionadas.
     */
    public boolean hasSelectedCategories() {
        return this.selectedCategoriesVarious != null && !this.selectedCategoriesVarious.isEmpty();
    }

    /**
     * Elimina varias categorias seleccionados.
     */
    public void deleteSelectedCategories() {
        for (CategoryDTO p : selectedCategoriesVarious) {
            categoryService.delete(p.getId());
        }
        this.selectedCategoriesVarious = null;
        this.categoriesInTable = categoryService.getCategories();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorias eliminadas"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }

	public List<CategoryDTO> getCategories() {
		return categoriesInTable;
	}

	public void setCategories(List<CategoryDTO> categoriesInTable) {
		this.categoriesInTable = categoriesInTable;
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategories;
	}

	public void setSelectedCategory(CategoryDTO selectedCategories) {
		this.selectedCategories = selectedCategories;
	}

	public List<CategoryDTO> getSelectedCategories() {
		return selectedCategoriesVarious;
	}

	public void setSelectedCategories(List<CategoryDTO> selectedCategoriesVarious) {
		this.selectedCategoriesVarious = selectedCategoriesVarious;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
