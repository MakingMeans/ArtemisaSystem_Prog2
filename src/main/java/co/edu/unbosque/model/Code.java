package co.edu.unbosque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="code")
public class Code {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Lenguaje De Programacion", nullable=false)
	private String language;
	@Column(name = "Contenido", nullable=false)
	private String content;
	
	public Code() {
		// TODO Auto-generated constructor stub
	}

	public Code(long id, String language, String content) {
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
		return "Code [id=" + id + ", language=" + language + ", content=" + content + "]";
	}
}
