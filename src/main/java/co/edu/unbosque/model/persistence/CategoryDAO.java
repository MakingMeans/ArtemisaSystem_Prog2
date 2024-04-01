package co.edu.unbosque.model.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import co.edu.unbosque.model.Category;

/**
 * 
 * Data Access Object de Category.
 * 
 * @author SantiagoR
 *
 */
public class CategoryDAO implements CRUDOperation<Category>{
	public EntityManagerFactory emf;
	public EntityManager em;
	
	/**
	 * Constructor que inicializa la lista de objetos CategoryDAO.
	 */
	public CategoryDAO() {
		emf = Persistence.createEntityManagerFactory("defualt");
		em = emf.createEntityManager();
	}
	/**
	 * Abre una conexión con la base de datos si aún no está abierta.
	 * Si ya está abierta, no realiza ninguna acción.
	 */
	public void open() {
		if (!emf.isOpen() || !em.isOpen()) {
			emf = Persistence.createEntityManagerFactory("default");
			em = emf.createEntityManager();
		}
	}
	
	/**
	 * Crea una nueva categoría en la base de datos.
	 * @param obj La categoría que se va a crear.
	 */
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
	/**
	 * Elimina una categoría de la base de datos según su ID.
	 * @param id El ID de la categoría que se va a eliminar.
	 * @return true si la eliminación se realizó correctamente, false de lo contrario.
	 */
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
	
	/**
	 * Actualiza los datos de una categoría en la base de datos.
	 * @param id El ID de la categoría que se va a actualizar.
	 * @param obj La categoría con los nuevos datos.
	 * @return true si la actualización se realizó correctamente, false de lo contrario.
	 */
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
	
	/**
	 * Lee todas las categorías almacenadas en la base de datos.
	 * @return Una lista de todas las categorías almacenadas.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Category> readAll() {
		open();
		try {
			// usando JPQL
			return (ArrayList<Category>) em.createQuery("select p from Category p").getResultList();
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
	
	/**
	 * Busca y devuelve una categoría específica de la base de datos según su ID.
	 * @param id El ID de la categoría que se va a buscar.
	 * @return La categoría encontrada, o null si no se encuentra ninguna categoría con ese ID.
	 */
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
	
	/**
	 * Devuelve el número total de categorías almacenadas en la base de datos.
	 * @return El número total de categorías almacenadas.
	 */
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
