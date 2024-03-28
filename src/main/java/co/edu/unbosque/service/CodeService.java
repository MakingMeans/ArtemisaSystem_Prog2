package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.CodeDTO;
import co.edu.unbosque.model.Code;
import co.edu.unbosque.model.persistence.CodeDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class CodeService implements ServiceOperation<CodeDTO> {
	
	private List<CodeDTO> codes;
	private CodeDAO codeDAO = new CodeDAO();
	
	@PostConstruct
	public void init() {
		codeDAO = new CodeDAO();
		codes=new ArrayList<>();
		codes = readAll();
	}
	
	// mappeando de entity a dto
	public CodeDTO toDto(Code code) {
		CodeDTO dto = new CodeDTO();
		dto.setId(code.getId());
		dto.setLanguage(code.getLanguage());
		dto.setContent(code.getContent());
	
		return dto;
	}

	// mappeando de entity a dto
	public Code toEntity(CodeDTO codeDTO) {
		Code entity = new Code();
		entity.setId(codeDTO.getId());
		entity.setLanguage(codeDTO.getLanguage());
		entity.setContent(codeDTO.getContent());
		
		return entity;
	}

	

	public List<CodeDTO> getCodes(int size) {

		if (size > codes.size()) {
			List<CodeDTO> shortenedList = new ArrayList<>();
			for (int i = 0; i < codes.size() - 1; i++) {
				shortenedList.add(codes.get(i));
			}
			return shortenedList;
		}

		else {
			return new ArrayList<>(codes.subList(0, size));
		}

	}

	@Override
	public void create(CodeDTO obj) {
		codeDAO.create(toEntity(obj));
		codes=readAll();

	}

	@Override
	public boolean delete(long id) {
		boolean result = codeDAO.delete(id);
		codes=readAll();
		return result;
	}

	@Override
	public boolean update(long id, CodeDTO obj) {
		boolean result = codeDAO.update(id, toEntity(obj));
		codes=readAll();
		return result;
	}

	@Override
	public List<CodeDTO> readAll() {
		codes.clear();
		ArrayList<Code> entities = codeDAO.readAll();
		for (Code Code : entities) {
			codes.add(toDto(Code));
		}
		return codes;
	}

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
