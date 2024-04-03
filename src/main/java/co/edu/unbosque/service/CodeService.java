package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.CodeDTO;
import co.edu.unbosque.model.Code;
import co.edu.unbosque.model.persistence.CodeDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * 
 * Service de Code.
 * 
 * @author WilmerR
 *
 */
@Named
@ApplicationScoped
public class CodeService implements ServiceOperation<CodeDTO> {

	private List<CodeDTO> codes;
	private CodeDAO codeDAO = new CodeDAO();
	/**
	 * Método de inicialización llamado después de que se haya creado una instancia de la clase.
	 * Este método inicializa el DAO de códigos y carga todos los códigos desde la base de datos.
	 */
	@PostConstruct
	public void init() {
		codeDAO = new CodeDAO();
		codes = new ArrayList<>();
		codes = readAll();
	}

	/**
	 * Convierte un objeto de tipo Code a un objeto de tipo CodeDTO.
	 * @param code El objeto de tipo Code a convertir.
	 * @return Un objeto de tipo CodeDTO convertido.
	 */
	public CodeDTO toDto(Code code) {
	    CodeDTO dto = new CodeDTO();
	    dto.setId(code.getId());
	    dto.setLanguage(code.getLanguage());
	    dto.setContent(code.getContent());
	    dto.setSubjectOf(code.getSubjectOf());

	    return dto;
	}

	/**
	 * Convierte un objeto de tipo CodeDTO a un objeto de tipo Code.
	 * @param codeDTO El objeto de tipo CodeDTO a convertir.
	 * @return Un objeto de tipo Code convertido.
	 */
	public Code toEntity(CodeDTO codeDTO) {
	    Code entity = new Code();
	    entity.setId(codeDTO.getId());
	    entity.setLanguage(codeDTO.getLanguage());
	    entity.setContent(codeDTO.getContent());
	    entity.setSubjectOf(codeDTO.getSubjectOf());

	    return entity;
	}

	/**
	 * Obtiene una lista de códigos con el tamaño especificado.
	 * Si el tamaño especificado es mayor que el tamaño actual de la lista de códigos, devuelve una lista truncada.
	 * @param size El tamaño de la lista de códigos deseado.
	 * @return Una lista de códigos con el tamaño especificado.
	 */
	public List<CodeDTO> getCodes(int size) {
	    if (size > codes.size()) {
	        List<CodeDTO> shortenedList = new ArrayList<>();
	        for (int i = 0; i < codes.size() - 1; i++) {
	            shortenedList.add(codes.get(i));
	        }
	        return shortenedList;
	    } else {
	        return new ArrayList<>(codes.subList(0, size));
	    }
	}

	/**
	 * Crea un nuevo código en la base de datos.
	 * 
	 * @param obj El objeto CodeDTO que representa el código a crear.
	 */
	@Override
	public void create(CodeDTO obj) {
		codeDAO.create(toEntity(obj));
		codes = readAll();

	}

	/**
	 * Elimina un código de la base de datos según su ID.
	 * 
	 * @param id El ID del código que se va a eliminar.
	 * @return true si la eliminación se realizó correctamente, false de lo
	 *         contrario.
	 */
	@Override
	public boolean delete(long id) {
		boolean result = codeDAO.delete(id);
		codes = readAll();
		return result;
	}

	/**
	 * Actualiza los datos de un código en la base de datos.
	 * 
	 * @param id  El ID del código que se va a actualizar.
	 * @param obj El objeto CodeDTO con los nuevos datos.
	 * @return true si la actualización se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean update(long id, CodeDTO obj) {
		boolean result = codeDAO.update(id, toEntity(obj));
		codes = readAll();
		return result;
	}

	/**
	 * Lee todos los códigos almacenados en la base de datos y los convierte en una
	 * lista de CodeDTO.
	 * 
	 * @return Una lista de todos los códigos almacenados convertidos en objetos de
	 *         tipo CodeDTO.
	 */
	@Override
	public List<CodeDTO> readAll() {
		codes.clear();
		ArrayList<Code> entities = codeDAO.readAll();
		for (Code code : entities) {
			codes.add(toDto(code));
		}
		return codes;
	}

	/**
	 * Busca y devuelve un código específico de la base de datos según su ID.
	 * 
	 * @param id El ID del código que se va a buscar.
	 * @return El código encontrado, o null si no se encuentra ningún código con ese
	 *         ID.
	 */
	@Override
	public CodeDTO findOne(long id) {
		CodeDTO find = toDto(codeDAO.findOne(id));
		return find;
	}

	public List<CodeDTO> getCodes() {
		return codes;
	}

	public void setCodes(List<CodeDTO> codes) {
		this.codes = codes;
	}

	public CodeDAO getCodeDAO() {
		return codeDAO;
	}

	public void setCodeDAO(CodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}
}
