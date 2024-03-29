package co.edu.unbosque.model;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="category")
public class Category {
	/**
     * Identificador único de la categoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    /**
     * Lista de temas asociados a la categoría.
     */
    @Column(name="tema", nullable = false)
    private ArrayList<SubjectDTO> subjects;
    
    /**
     * Título de la categoría.
     */
    @Column(name="titulo", nullable = false)
    private String title;

    /**
     * Constructor por defecto de la categoría.
     */
    public Category() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor de la categoría con parámetros.
     */
    public Category(long id, ArrayList<SubjectDTO> subjects, String title) {
        super();
        this.id = id;
        this.subjects = subjects;
        this.title = title;
    }

    /**
     * Obtiene el identificador de la categoría.
     */
    public long getId() {
        return id;
    }

    /**
     * Establece el identificador de la categoría.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtiene la lista de temas asociados a la categoría.
     */
    public ArrayList<SubjectDTO> getSubjects() {
        return subjects;
    }

    /**
     * Establece la lista de temas asociados a la categoría
     */
    public void setSubjects(ArrayList<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    /**
     * Obtiene el título de la categoría.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título de la categoría.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Método toString para representar la categoría como una cadena de texto.
     */
    @Override
    public String toString() {
        return "Category [id=" + id + ", subjects=" + subjects + ", title=" + title + "]";
    }

}
