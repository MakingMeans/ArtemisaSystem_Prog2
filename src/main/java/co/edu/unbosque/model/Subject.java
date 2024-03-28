package co.edu.unbosque.model;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="subject")
public class Subject{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Tema", nullable=false)
	private String name;
	@Column(name = "Descripcion", nullable=false)
	private String desc;
	@Column(name = "Codigos", nullable=false)
	private ArrayList<CodeDTO> codes;
	
	public Subject() {
		// TODO Auto-generated constructor stub
	}
	
	public Subject(long id, String name, String desc, ArrayList<CodeDTO> codes) {
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
		return "Subject [id=" + id + ", name=" + name + ", desc=" + desc + ", codes=" + codes + "]";
	}
}

