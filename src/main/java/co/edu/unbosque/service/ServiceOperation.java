package co.edu.unbosque.service;

import java.util.List;

/**
 * Esta interfaz define operaciones básicas de servicio (Crear, Leer, Actualizar, Eliminar)
 * para un tipo genérico de objeto.
 *
 * @param <E> El tipo de objeto para el cual se definen las operaciones de servicio.
 * @author DavidG
 */
public interface ServiceOperation<E> {

    /**
     * Crea un nuevo objeto del tipo E.
     *
     * @param obj El objeto que se va a crear.
     */
    public void create(E obj);

    /**
     * Elimina un objeto con el ID especificado.
     *
     * @param id El ID del objeto que se va a eliminar.
     * @return Verdadero si la eliminación es exitosa, falso en caso contrario.
     */
    public boolean delete(long id);

    /**
     * Actualiza un objeto con el ID especificado.
     *
     * @param id  El ID del objeto que se va a actualizar.
     * @param obj Los nuevos datos del objeto a actualizar.
     * @return Verdadero si la actualización es exitosa, falso en caso contrario.
     */
    public boolean update(long id, E obj);

    /**
     * Recupera todos los objetos del tipo E.
     *
     * @return Una lista que contiene todos los objetos del tipo E.
     */
    public List<E> readAll();

    /**
     * Recupera un objeto del tipo E con el ID especificado.
     *
     * @param id El ID del objeto a recuperar.
     * @return El objeto con el ID especificado, o null si no se encuentra.
     */
    public E findOne(long id);
}
