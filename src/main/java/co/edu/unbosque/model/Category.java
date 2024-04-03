package co.edu.unbosque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * Entidad de Category.
 * 
 * @author SantiagoR
 *
 */
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
    public Category(long id, String title) {
        super();
        this.id = id;
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
        return "Category [id=" + id + ", title=" + title + "]";
    }

}
