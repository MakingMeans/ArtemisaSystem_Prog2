package co.edu.unbosque.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;

import co.edu.unbosque.service.UserService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * 
 * Bean de Login&Register.
 * 
 * @author DavidG
 *
 */
@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Inject
    private UserService userService;
    
    private String usernameOrEmail;
    private String password;
    
    /**
     * Email temporal del index.
     */
    private String tempEmail;
    
    // Getters y Setters
    
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
    
    // Método para el inicio de sesión
    public void login() {
    	System.out.println(tempEmail);
        int loggedIn = userService.login(usernameOrEmail, password);
        if (loggedIn == 0) {
            // admin
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml");
        } else if (loggedIn == 1) {
            // plebeyo
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "welcome.xhtml");
        } else {
            // intruder
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Nombre de usuario, correo o contraseña incorrectos."));
            FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("setTimeout(function() { var messages = document.getElementById('messages'); messages.style.display = 'none'; }, 5000);");
        }
    }

}
