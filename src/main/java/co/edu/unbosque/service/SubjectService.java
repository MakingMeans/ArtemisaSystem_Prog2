package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.Subject;
import co.edu.unbosque.model.SubjectDTO;
import co.edu.unbosque.model.persistence.SubjectDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class SubjectService implements ServiceOperation<SubjectDTO> {

	private List<SubjectDTO> subjects;
	private SubjectDAO subjectDAO = new SubjectDAO();

	@PostConstruct
	public void init() {
		subjectDAO = new SubjectDAO();
		subjects=new ArrayList<>();
		subjects = readAll();
	}

	// mappeando de entity a dto
	public SubjectDTO toDto(Subject subject) {
		SubjectDTO dto = new SubjectDTO();
		dto.setId(subject.getId());
		dto.setName(subject.getName());
		dto.setDesc(subject.getDesc());
		dto.setCodes(subject.getCodes());
		return dto;
	}

	// mappeando de entity a dto
	public Subject toEntity(SubjectDTO subjectDTO) {
		Subject entity = new Subject();
		entity.setId(subjectDTO.getId());
		entity.setName(subjectDTO.getName());
		entity.setDesc(subjectDTO.getDesc());
		entity.setCodes(subjectDTO.getCodes());
		return entity;
	}

	

	public List<SubjectDTO> getSubjects(int size) {

		if (size > subjects.size()) {
			List<SubjectDTO> shortenedList = new ArrayList<>();
			for (int i = 0; i < subjects.size() - 1; i++) {
				shortenedList.add(subjects.get(i));
			}
			return shortenedList;
		}

		else {
			return new ArrayList<>(subjects.subList(0, size));
		}

	}

	@Override
	public void create(SubjectDTO obj) {
		subjectDAO.create(toEntity(obj));
		subjects=readAll();

	}

	@Override
	public boolean delete(long id) {
		boolean result = subjectDAO.delete(id);
		subjects=readAll();
		return result;
	}

	@Override
	public boolean update(long id, SubjectDTO obj) {
		boolean result = subjectDAO.update(id, toEntity(obj));
		subjects=readAll();
		return result;
	}

	@Override
	public List<SubjectDTO> readAll() {
		subjects.clear();
		ArrayList<Subject> entities = subjectDAO.readAll();
		for (Subject subject : entities) {
			subjects.add(toDto(subject));
		}
		return subjects;
	}

	@Override
	public SubjectDTO findOne(long id) {
		SubjectDTO find = toDto(subjectDAO.findOne(id));
		return find;
	}

	public List<SubjectDTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectDTO> Subjects) {
		this.subjects = Subjects;
	}

	public SubjectDAO getSubjectDAO() {
		return subjectDAO;
	}

	public void setSubjectDAO(SubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}
}