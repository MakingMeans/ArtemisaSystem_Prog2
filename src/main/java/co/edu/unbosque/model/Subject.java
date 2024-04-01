package co.edu.unbosque.model;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Column(name = "descripcion", nullable=false)
    private String desc;
    
    /**
     * Lista de códigos asociados al tema.
     */
    @Column(name = "codigos", nullable=false)
    private ArrayList<CodeDTO> codes;
    
    /**
     * Constructor por defecto de la clase Subject.
     */
    public Subject() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Constructor de la clase Subject con parámetros.
     */
    public Subject(long id, String name, String desc, ArrayList<CodeDTO> codes) {
        super();
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.codes = codes;
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
     * Obtiene la lista de códigos asociados al tema.
     */
    public ArrayList<CodeDTO> getCodes() {
        return codes;
    }

    /**
     * Establece la lista de códigos asociados al tema.
     */
    public void setCodes(ArrayList<CodeDTO> codes) {
        this.codes = codes;
    }

    /**
     * Método toString para representar el tema como una cadena de texto.
     */
    @Override
    public String toString() {
        return "Subject [id=" + id + ", name=" + name + ", desc=" + desc + ", codes=" + codes + "]";
    }
}

