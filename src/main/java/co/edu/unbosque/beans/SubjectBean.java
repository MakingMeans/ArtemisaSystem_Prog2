package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;

import co.edu.unbosque.model.SubjectDTO;
import co.edu.unbosque.service.SubjectService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * 
 * Bean de Subject.
 * 
 * @author DavidG
 *
 */
@Named("subjectBean")
@SessionScoped
public class SubjectBean implements Serializable {

	/**
	 * Corresponde al serialVersionUID
	 */
    private static final long serialVersionUID = 1L;

    /**
     * Servicio para la gestión de temas.
     */
    @Inject
    private SubjectService subjectService;
    
    /**
     * Lista de temas mostrados en la tabla.
     */
    private List<SubjectDTO> temasEnTabla;
    
    /**
     * Tema seleccionado actualmente.
     */
    private SubjectDTO temaSeleccionado;
    
    /**
     * Lista de temas seleccionados.
     */
    private List<SubjectDTO> temasSeleccionadosVarios;
    
    /**
     * Inicializa el bean. Carga la lista de temas al iniciar la sesión.
     */
    @PostConstruct
    public void init() {
        this.temasEnTabla = new ArrayList<>();
        this.temasEnTabla = subjectService.getSubjects();
        this.temasSeleccionadosVarios = new ArrayList<SubjectDTO>();
    }
    
    /**
     * Abre un nuevo formulario para agregar un tema.
     */
    public void openNew() {
        this.temaSeleccionado = new SubjectDTO();
    }
    
    /**
     * Guarda un tema en la base de datos.
     */
    public void saveSubject() {
        if (this.temaSeleccionado.getId() == 0) {
            this.temaSeleccionado.setId(0);
            subjectService.create(temaSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tema agregado"));
        } else {
            subjectService.update(temaSeleccionado.getId(), temaSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tema actualizado"));
        }
        this.temasEnTabla = subjectService.getSubjects();
        PrimeFaces.current().executeScript("PF('manageSubjectDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Subjects");
    }

    /**
     * Elimina un tema seleccionado.
     */
    public void deleteSubject() {
        subjectService.delete(this.temaSeleccionado.getId());
        this.temasSeleccionadosVarios.remove(this.temaSeleccionado);
        this.temaSeleccionado = null;
        this.temasEnTabla = subjectService.getSubjects();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tema eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Subjects");
        PrimeFaces.current().executeScript("PF('dtSubjects').clearFilters()");
    }
    
    /**
     * Obtiene el mensaje para el botón de eliminar.
     */
    public String getDeleteButtonMessage() {
        if (hasSelectedSubjects()) {
            int size = this.temasSeleccionadosVarios.size();
            return size > 1 ? size + " temas seleccionados" : "1 tema seleccionado";
        }
        return "Eliminado";
    }
    
    /**
     * Verifica si hay temas seleccionados.
     */
    public boolean hasSelectedSubjects() {
        return this.temasSeleccionadosVarios != null && !this.temasSeleccionadosVarios.isEmpty();
    }
    
    /**
     * Elimina varios temas seleccionados.
     */
    public void deleteSelectedSubjects() {
        for (SubjectDTO p : temasSeleccionadosVarios) {
            subjectService.delete(p.getId());
        }
        this.temasSeleccionadosVarios = null;
        this.temasEnTabla = subjectService.getSubjects();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Temas eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Subjects");
        PrimeFaces.current().executeScript("PF('dtSubjects').clearFilters()");
    }
    
    public List<String> getAllSubjectNames() {
        List<String> subjectNames = new ArrayList<>();
        for (SubjectDTO subject : temasEnTabla) {
            subjectNames.add(subject.getName());
        }
        return subjectNames;
    }
    
    // Métodos getters y setters
    
    /**
     * Obtiene la lista de temas.
     */
    public List<SubjectDTO> getSubjects() {
        return temasEnTabla;
    }
    
    /**
     * Establece la lista de temas.
     */
    public void setSubjects(List<SubjectDTO> Subjects) {
        this.temasEnTabla = Subjects;
    }
    
    /**
     * Obtiene el tema seleccionado.
     */
    public SubjectDTO getSelectedSubject() {
        return temaSeleccionado;
    }
    
    /**
     * Establece el tema seleccionado.
     */
    public void setSelectedSubject(SubjectDTO selectedSubject) {
        this.temaSeleccionado = selectedSubject;
    }
    
    /**
     * Obtiene la lista de temas seleccionados.
     */
    public List<SubjectDTO> getSelectedSubjects() {
        return temasSeleccionadosVarios;
    }
    
    /**
     * Establece la lista de temas seleccionados.
     */
    public void setSelectedSubjects(List<SubjectDTO> selectedSubjects) {
        this.temasSeleccionadosVarios = selectedSubjects;
    }
    
    /**
     * Obtiene el serialVersionUID.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}

