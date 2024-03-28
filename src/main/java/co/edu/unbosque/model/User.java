package co.edu.unbosque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Nombre de usuario", nullable=false)
	private String username;
	@Column(name = "Contraseña", nullable=false)
	private String password;
	@Column(name = "Email", nullable=false)
	private String email;
	@Column(name = "Semestre", nullable=false)
	private int semester;
	@Column(name = "Carrera", nullable=false)
	private String career;
	@Column(name = "EsAdmin", nullable=false)
	private boolean hasAdmin;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public boolean isHasAdmin() {
		return hasAdmin;
	}

	public void setHasAdmin(boolean hasAdmin) {
		this.hasAdmin = hasAdmin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", semester=" + semester + ", career=" + career + ", hasAdmin=" + hasAdmin + "]";
	}
}
