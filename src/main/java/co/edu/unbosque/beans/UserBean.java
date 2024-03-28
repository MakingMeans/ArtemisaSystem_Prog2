package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;

import co.edu.unbosque.model.UserDTO;
import co.edu.unbosque.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Clase que representa un bean para la gestión de usuarios en la aplicación.
 * Este bean se encarga de manejar la interacción con la capa de presentación
 * y la capa de servicios para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre usuarios.
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

    /**
     * Serial version UID para la serialización de objetos.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Servicio para la gestión de usuarios.
     */
    @Inject
    private UserService userService;

    /**
     * Lista de usuarios mostrados en la tabla.
     */
    private List<UserDTO> usuariosEnTabla;

    /**
     * Usuario seleccionado actualmente.
     */
    private UserDTO usuarioSeleccionado;

    /**
     * Lista de usuarios seleccionados.
     */
    private List<UserDTO> usuariosSeleccionadosVarios;

    /**
     * Inicializa el bean. Carga la lista de usuarios al iniciar la sesión.
     */
    @PostConstruct
    public void init() {
        this.usuariosEnTabla = new ArrayList<>();
        this.usuariosEnTabla = userService.getUsers();
        this.usuariosSeleccionadosVarios = new ArrayList<UserDTO>();
    }

    /**
     * Abre un nuevo formulario para agregar un usuario.
     */
    public void openNew() {
        this.usuarioSeleccionado = new UserDTO();
    }

    /**
     * Guarda un usuario en la base de datos.
     */
    public void saveUser() {
        if (this.usuarioSeleccionado.getId() == 0) {
            this.usuarioSeleccionado.setId(0);
            userService.create(usuarioSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario agregado"));
        } else {
            userService.update(usuarioSeleccionado.getId(), usuarioSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario actualizado"));
        }
        this.usuariosEnTabla = userService.getUsers();
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Users");
    }

    /**
     * Elimina un usuario seleccionado.
     */
    public void deleteUser() {
        userService.delete(this.usuarioSeleccionado.getId());
        this.usuariosSeleccionadosVarios.remove(this.usuarioSeleccionado);
        this.usuarioSeleccionado = null;
        this.usuariosEnTabla = userService.getUsers();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Users");
        PrimeFaces.current().executeScript("PF('dtUsers').clearFilters()");
    }

    /**
     * Obtiene el mensaje para el botón de eliminar.
     */
    public String getDeleteButtonMessage() {
        if (hasSelectedUsers()) {
            int size = this.usuariosSeleccionadosVarios.size();
            return size > 1 ? size + " usuarios seleccionados" : "1 usuario seleccionado";
        }
        return "Eliminado";
    }

    /**
     * Verifica si hay usuarios seleccionados.
     */
    public boolean hasSelectedUsers() {
        return this.usuariosSeleccionadosVarios != null && !this.usuariosSeleccionadosVarios.isEmpty();
    }

    /**
     * Elimina varios usuarios seleccionados.
     */
    public void deleteSelectedUsers() {
        for (UserDTO p : usuariosSeleccionadosVarios) {
            userService.delete(p.getId());
        }
        this.usuariosSeleccionadosVarios = null;
        this.usuariosEnTabla = userService.getUsers();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuarios eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Users");
        PrimeFaces.current().executeScript("PF('dtUsers').clearFilters()");
    }

    /**
     * Obtiene la lista de usuarios.
     */
    public List<UserDTO> getUsers() {
        return usuariosEnTabla;
    }

    /**
     * Establece la lista de usuarios.
     */
    public void setUsers(List<UserDTO> Users) {
        this.usuariosEnTabla = Users;
    }

    /**
     * Obtiene el usuario seleccionado.
     */
    public UserDTO getSelectedUser() {
        return usuarioSeleccionado;
    }

    /**
     * Establece el usuario seleccionado.
     */
    public void setSelectedUser(UserDTO selectedUser) {
        this.usuarioSeleccionado = selectedUser;
    }

    /**
     * Obtiene la lista de usuarios seleccionados.
     */
    public List<UserDTO> getSelectedUsers() {
        return usuariosSeleccionadosVarios;
    }

    /**
     * Establece la lista de usuarios seleccionados.
     */
    public void setSelectedUsers(List<UserDTO> selectedUsers) {
        this.usuariosSeleccionadosVarios = selectedUsers;
    }

    /**
     * Obtiene el serialVersionUID.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
