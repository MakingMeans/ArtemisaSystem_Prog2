package co.edu.unbosque.model;

public class UserDTO{
	/**
     * Identificador único del usuario.
     */
    private long id;
    
    /**
     * Nombre de usuario.
     */
    private String username;
    
    /**
     * Contraseña del usuario.
     */
    private String password;
    
    /**
     * Dirección de correo electrónico del usuario.
     */
    private String email;
    
    /**
     * Semestre del usuario.
     */
    private int semester;
    
    /**
     * Carrera del usuario.
     */
    private String career;
    
    /**
     * Indicador de si el usuario es administrador o no.
     */
    private boolean hasAdmin;
    
    /**
     * Constructor por defecto de la clase UserDTO.
     */
    public UserDTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor de la clase UserDTO con parámetros.
     */
    public UserDTO(long id, String username, String password, String email, int semester, String career,
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
     * Obtiene el nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
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
     * Obtiene la dirección de correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece la dirección de correo electrónico del usuario.
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
     * Verifica si el usuario es administrador.
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
        return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
                + ", semester=" + semester + ", career=" + career + ", hasAdmin=" + hasAdmin + "]";
    }
}
