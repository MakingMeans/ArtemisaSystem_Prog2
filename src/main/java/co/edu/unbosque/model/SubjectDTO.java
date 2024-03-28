package co.edu.unbosque.model;

import java.util.ArrayList;

public class SubjectDTO {
	private long id;
	private String name;
	private String desc;
	private ArrayList<CodeDTO> codes;
	
	public SubjectDTO() {
		// TODO Auto-generated constructor stub
	}

	public SubjectDTO(long id, String name, String desc, ArrayList<CodeDTO> codes) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.codes = codes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ArrayList<CodeDTO> getCodes() {
		return codes;
	}

	public void setCodes(ArrayList<CodeDTO> codes) {
		this.codes = codes;
	}

	@Override
	public String toString() {
		return "SubjectDTO [id=" + id + ", name=" + name + ", desc=" + desc + ", codes=" + codes + "]";
	}
}
