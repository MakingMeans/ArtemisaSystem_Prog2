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
	private SubjectDAO pdao = new SubjectDAO();

	@PostConstruct
	public void init() {
		pdao = new SubjectDAO();
		subjects=new ArrayList<>();
		subjects = readAll();
	}

	// mappeando de entity a dto
	public SubjectDTO toDto(Subject Subject) {
		SubjectDTO dto = new SubjectDTO();
		dto.setId(Subject.getId());
		dto.setName(Subject.getName());
		dto.setDesc(Subject.getDesc());
		dto.setCodes(Subject.getCodes());
		return dto;
	}

	// mappeando de entity a dto
	public Subject toEntity(SubjectDTO SubjectDto) {
		Subject entity = new Subject();
		entity.setId(SubjectDto.getId());
		entity.setName(SubjectDto.getName());
		entity.setDesc(SubjectDto.getDesc());
		entity.setCodes(SubjectDto.getCodes());
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
		pdao.create(toEntity(obj));
		subjects=readAll();

	}

	@Override
	public boolean delete(long id) {
		boolean result = pdao.delete(id);
		subjects=readAll();
		return result;
	}

	@Override
	public boolean update(long id, SubjectDTO obj) {
		boolean result = pdao.update(id, toEntity(obj));
		subjects=readAll();
		return result;
	}

	@Override
	public List<SubjectDTO> readAll() {
		subjects.clear();
		ArrayList<Subject> entities = pdao.readAll();
		for (Subject subject : entities) {
			subjects.add(toDto(subject));
		}
		return subjects;
	}

	@Override
	public SubjectDTO findOne(long id) {
		SubjectDTO find = toDto(pdao.findOne(id));
		return find;
	}

	public List<SubjectDTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectDTO> Subjects) {
		this.subjects = Subjects;
	}

	public SubjectDAO getPdao() {
		return pdao;
	}

	public void setPdao(SubjectDAO pdao) {
		this.pdao = pdao;
	}
	
	

}