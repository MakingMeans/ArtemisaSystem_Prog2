package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.model.Subject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/**
 * 
 * Data Access Object de SubjectDTO.
 * 
 * @author DavidG
 *
 */
public class SubjectDAO implements CRUDOperation<Subject> {

	public EntityManagerFactory emf;
	public EntityManager em;

	/**
	 * Constructor que inicializa la lista de objetos SubjectDAO.
	 */
	public SubjectDAO() {
		emf = Persistence.createEntityManagerFactory("default");
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
	 * Crea un nuevo tema en la base de datos.
	 * @param obj El objeto Subject que representa el tema a crear.
	 */
	@Override
	public void create(Subject obj) {

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
	 * Elimina un tema de la base de datos según su ID.
	 * @param id El ID del tema que se va a eliminar.
	 * @return true si la eliminación se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean delete(long id) {
		open();
		try {
			// Delete
			em.getTransaction().begin();
			em.remove(em.find(Subject.class, id));
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
	 * Actualiza los datos de un tema en la base de datos.
	 * @param id El ID del tema que se va a actualizar.
	 * @param obj El objeto Subject con los nuevos datos.
	 * @return true si la actualización se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean update(long id, Subject obj) {
		open();
		try {
			// Update
			em.getTransaction().begin();
			Subject selected = em.find(Subject.class, id);

			selected.setId(obj.getId());
			selected.setName(obj.getName());
			selected.setDesc(obj.getDesc());
			selected.setCodes(obj.getCodes());
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
	 * Lee todos los temas almacenados en la base de datos.
	 * @return Una lista de todos los temas almacenadas.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Subject> readAll() {
		open();
		try {
			// usando JPQL
			return (ArrayList<Subject>) em.createQuery("select p from Subject p").getResultList();
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
		return new ArrayList<Subject>();

	}
	
	/**
	 * Busca y devuelve un tema específica de la base de datos según su ID.
	 * @param id El ID de la tema que se va a buscar.
	 * @return El tema encontrado, o null si no se encuentra ningun tema con ese ID.
	 */
	@Override
	public Subject findOne(long id) {
		open();
		try {
			// Find
			Subject selected = em.find(Subject.class, id);
			return selected;
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
	 * Devuelve el número total de temas almacenados en la base de datos.
	 * @return El número total de temas almacenados.
	 */
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
