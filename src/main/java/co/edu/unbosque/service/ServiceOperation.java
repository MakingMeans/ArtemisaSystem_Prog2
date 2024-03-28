package co.edu.unbosque.service;

import java.util.List;

public interface ServiceOperation<E> {

	public void create(E obj);

	public boolean delete(long id);

	public boolean update(long id, E obj);

	public List<E> readAll();

	public E findOne(long id);

}
