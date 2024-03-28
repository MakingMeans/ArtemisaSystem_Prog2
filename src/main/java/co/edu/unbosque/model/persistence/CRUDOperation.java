package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

public interface CRUDOperation<E> {

	public void create(E obj);

	public boolean delete(long id);

	public boolean update(long id, E obj);

	public ArrayList<E> readAll();

	public E findOne(long id);

	public int count();
}
