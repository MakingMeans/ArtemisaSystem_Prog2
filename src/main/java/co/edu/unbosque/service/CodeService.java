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
	private CodeDAO cdao = new CodeDAO();
	
	@PostConstruct
	public void init() {
		cdao = new CodeDAO();
		codes=new ArrayList<>();
		codes = readAll();
	}
	
	// mappeando de entity a dto
	public CodeDTO toDto(Code Code) {
		CodeDTO dto = new CodeDTO();
		dto.setId(Code.getId());
		dto.setTypeCode(Code.getTypeContent());
		dto.setContent(Code.getContent());
	
		return dto;
	}

	// mappeando de entity a dto
	public Code toEntity(CodeDTO CodeDto) {
		Code entity = new Code();
		entity.setId(CodeDto.getId());
		entity.setTypeContent(CodeDto.getTypeCode());
		entity.setContent(CodeDto.getContent());
		
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
		cdao.create(toEntity(obj));
		codes=readAll();

	}

	@Override
	public boolean delete(long id) {
		boolean result = cdao.delete(id);
		codes=readAll();
		return result;
	}

	@Override
	public boolean update(long id, CodeDTO obj) {
		boolean result = cdao.update(id, toEntity(obj));
		codes=readAll();
		return result;
	}

	@Override
	public List<CodeDTO> readAll() {
		codes.clear();
		ArrayList<Code> entities = cdao.readAll();
		for (Code Code : entities) {
			codes.add(toDto(Code));
		}
		return codes;
	}

	@Override
	public CodeDTO findOne(long id) {
		CodeDTO find = toDto(cdao.findOne(id));
		return find;
	}

	public List<CodeDTO> getCodes() {
		return codes;
	}

	public void setCodes(List<CodeDTO> codes) {
		this.codes = codes;
	}

	public CodeDAO getCdao() {
		return cdao;
	}

	public void setCdao(CodeDAO cdao) {
		this.cdao = cdao;
	}
	
	

}
