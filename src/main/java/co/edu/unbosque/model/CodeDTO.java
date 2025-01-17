package co.edu.unbosque.model;

/**
 * 
 * Data Transfer Object de Code.
 * 
 * @author WilmerR
 *
 */
public class CodeDTO {
	
	 /**
     * Identificador único del código.
     */
    private long id;
    
    /**
     * Lenguaje de programación del código.
     */
    private String language;
    
    /**
     * Contenido del código.
     */
    private String content;
    
    /**
     * Tema al que pertenece el codigo.
     */
    private String subjectOf;	
    
    /**
     * Constructor por defecto de la clase CodeDTO.
     */
    public CodeDTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor de la clase CodeDTO con parámetros.
     */
    public CodeDTO(long id, String language, String content, String subjectOf) {
		super();
		this.id = id;
		this.language = language;
		this.content = content;
		this.subjectOf = subjectOf;
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
     * Obtiene el tema del código.
     */
    public String getSubjectOf() {
		return subjectOf;
	}
    
    /**
     * Establece el tema del código.
     */
	public void setSubjectOf(String subjectOf) {
		this.subjectOf = subjectOf;
	}

    /**
     * Método toString para representar el código como una cadena de texto.
     */
	@Override
	public String toString() {
		return "CodeDTO [id=" + id + ", language=" + language + ", content=" + content + ", subjectOf=" + subjectOf
				+ "]";
	}
}
