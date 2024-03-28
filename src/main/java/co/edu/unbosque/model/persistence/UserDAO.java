package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UserDAO implements CRUDOperation<User> {

	public EntityManagerFactory emf;
	public EntityManager em;

	public UserDAO() {
		emf = Persistence.createEntityManagerFactory("default");
		em = emf.createEntityManager();
	}

	public void open() {
		if (!emf.isOpen() || !em.isOpen()) {
			emf = Persistence.createEntityManagerFactory("default");
			em = emf.createEntityManager();
		}
	}

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

	@Override
	public User findOne(long id) {
		open();
		try {
			// Find
			User selectedProductFruit = em.find(User.class, id);
			return selectedProductFruit;
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
