package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.Category;
import co.edu.unbosque.model.CategoryDTO;
import co.edu.unbosque.model.persistence.CategoryDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * 
 * Service de Category.
 * 
 * @author SantiagoR
 *
 */
@Named
@ApplicationScoped
public class CategoryService implements ServiceOperation<CategoryDTO>{

	private List<CategoryDTO> categories;
	private CategoryDAO categoryDAO = new CategoryDAO();

	/**
	 * Método de inicialización llamado después de que se haya creado una instancia de la clase CategoryDAO.
	 * Este método inicializa el DAO de categorías y carga todas las categorías desde la base de datos mediante 
	 * una funcion de CategoryDAO.
	 */
	@PostConstruct
	public void init() {
		categoryDAO = new CategoryDAO();
		categories = new ArrayList<>();
		categories = readAll();
	}
	
	/**
	 * Convierte un objeto de tipo Category a un objeto de tipo CategoryDTO.
	 * @param category El objeto de tipo Category a convertir.
	 * @return Un objeto de tipo CategoryDTO convertido.
	 */
	public CategoryDTO toDto(Category category) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(category.getId());
		dto.setSubjects(category.getSubjects());
		dto.setTitle(category.getTitle());
		return dto;
	}
	/**
	 * Convierte un objeto de tipo CategoryDTO a un objeto de tipo Category.
	 * @param categoryDTO El objeto de tipo CategoryDTO a convertir.
	 * @return Un objeto de tipo Category convertido.
	 */
	public Category toEntity(CategoryDTO categoryDTO) {
		Category entity = new Category();
		entity.setId(categoryDTO.getId());
		entity.setSubjects(categoryDTO.getSubjects());
		entity.setTitle(categoryDTO.getTitle());
		return entity;
	}

	/**
	 * Obtiene una lista de categorías con el tamaño especificado.
	 * Si el tamaño especificado es mayor que el tamaño actual de la lista de categorías, devuelve una lista truncada.
	 * @param size El tamaño de la lista de categorías deseado.
	 * @return Una lista de categorías con el tamaño especificado.
	 */
	public List<CategoryDTO> getCategories(int size) {

		if (size > categories.size()) {
			List<CategoryDTO> shortenedList = new ArrayList<>();
			for (int i = 0; i < categories.size() - 1; i++) {
				shortenedList.add(categories.get(i));
			}
			return shortenedList;
		}

		else {
			return new ArrayList<>(categories.subList(0, size));
		}

	}

	/**
	 * Crea una nueva categoría en la base de datos.
	 * @param obj El objeto CategoryDTO que representa la categoría a crear.
	 */
	@Override
	public void create(CategoryDTO obj) {
		categoryDAO.create(toEntity(obj));
		categories=readAll();

	}
	
	/**
	 * Elimina una categoría de la base de datos según su ID.
	 * @param id El ID de la categoría que se va a eliminar.
	 * @return true si la eliminación se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean delete(long id) {
		boolean result = categoryDAO.delete(id);
		categories=readAll();
		return result;
	}

	/**
	 * Actualiza los datos de una categoría en la base de datos.
	 * @param id El ID de la categoría que se va a actualizar.
	 * @param obj El objeto CategoryDTO con los nuevos datos.
	 * @return true si la actualización se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean update(long id, CategoryDTO obj) {
		boolean result = categoryDAO.update(id, toEntity(obj));
		categories=readAll();
		return result;
	}

	/**
	 * Lee todas las categorías almacenadas en la base de datos.
	 * @return Una lista de todas las categorías almacenadas.
	 */

	@Override
	public List<CategoryDTO> readAll() {
		categories.clear();
		ArrayList<Category> entities = categoryDAO.readAll();
		for (Category category : entities) {
			categories.add(toDto(category));
		}
		return categories;
	}
	/**
	 * Busca y devuelve una categoría específica de la base de datos según su ID.
	 * @param id El ID de la categoría que se va a buscar.
	 * @return La categoría encontrada, o null si no se encuentra ninguna categoría con ese ID.
	 */
	@Override
	public CategoryDTO findOne(long id) {
		CategoryDTO find = toDto(categoryDAO.findOne(id));
		return find;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
}
