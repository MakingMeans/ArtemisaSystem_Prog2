package co.edu.unbosque.model.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import co.edu.unbosque.model.Category;


public class CategoryDAO implements CRUDOperation<Category>{
	public EntityManagerFactory emf;
	public EntityManager em;
	
	public CategoryDAO() {
		emf = Persistence.createEntityManagerFactory("defualt");
		em = emf.createEntityManager();
	}
	
	public void open() {
		if (!emf.isOpen() || !em.isOpen()) {
			emf = Persistence.createEntityManagerFactory("default");
			em = emf.createEntityManager();
		}
	}
	
	@Override
	public void create(Category obj) {
		try {
			open();
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
		
	}
	@Override
	public boolean delete(long id) {
		open();
		try {
			// Delete
			em.getTransaction().begin();
			em.remove(em.find(Category.class, id));
			em.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
		return false;
	}
	@Override
	public boolean update(long id, Category obj) {
		open();
		try {
			// Update
			em.getTransaction().begin();
			Category selected = em.find(Category.class, id);

			selected.setId(obj.getId());
			selected.setSubjects(obj.getSubjects());
			selected.setTitle(obj.getTitle());
		
			em.persist(selected);
			em.getTransaction().commit();
			return true;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Category> readAll() {
		open();
		try {
			// usando JPQL
			return (ArrayList<Category>) em.createQuery("select p from User p").getResultList();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
		return new ArrayList<Category>();

	}
	@Override
	public Category findOne(long id) {
		open();
		try {
			// Find
			Category selectedCategory = em.find(Category.class, id);
			return selectedCategory;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
		return null;
	}
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
