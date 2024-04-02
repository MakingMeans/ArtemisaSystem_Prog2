package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.UserDTO;
import co.edu.unbosque.model.persistence.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * 
 * Service de User.
 * 
 * @author DavidG.
 *
 */
@Named
@ApplicationScoped
public class UserService implements ServiceOperation<UserDTO> {

	private List<UserDTO> users;
	private UserDAO userDAO = new UserDAO();

	/**
	 * Inicializa el DAO de usuarios y carga todos los usuarios desde la base de datos.
	 */
	@PostConstruct
	public void init() {
	    userDAO = new UserDAO();
	    users = new ArrayList<>();
	    users = readAll();
	}

	/**
	 * Convierte un objeto de tipo User a un objeto de tipo UserDTO.
	 * @param user El objeto de tipo User a convertir.
	 * @return Un objeto de tipo UserDTO convertido.
	 */
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

	/**
	 * Convierte un objeto de tipo UserDTO a un objeto de tipo User.
	 * @param userDTO El objeto de tipo UserDTO a convertir.
	 * @return Un objeto de tipo User convertido.
	 */
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

	/**
	 * Obtiene una lista de usuarios con el tamaño especificado.
	 * Si el tamaño especificado es mayor que el tamaño actual de la lista de usuarios, devuelve una lista truncada.
	 * @param size El tamaño de la lista de usuarios deseado.
	 * @return Una lista de usuarios con el tamaño especificado.
	 */
	public List<UserDTO> getUsers(int size) {
	    if (size > users.size()) {
	        List<UserDTO> shortenedList = new ArrayList<>();
	        for (int i = 0; i < users.size() - 1; i++) {
	            shortenedList.add(users.get(i));
	        }
	        return shortenedList;
	    } else {
	        return new ArrayList<>(users.subList(0, size));
	    }
	}

	/**
	 * Crea un nuevo usuario en la base de datos.
	 * @param obj El objeto UserDTO que representa el usuario a crear.
	 */
	@Override
	public void create(UserDTO obj) {
	    userDAO.create(toEntity(obj));
	    users = readAll();
	}

	/**
	 * Elimina un usuario de la base de datos según su ID.
	 * @param id El ID del usuario que se va a eliminar.
	 * @return true si la eliminación se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean delete(long id) {
	    boolean result = userDAO.delete(id);
	    users = readAll();
	    return result;
	}

	/**
	 * Actualiza los datos de un usuario en la base de datos.
	 * @param id El ID del usuario que se va a actualizar.
	 * @param obj El objeto UserDTO con los nuevos datos.
	 * @return true si la actualización se realizó correctamente, false de lo contrario.
	 */
	@Override
	public boolean update(long id, UserDTO obj) {
	    boolean result = userDAO.update(id, toEntity(obj));
	    users = readAll();
	    return result;
	}

	/**
	 * Lee todos los usuarios almacenados en la base de datos y los convierte en una lista de UserDTO.
	 * @return Una lista de todos los usuarios almacenados convertidos en objetos de tipo UserDTO.
	 */
	@Override
	public List<UserDTO> readAll() {
	    users.clear();
	    ArrayList<User> entities = userDAO.readAll();
	    for (User user : entities) {
	        users.add(toDto(user));
	    }
	    return users;
	}

	/**
	 * Busca y devuelve un usuario específico de la base de datos según su ID.
	 * @param id El ID del usuario que se va a buscar.
	 * @return El usuario encontrado, o null si no se encuentra ningún usuario con ese ID.
	 */
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
	
	public int login(String usernameOrEmail, String password) {
	    UserDTO user = findUserByUsernameOrEmail(usernameOrEmail);
	    if(user != null && user.getPassword().equals(password) && user.isHasAdmin()) {
	    	return 0;
	    }else if (user != null && user.getPassword().equals(password)) {
	        // Usuario encontrado y contraseña coincide
	        // Realizar cualquier otra lógica de inicio de sesión necesaria
	        return 1;
	    } else {
	        return 2;
	    }
	}
	
	public int takenUserOrEmail(String usernameOrEmail) {
	    for (UserDTO user : getUsers()) {
	        if (user.getUsername().equals(usernameOrEmail)) {
	            return 0;
	        }
	        else if (user.getEmail().equals(usernameOrEmail)) {
	            return 1;
	        }
	    }
	    return 2;
	}
	
	public UserDTO findUserByUsernameOrEmail(String usernameOrEmail) {
	    for (UserDTO user : getUsers()) {
	        if (user.getUsername().equals(usernameOrEmail) || user.getEmail().equals(usernameOrEmail)) {
	            return user;
	        }
	    }
	    return null;
	}


}