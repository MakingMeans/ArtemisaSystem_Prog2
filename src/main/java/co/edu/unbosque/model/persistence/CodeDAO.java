package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.model.Code;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * 
 * Data Access Object de Code.
 * 
 * @author WilmerR
 *
 */
public class CodeDAO implements CRUDOperation<Code> {

	public EntityManagerFactory emf;
	public EntityManager em;
	
	/**
	 * Constructor que inicializa la lista de objetos CodeDAO.
	 */
	public CodeDAO() {
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
	 * Crea un nuevo código en la base de datos.
	 * @param obj El objeto Code que representa el código a crear.
	 */
	@Override
	public void create(Code obj) {

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
	 * Elimina un código de la base de datos según su ID.
	 * @param id El ID del código que se va a eliminar.
	 * @return true si la eliminación se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean delete(long id) {
		open();
		try {
			// Delete
			em.getTransaction().begin();
			em.remove(em.find(Code.class, id));
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
	 * Actualiza los datos de un código en la base de datos.
	 * @param id El ID del código que se va a actualizar.
	 * @param obj El objeto Code con los nuevos datos.
	 * @return true si la actualización se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean update(long id, Code obj) {
		open();
		try {
			// Update
			em.getTransaction().begin();
			Code selected = em.find(Code.class, id);

			selected.setId(obj.getId());
			selected.setLanguage(obj.getLanguage());
			selected.setContent(obj.getContent());
		
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
	 * Lee todos los códigos almacenados en la base de datos.
	 * @return Una lista de todos los códigos almacenados.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Code> readAll() {
		open();
		try {
			// usando JPQL
			return (ArrayList<Code>) em.createQuery("select p from Code p").getResultList();
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
		return new ArrayList<Code>();

	}
	
	/**
	 * Busca y devuelve un código específico de la base de datos según su ID.
	 * @param id El ID del código que se va a buscar.
	 * @return El código encontrado, o null si no se encuentra ningún código con ese ID.
	 */
	@Override
	public Code findOne(long id) {
		open();
		try {
			// Find
			Code selectedCode = em.find(Code.class, id);
			return selectedCode;
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
	 * Devuelve el número total de códigos almacenados en la base de datos.
	 * @return El número total de códigos almacenados.
	 */
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
