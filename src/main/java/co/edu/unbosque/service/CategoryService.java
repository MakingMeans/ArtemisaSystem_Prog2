package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.Category;
import co.edu.unbosque.model.CategoryDTO;
import co.edu.unbosque.model.persistence.CategoryDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class CategoryService implements ServiceOperation<CategoryDTO>{

	private List<CategoryDTO> categories;
	private CategoryDAO categoryDAO = new CategoryDAO();

	@PostConstruct
	public void init() {
		categoryDAO = new CategoryDAO();
		categories = new ArrayList<>();
		categories = readAll();
	}

	public CategoryDTO toDto(Category category) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(category.getId());
		dto.setSubjects(category.getSubjects());
		dto.setTitle(category.getTitle());
		return dto;
	}

	// mappeando de entity a dto
	public Category toEntity(CategoryDTO categoryDTO) {
		Category entity = new Category();
		entity.setId(categoryDTO.getId());
		entity.setSubjects(categoryDTO.getSubjects());
		entity.setTitle(categoryDTO.getTitle());
		return entity;
	}

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

	@Override
	public void create(CategoryDTO obj) {
		categoryDAO.create(toEntity(obj));
		categories=readAll();

	}

	@Override
	public boolean delete(long id) {
		boolean result = categoryDAO.delete(id);
		categories=readAll();
		return result;
	}

	@Override
	public boolean update(long id, CategoryDTO obj) {
		boolean result = categoryDAO.update(id, toEntity(obj));
		categories=readAll();
		return result;
	}

	@Override
	public List<CategoryDTO> readAll() {
		categories.clear();
		ArrayList<Category> entities = categoryDAO.readAll();
		for (Category category : entities) {
			categories.add(toDto(category));
		}
		return categories;
	}

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
