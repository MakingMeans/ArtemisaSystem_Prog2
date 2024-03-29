package co.edu.unbosque.model;

import java.util.ArrayList;

public class CategoryDTO {
	
	private long id;
	private ArrayList<SubjectDTO> subjects;
	private String title;
	
	public CategoryDTO() {
		// TODO Auto-generated constructor stub
	}

	public CategoryDTO(long id, ArrayList<SubjectDTO> subjects, String title) {
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
		return "CategoryDTO [id=" + id + ", subjects=" + subjects + ", title=" + title + "]";
	}
	
	

}
