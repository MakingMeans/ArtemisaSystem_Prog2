package co.edu.unbosque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * Entidad de Code.
 * 
 * @author WilmerR
 *
 */
@Entity
@Table(name="code")
public class Code {
	 /**
     * Identificador único del código.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    /**
     * Lenguaje de programación del código.
     */
    @Column(name = "lenguajeProgramacion", nullable=false)
    private String language;
    
    /**
     * Contenido del código.
     */
    @Column(name = "contenido", nullable=false)
    private String content;
    
    /**
     * Constructor por defecto de la clase Code.
     */
    public Code() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Constructor de la clase Code con parámetros.
     */
    public Code(long id, String language, String content) {
        super();
        this.id = id;
        this.language = language;
        this.content = content;
    }
    
    /**
     * Obtiene el identificador del código.
     */
    public long getId() {
        return id;
    }
    
    /**
     * Establece el identificador del código.
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Obtiene el lenguaje de programación del código.
     */
    public String getLanguage() {
        return language;
    }
    
    /**
     * Establece el lenguaje de programación del código.
     */
    public void setLanguage(String language) {
        this.language = language;
    }
    
    /**
     * Obtiene el contenido del código.
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Establece el contenido del código.
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Método toString para representar el código como una cadena de texto.
     */
    @Override
    public String toString() {
        return "Code [id=" + id + ", language=" + language + ", content=" + content + "]";
    }
}
