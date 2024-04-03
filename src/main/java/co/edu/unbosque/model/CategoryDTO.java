package co.edu.unbosque.model;

/**
 * 
 * Data Transfer Object de Category.
 * 
 * @author SantiagoR
 *
 */
public class CategoryDTO {
	
	 /**
     * Identificador único de la categoría.
     */
    private long id;
    
    /**
     * Título de la categoría.
     */
    private String title;
    
    /**
     * Constructor por defecto de la clase CategoryDTO.
     */
    public CategoryDTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor de la clase CategoryDTO con parámetros.
     */
    public CategoryDTO(long id, String title) {
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
        return "CategoryDTO [id=" + id + ", title=" + title + "]";
    }

}
