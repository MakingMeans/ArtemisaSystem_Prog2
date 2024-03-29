package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * 
 * Data Access Object de User.
 * 
 * @author SantiagoR
 *
 */
public class UserDAO implements CRUDOperation<User> {

	public EntityManagerFactory emf;
	public EntityManager em;

	public UserDAO() {
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
	 * Crea un nuevo usuario en la base de datos.
	 * @param obj El objeto User que representa el usuario a crear.
	 */
	@Override
	public void create(User obj) {

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
	 * Elimina un usuario de la base de datos según su ID.
	 * @param id El ID del usuario que se va a eliminar.
	 * @return true si la eliminación se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean delete(long id) {
		open();
		try {
			// Delete
			em.getTransaction().begin();
			em.remove(em.find(User.class, id));
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
	 * Actualiza los datos de un usuario en la base de datos.
	 * @param id El ID del usuario que se va a actualizar.
	 * @param obj El objeto User con los nuevos datos.
	 * @return true si la actualización se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean update(long id, User obj) {
		open();
		try {
			// Update
			em.getTransaction().begin();
			User selected = em.find(User.class, id);

			selected.setId(obj.getId());
			selected.setUsername(obj.getUsername());
			selected.setPassword(obj.getPassword());
			selected.setEmail(obj.getEmail());
			selected.setSemester(obj.getSemester());
			selected.setCareer(obj.getCareer());
			selected.setHasAdmin(obj.isHasAdmin());
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
	 * Lee todos los usuarios almacenados en la base de datos.
	 * @return Una lista de todos los usuarios almacenados.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> readAll() {
		open();
		try {
			// usando JPQL
			return (ArrayList<User>) em.createQuery("select p from User p").getResultList();
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
		return new ArrayList<User>();

	}

	/**
	 * Busca y devuelve un usuario específico de la base de datos según su ID.
	 * @param id El ID del usuario que se va a buscar.
	 * @return El usuario encontrado, o null si no se encuentra ningún usuario con ese ID.
	 */
	@Override
	public User findOne(long id) {
		open();
		try {
			// Find
			User selected = em.find(User.class, id);
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
	 * Devuelve el número total de usuarios almacenados en la base de datos.
	 * @return El número total de usuarios almacenados.
	 */
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
