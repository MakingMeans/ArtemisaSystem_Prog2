package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.Subject;
import co.edu.unbosque.model.SubjectDTO;
import co.edu.unbosque.model.persistence.SubjectDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * 
 * Service de Subject.
 * 
 * @author DavidG
 *
 */
@Named
@ApplicationScoped
public class SubjectService implements ServiceOperation<SubjectDTO> {

	private List<SubjectDTO> subjects;
	private SubjectDAO subjectDAO = new SubjectDAO();

	/**
	 * Inicializa el DAO de asignaturas y carga todas las asignaturas desde la base de datos.
	 */
	@PostConstruct
	public void init() {
	    subjectDAO = new SubjectDAO();
	    subjects = new ArrayList<>();
	    subjects = readAll();
	}

	/**
	 * Convierte un objeto de tipo Subject a un objeto de tipo SubjectDTO.
	 * @param subject El objeto de tipo Subject a convertir.
	 * @return Un objeto de tipo SubjectDTO convertido.
	 */
	public SubjectDTO toDto(Subject subject) {
	    SubjectDTO dto = new SubjectDTO();
	    dto.setId(subject.getId());
	    dto.setName(subject.getName());
	    dto.setDesc(subject.getDesc());
	    dto.setCodes(subject.getCodes());
	    return dto;
	}

	/**
	 * Convierte un objeto de tipo SubjectDTO a un objeto de tipo Subject.
	 * @param subjectDTO El objeto de tipo SubjectDTO a convertir.
	 * @return Un objeto de tipo Subject convertido.
	 */
	public Subject toEntity(SubjectDTO subjectDTO) {
	    Subject entity = new Subject();
	    entity.setId(subjectDTO.getId());
	    entity.setName(subjectDTO.getName());
	    entity.setDesc(subjectDTO.getDesc());
	    entity.setCodes(subjectDTO.getCodes());
	    return entity;
	}

	/**
	 * Obtiene una lista de asignaturas con el tamaño especificado.
	 * Si el tamaño especificado es mayor que el tamaño actual de la lista de asignaturas, devuelve una lista truncada.
	 * @param size El tamaño de la lista de asignaturas deseado.
	 * @return Una lista de asignaturas con el tamaño especificado.
	 */
	public List<SubjectDTO> getSubjects(int size) {
	    if (size > subjects.size()) {
	        List<SubjectDTO> shortenedList = new ArrayList<>();
	        for (int i = 0; i < subjects.size() - 1; i++) {
	            shortenedList.add(subjects.get(i));
	        }
	        return shortenedList;
	    } else {
	        return new ArrayList<>(subjects.subList(0, size));
	    }
	}

	/**
	 * Crea una nueva asignatura en la base de datos.
	 * @param obj El objeto SubjectDTO que representa la asignatura a crear.
	 */
	@Override
	public void create(SubjectDTO obj) {
	    subjectDAO.create(toEntity(obj));
	    subjects = readAll();
	}

	/**
	 * Elimina una asignatura de la base de datos según su ID.
	 * @param id El ID de la asignatura que se va a eliminar.
	 * @return true si la eliminación se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean delete(long id) {
	    boolean result = subjectDAO.delete(id);
	    subjects = readAll();
	    return result;
	}

	/**
	 * Actualiza los datos de una asignatura en la base de datos.
	 * @param id El ID de la asignatura que se va a actualizar.
	 * @param obj El objeto SubjectDTO con los nuevos datos.
	 * @return true si la actualización se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean update(long id, SubjectDTO obj) {
	    boolean result = subjectDAO.update(id, toEntity(obj));
	    subjects = readAll();
	    return result;
	}

	/**
	 * Lee todas las asignaturas almacenadas en la base de datos y las convierte en una lista de SubjectDTO.
	 * @return Una lista de todas las asignaturas almacenadas convertidas en objetos de tipo SubjectDTO.
	 */
	@Override
	public List<SubjectDTO> readAll() {
	    subjects.clear();
	    ArrayList<Subject> entities = subjectDAO.readAll();
	    for (Subject subject : entities) {
	        subjects.add(toDto(subject));
	    }
	    return subjects;
	}

	/**
	 * Busca y devuelve una asignatura específica de la base de datos según su ID.
	 * @param id El ID de la asignatura que se va a buscar.
	 * @return La asignatura encontrada, o null si no se encuentra ninguna asignatura con ese ID.
	 */
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