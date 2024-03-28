package co.edu.unbosque.model;

public class CodeDTO {
	
	private long id;
	private String typeCode;
	private String content;
	
	public CodeDTO() {
		// TODO Auto-generated constructor stub
	}

	public CodeDTO(long id, String typeCode, String content) {
		super();
		this.id = id;
		this.typeCode = typeCode;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CodeDTO [id=" + id + ", typeCode=" + typeCode + ", content=" + content + "]";
	}
	
	
	

}
