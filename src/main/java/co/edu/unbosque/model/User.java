package co.edu.unbosque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * Entidad de User.
 * 
 * @author DavidG
 *
 */
@Entity
@Table(name="userAccount")
public class User{
	/**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    /**
     * Nombre de usuario del usuario.
     */
    @Column(name = "nombreUsuario", nullable=false, unique = true)
    private String username;
    
    /**
     * Contraseña del usuario.
     */
    @Column(name = "contraseña", nullable=false)
    private String password;
    
    /**
     * Correo electrónico del usuario.
     */
    @Column(name = "email", nullable=false, unique = true)
    private String email;
    
    /**
     * Semestre del usuario.
     */
    @Column(name = "semestre", nullable=false)
    private int semester;
    
    /**
     * Carrera del usuario.
     */
    @Column(name = "carrera", nullable=false)
    private String career;
    
    /**
     * Indica si el usuario es administrador o no.
     */
    @Column(name = "esAdmin", nullable=false)
    private boolean hasAdmin;
    
    /**
     * Constructor por defecto de la clase User.
     */
    public User() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor de la clase User con parámetros.
     */
    public User(long id, String username, String password, String email, int semester, String career,
            boolean hasAdmin) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.semester = semester;
        this.career = career;
        this.hasAdmin = hasAdmin;
    }

    /**
     * Obtiene el identificador del usuario.
     */
    public long getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario del usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario del usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el semestre del usuario.
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Establece el semestre del usuario.
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * Obtiene la carrera del usuario.
     */
    public String getCareer() {
        return career;
    }

    /**
     * Establece la carrera del usuario.
     */
    public void setCareer(String career) {
        this.career = career;
    }

    /**
     * Indica si el usuario es administrador o no.
     */
    public boolean isHasAdmin() {
        return hasAdmin;
    }

    /**
     * Establece si el usuario es administrador o no.
     */
    public void setHasAdmin(boolean hasAdmin) {
        this.hasAdmin = hasAdmin;
    }

    /**
     * Método toString para representar el usuario como una cadena de texto.
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
                + ", semester=" + semester + ", career=" + career + ", hasAdmin=" + hasAdmin + "]";
    }
}
