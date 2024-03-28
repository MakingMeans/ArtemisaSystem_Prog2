package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.UserDTO;
import co.edu.unbosque.model.persistence.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UserService implements ServiceOperation<UserDTO> {

	private List<UserDTO> users;
	private UserDAO userDAO = new UserDAO();

	@PostConstruct
	public void init() {
		userDAO = new UserDAO();
		users=new ArrayList<>();
		users = readAll();
	}

	// mappeando de entity a dto
	public UserDTO toDto(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setEmail(user.getEmail());
		dto.setSemester(user.getSemester());
		dto.setCareer(user.getCareer());
		dto.setHasAdmin(user.isHasAdmin());
		return dto;
	}

	// mappeando de entity a dto
	public User toEntity(UserDTO userDTO) {
		User entity = new User();
		entity.setId(userDTO.getId());
		entity.setUsername(userDTO.getUsername());
		entity.setPassword(userDTO.getPassword());
		entity.setEmail(userDTO.getEmail());
		entity.setSemester(userDTO.getSemester());
		entity.setCareer(userDTO.getCareer());
		entity.setHasAdmin(userDTO.isHasAdmin());
		return entity;
	}

	

	public List<UserDTO> getUsers(int size) {

		if (size > users.size()) {
			List<UserDTO> shortenedList = new ArrayList<>();
			for (int i = 0; i < users.size() - 1; i++) {
				shortenedList.add(users.get(i));
			}
			return shortenedList;
		}

		else {
			return new ArrayList<>(users.subList(0, size));
		}

	}

	@Override
	public void create(UserDTO obj) {
		userDAO.create(toEntity(obj));
		users=readAll();

	}

	@Override
	public boolean delete(long id) {
		boolean result = userDAO.delete(id);
		users=readAll();
		return result;
	}

	@Override
	public boolean update(long id, UserDTO obj) {
		boolean result = userDAO.update(id, toEntity(obj));
		users=readAll();
		return result;
	}

	@Override
	public List<UserDTO> readAll() {
		users.clear();
		ArrayList<User> entities = userDAO.readAll();
		for (User user : entities) {
			users.add(toDto(user));
		}
		return users;
	}

	@Override
	public UserDTO findOne(long id) {
		UserDTO find = toDto(userDAO.findOne(id));
		return find;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}