package co.edu.unbosque.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.primefaces.PrimeFaces;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import co.edu.unbosque.model.Email;
import co.edu.unbosque.model.UserDTO;
import co.edu.unbosque.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * 
 * Bean de User.
 * 
 * @author DavidG
 *
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

	/**
	 * Corresponde al serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Servicio para la gestión de usuarios.
	 */
	@Inject
	private UserService userService;

	@Inject
	private EmailSenderBean emailSenderBean;
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
	 * Email temporal del index.
	 */
	private String tempEmail;

	private String usernameOrEmail;
	private String password;

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
	 * Crea un pdf sobre todos los registros de este objeto.
	 * @throws com.itextpdf.text.DocumentException
	 */
	public void exportToPDF() throws com.itextpdf.text.DocumentException {
		Document document = new Document();
		try {
			String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")
					+ "UsersHistorial.pdf";
			try {
				PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));
			} catch (com.itextpdf.text.DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			document.open();
			try {
				document.add(new Paragraph("Lista de Codigos C++\n\n"));
			} catch (com.itextpdf.text.DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (usuariosEnTabla != null && !usuariosEnTabla.isEmpty()) {
				for (UserDTO usuario : usuariosEnTabla) {
					try {
						document.add(new Paragraph("ID: " + usuario.getId()));
	                    document.add(new Paragraph("Nombre de usuario: " + usuario.getUsername()));
	                    document.add(new Paragraph("Correo electrónico: " + usuario.getEmail()));
	                    document.add(new Paragraph("Semestre: " + usuario.getSemester()));
	                    document.add(new Paragraph("Carrera: " + usuario.getCareer()));
	                    document.add(new Paragraph("Es administrador: " + usuario.isHasAdmin()));
	                    document.add(new Paragraph("\n"));

						document.add(new Paragraph("\n"));
					} catch (com.itextpdf.text.DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				document.add(new Paragraph("No hay archivos para exportar."));
			}

			document.close();

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			File file = new File(filePath);
			byte[] content = Files.readAllBytes(file.toPath());
			externalContext.responseReset();
			externalContext.setResponseContentType("application/pdf");
			externalContext.setResponseContentLength(content.length);
			externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
			externalContext.getResponseOutputStream().write(content);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (com.itextpdf.text.DocumentException | IOException e) {
			e.printStackTrace();
			// Agregar manejo específico de excepciones aquí
		}
	}
	
	/**
	 * Verifica la seguridad de la contraseña.
	 */
	public boolean checkPasswordStrength(String password) {
	    // Verificar la longitud mínima de la contraseña
	    if (password.length() < 8) {
	        showError("La contraseña debe tener al menos 8 caracteres.");
	        return false;
	    }
	    
	    // Verificar si la contraseña contiene al menos un número
	    if (!password.matches(".*\\d.*")) {
	        showError("La contraseña debe contener al menos un número.");
	        return false;
	    }
	    
	    // Verificar si la contraseña contiene al menos una letra minúscula
	    if (!password.matches(".*[a-z].*")) {
	        showError("La contraseña debe contener al menos una letra minúscula.");
	        return false;
	    }
	    
	    // Verificar si la contraseña contiene al menos una letra mayúscula
	    if (!password.matches(".*[A-Z].*")) {
	        showError("La contraseña debe contener al menos una letra mayúscula.");
	        return false;
	    }
	    
	    // Verificar si la contraseña contiene al menos un carácter especial
	    if (!password.matches(".*[!@#$%^&*()].*")) {
	        showError("La contraseña debe contener al menos un carácter especial.");
	        return false;
	    }
	    return true;
	}

	/**
	 * Guarda un usuario en la base de datos.
	 */
	public void saveUserOnRegister() {
		if (this.usuarioSeleccionado.getId() == 0) {
			this.usuarioSeleccionado.setId(0);
			this.usuarioSeleccionado.setEmail(tempEmail);
			int taken = userService.takenUserOrEmail(this.usuarioSeleccionado.getUsername());
			if (tempEmail.equals("dgarciapr@unbosque.edu.co")
					|| tempEmail.equals("sradah@unbosque.edu.co") || tempEmail.equals("wramoso@unbosque.edu.co")
					|| tempEmail.equals("vyara@unbosque.edu.co") || tempEmail.equals("jjaramillon@unbosque.edu.co")) {
				this.usuarioSeleccionado.setHasAdmin(true);
				Email email = new Email(tempEmail, "Bienvenido a Biblioteca Artemisa",
						"Hola! " + this.usuarioSeleccionado.getUsername() + " Gracias por registrarte en Biblioteca Artemisa! Tu rol es: Admin");
				emailSenderBean.setEmail(email);
				emailSenderBean.sendEmail();
			} else {
				this.usuarioSeleccionado.setHasAdmin(false);
				Email email = new Email(tempEmail, "Bienvenido a Biblioteca Artemisa",
						"Hola! " + this.usuarioSeleccionado.getUsername() + " Gracias por registrarte en Biblioteca Artemisa! Tu rol es: Usuario");
				emailSenderBean.setEmail(email);
				emailSenderBean.sendEmail();
			}
			if (taken == 0) {
				// faker
				showError("Nombre de usuario ya en uso.");
			} else if(checkPasswordStrength(usuarioSeleccionado.getPassword())){
				userService.create(usuarioSeleccionado);
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
						.handleNavigation(FacesContext.getCurrentInstance(), null, "login.xhtml");
				this.usuariosEnTabla = userService.getUsers();
			}
		}
	}
	
	/**
	 * Verifica el formato de email.
	 */
	public void checkEmail() {
		openNew();
		Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._-]+@unbosque\\.edu\\.co$");
		Matcher matcher = emailPattern.matcher(tempEmail);
		// Verificar si el campo está vacío
		if (tempEmail.trim().isEmpty()) {
			// Mostrar mensaje de alerta o manejar el error de alguna otra manera
			showError("Por favor, introduce una dirección de correo electrónico.");
		}

		// Verificar si hay al menos un @ en el correo electrónico
		else if (tempEmail.indexOf('@') == -1) {
			// Mostrar mensaje de alerta o manejar el error de alguna otra manera
			showError("Por favor, introduce una dirección de correo electrónico válida.");
		}

		// Validar el formato del correo electrónico
		else if (!matcher.matches()) {
			// Mostrar mensaje de alerta o manejar el error de alguna otra manera
			showError("Por favor, introduce una dirección de correo electrónico válida con el dominio @unbosque.edu.co");
		} else {
			int taken = userService.takenUserOrEmail(tempEmail);
			if (taken == 1) {
				// faker
				showInfo("Email ya en uso.");
			} else {
				// original
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
						.handleNavigation(FacesContext.getCurrentInstance(), null, "welcome.xhtml");
			}
		}
	}

	/**
	 * Metodo para iniciar sesion
	 */
	public void login() {
		int loggedIn = userService.login(usernameOrEmail, password);
		if (loggedIn == 0) {
			// admin
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "admin.xhtml");
		} else if (loggedIn == 1) {
			// plebeyo
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "main.xhtml");
		} else {
			// intruder
			showError("Nombre de usuario, correo o contraseña incorrectos.");
		}
	}
	
	/**
	 * Muestra mensaje de faces.
	 * @param severity
	 * @param summary
	 * @param detail
	 */
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("setTimeout(function() { var messages = document.getElementById('growl'); messages.style.display = 'none'; }, 5000);");
        FacesContext.getCurrentInstance().renderResponse();
    }

	/**
	 * Muestra alerta de info.
	 * @param data
	 */
    public void showInfo(String data) {
        addMessage(FacesMessage.SEVERITY_INFO, "Info Message", data);
    }
    
    /**
     * Muestra alerta de warn.
     * @param data
     */
    public void showWarn(String data) {
        addMessage(FacesMessage.SEVERITY_WARN, "Warn Message", data);
    }

    /**
     * Muestra alerta de error.
     * @param data
     */
    public void showError(String data) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error Message", data);
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

	// Métodos getters y setters

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
	 * Obtiene los usuarios seleccionados.
	 */
	public List<UserDTO> getSelectedUsers() {
		return usuariosSeleccionadosVarios;
	}

	/**
	 * Establece los usuarios seleccionados.
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

	/**
	 * Obtiene el email temporal.
	 */
	public String getTempEmail() {
		return tempEmail;
	}

	/**
	 * Establece el email temporal.
	 */
	public void setTempEmail(String tempEmail) {
		this.tempEmail = tempEmail;
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmailSenderBean getEmailSenderBean() {
		return emailSenderBean;
	}

	public void setEmailSenderBean(EmailSenderBean emailSenderBean) {
		this.emailSenderBean = emailSenderBean;
	}
}
