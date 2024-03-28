package co.edu.unbosque.model;

public class CodeDTO {
	
	private long id;
	private String language;
	private String content;
	
	public CodeDTO() {
		// TODO Auto-generated constructor stub
	}

	public CodeDTO(long id, String language, String content) {
		super();
		this.id = id;
		this.language = language;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CodeDTO [id=" + id + ", language=" + language + ", content=" + content + "]";
	}
}
