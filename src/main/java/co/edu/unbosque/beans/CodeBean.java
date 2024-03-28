package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;

import co.edu.unbosque.model.CodeDTO;
import co.edu.unbosque.service.CodeService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

public class CodeBean implements Serializable {

	/**
	 * 
	 */
	@Named("codeBean")
	@SessionScoped
	private static final long serialVersionUID = 1L;
	
    @Inject
    private CodeService CodeService;

    /**
     * Lista de codigos mostrados en la tabla.
     */
    private List<CodeDTO> codesInTable;

    /**
     * Codigo seleccionado actualmente.
     */
    private CodeDTO selectedCodes;

    /**
     * Lista de codigos seleccionados.
     */
    private List<CodeDTO> selectedCodesVarious;

    /**
     * Inicializa el bean. Carga la lista de codigos al iniciar la sesión.
     */
    @PostConstruct
    public void init() {
        this.codesInTable = new ArrayList<>();
        this.codesInTable = CodeService.getCodes();
        this.selectedCodesVarious = new ArrayList<CodeDTO>();
    }

    /**
     * Abre un nuevo formulario para agregar un codigo.
     */
    public void openNew() {
        this.selectedCodes = new CodeDTO();
    }

    /**
     * Guarda un codigo en la base de datos.
     */
    public void saveCode() {
        if (this.selectedCodes.getId() == 0) {
            this.selectedCodes.setId(0);
            CodeService.create(selectedCodes);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigo agregado"));
        } else {
            CodeService.update(selectedCodes.getId(), selectedCodes);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigo actualizado"));
        }
        this.codesInTable = CodeService.getCodes();
        PrimeFaces.current().executeScript("PF('manageCodeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
    }

    /**
     * Elimina un codigo seleccionado.
     */
    public void deleteCode() {
        CodeService.delete(this.selectedCodes.getId());
        this.selectedCodesVarious.remove(this.selectedCodes);
        this.selectedCodes = null;
        this.codesInTable = CodeService.getCodes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigo eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }

    /**
     * Obtiene el mensaje para el botón de eliminar.
     */
    public String getDeleteButtonMessage() {
        if (hasSelectedCodes()) {
            int size = this.selectedCodesVarious.size();
            return size > 1 ? size + " codigos seleccionados" : "1 codigo seleccionado";
        }
        return "Eliminado";
    }

    /**
     * Verifica si hay codigos seleccionados.
     */
    public boolean hasSelectedCodes() {
        return this.selectedCodesVarious != null && !this.selectedCodesVarious.isEmpty();
    }

    /**
     * Elimina varios codigos seleccionados.
     */
    public void deleteSelectedCodes() {
        for (CodeDTO p : selectedCodesVarious) {
            CodeService.delete(p.getId());
        }
        this.selectedCodesVarious = null;
        this.codesInTable = CodeService.getCodes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigos eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }

    /**
     * Obtiene la lista de codigos.
     */
    public List<CodeDTO> getCodes() {
        return codesInTable;
    }

    /**
     * Establece la lista de codigos.
     */
    public void setCodes(List<CodeDTO> Codes) {
        this.codesInTable = Codes;
    }

    /**
     * Obtiene el codigo seleccionado.
     */
    public CodeDTO getSelectedCode() {
        return selectedCodes;
    }

    /**
     * Establece el codigo seleccionado.
     */
    public void setSelectedCode(CodeDTO selectedCode) {
        this.selectedCodes = selectedCode;
    }

    /**
     * Obtiene la lista de codigos seleccionados.
     */
    public List<CodeDTO> getSelectedCodes() {
        return selectedCodesVarious;
    }

    /**
     * Establece la lista de codigos seleccionados.
     */
    public void setSelectedCodes(List<CodeDTO> selectedCodes) {
        this.selectedCodesVarious = selectedCodes;
    }

    /**
     * Obtiene el serialVersionUID.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
	
	

}
