package co.edu.unbosque.model;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="tema", nullable = false)
	private ArrayList<SubjectDTO> subjects;
	@Column(name="titulo", nullable = false)
	private String title;

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(long id, ArrayList<SubjectDTO> subjects, String title) {
		super();
		this.id = id;
		this.subjects = subjects;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArrayList<SubjectDTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(ArrayList<SubjectDTO> subjects) {
		this.subjects = subjects;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", subjects=" + subjects + ", title=" + title + "]";
	}




}
