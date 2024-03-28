package co.edu.unbosque.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Code {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Tipo de Lenguaje", nullable=false)
	private String typeContent;
	@Column(name = "Contenido", nullable=false)
	private String content;
	
	public Code() {
		// TODO Auto-generated constructor stub
	}

	public Code(long id, String typeContent, String content) {
		super();
		this.id = id;
		this.typeContent = typeContent;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeContent() {
		return typeContent;
	}

	public void setTypeContent(String typeContent) {
		this.typeContent = typeContent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Code [id=" + id + ", typeContent=" + typeContent + ", content=" + content + "]";
	}
	
	


}
