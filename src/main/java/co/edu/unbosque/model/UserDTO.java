package co.edu.unbosque.model;

public class UserDTO{
	private long id;
	private String username;
	private String password;
	private String email;
	private int semester;
	private String career;
	private boolean hasAdmin;
	
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

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
		return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", semester=" + semester + ", career=" + career + ", hasAdmin=" + hasAdmin + "]";
	}
}
