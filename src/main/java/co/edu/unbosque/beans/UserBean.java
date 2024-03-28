package co.edu.unbosque.beans;
//target\m2e-wtp\web-resources\META-INF\maven\co.edu.unbosque\ArtemisaSystem_Prog2\pom.properties
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

@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    private List<UserDTO> usuariosEnTabla;

    private UserDTO usuarioSeleccionado;

    private List<UserDTO> usuariosSeleccionadosVarios;

    @PostConstruct
    public void init() {
        this.usuariosEnTabla = new ArrayList<>();
        this.usuariosEnTabla = userService.getUsers();
        this.usuariosSeleccionadosVarios = new ArrayList<UserDTO>();
    }

    public void openNew() {
        this.usuarioSeleccionado = new UserDTO();
    }

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

    public void deleteUser() {
        userService.delete(this.usuarioSeleccionado.getId());
        this.usuariosSeleccionadosVarios.remove(this.usuarioSeleccionado);
        this.usuarioSeleccionado = null;
        this.usuariosEnTabla = userService.getUsers();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Users");
        PrimeFaces.current().executeScript("PF('dtUsers').clearFilters()");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedUsers()) {
            int size = this.usuariosSeleccionadosVarios.size();
            return size > 1 ? size + " usuarios seleccionados" : "1 usuario seleccionado";
        }
        return "Eliminado";
    }

    public boolean hasSelectedUsers() {
        return this.usuariosSeleccionadosVarios != null && !this.usuariosSeleccionadosVarios.isEmpty();
    }

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

    public List<UserDTO> getUsers() {
        return usuariosEnTabla;
    }

    public void setUsers(List<UserDTO> Users) {
        this.usuariosEnTabla = Users;
    }

    public UserDTO getSelectedUser() {
        return usuarioSeleccionado;
    }

    public void setSelectedUser(UserDTO selectedUser) {
        this.usuarioSeleccionado = selectedUser;
    }

    public List<UserDTO> getSelectedUsers() {
        return usuariosSeleccionadosVarios;
    }

    public void setSelectedUsers(List<UserDTO> selectedUsers) {
        this.usuariosSeleccionadosVarios = selectedUsers;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
