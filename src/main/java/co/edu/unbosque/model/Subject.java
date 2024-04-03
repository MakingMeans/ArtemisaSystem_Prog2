package co.edu.unbosque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

/**
 * 
 * Entidad de Subject.
 * 
 * @author DavidG
 *
 */
@Entity
@Table(name="subject")
public class Subject{
	/**
     * Identificador único del tema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    /**
     * Nombre del tema.
     */
    @Column(name = "tema", nullable=false)
    private String name;
    
    /**
     * Descripción del tema.
     */
    @Lob
    @Column(name = "descripcion", nullable=false)
    private String desc;
    
    /**
     * Dificultad Numerica del Tema.
     */
    @Column(name = "dificultad", nullable=false)
    private int diff;
    
    /**
     * Categoria a la que pertenece.
     */
    @Column(name = "categoria", nullable=false)
    private String categoryOf;
    
    /**
     * Constructor por defecto de la clase Subject.
     */
    public Subject() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Constructor de la clase Subject con parámetros.
     */
    public Subject(long id, String name, String desc, int diff, String categoryOf) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.diff = diff;
		this.categoryOf = categoryOf;
	}

    /**
     * Obtiene el identificador del tema.
     */
    public long getId() {
        return id;
    }

	/**
     * Establece el identificador del tema.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del tema.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del tema.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la descripción del tema.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Establece la descripción del tema.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /**
     * Obtiene la dificultad del tema.
     */
    public int getDiff() {
		return diff;
	}

    /**
     * Establece la dificultad del tema.
     */
	public void setDiff(int diff) {
		this.diff = diff;
	}

	/**
     * Obtiene la categoria del tema.
     */
	public String getCategoryOf() {
		return categoryOf;
	}

	/**
     * Establece la categoria del tema.
     */
	public void setCategoryOf(String categoryOf) {
		this.categoryOf = categoryOf;
	}

	/**
     * Método toString para representar el tema como una cadena de texto.
     */
    @Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", desc=" + desc + ", diff=" + diff + ", categoryOf="
				+ categoryOf + "]";
	}
}

