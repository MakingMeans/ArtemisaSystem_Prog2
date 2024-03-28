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

@Named("subjectBean")
@SessionScoped
public class SubjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SubjectService SubjectService;

    private List<SubjectDTO> temasEnTabla;

    private SubjectDTO temaSeleccionado;

    private List<SubjectDTO> temasSeleccionadosVarios;

    @PostConstruct
    public void init() {
        this.temasEnTabla = new ArrayList<>();
        this.temasEnTabla = SubjectService.getSubjects();
        this.temasSeleccionadosVarios = new ArrayList<SubjectDTO>();
    }

    public void openNew() {
        this.temaSeleccionado = new SubjectDTO();
    }

    public void saveSubject() {
        if (this.temaSeleccionado.getId() == 0) {
            this.temaSeleccionado.setId(0);
            SubjectService.create(temaSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tema agregado"));
        } else {
            SubjectService.update(temaSeleccionado.getId(), temaSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tema actualizado"));
        }
        this.temasEnTabla = SubjectService.getSubjects();
        PrimeFaces.current().executeScript("PF('manageSubjectDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Subjects");
    }

    public void deleteSubject() {
        SubjectService.delete(this.temaSeleccionado.getId());
        this.temasSeleccionadosVarios.remove(this.temaSeleccionado);
        this.temaSeleccionado = null;
        this.temasEnTabla = SubjectService.getSubjects();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tema eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Subjects");
        PrimeFaces.current().executeScript("PF('dtSubjects').clearFilters()");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedSubjects()) {
            int size = this.temasSeleccionadosVarios.size();
            return size > 1 ? size + " Temas seleccionados" : "1 Tema seleccionado";
        }
        return "Eliminado";
    }

    public boolean hasSelectedSubjects() {
        return this.temasSeleccionadosVarios != null && !this.temasSeleccionadosVarios.isEmpty();
    }

    public void deleteSelectedSubjects() {
        for (SubjectDTO p : temasSeleccionadosVarios) {
            SubjectService.delete(p.getId());
        }
        this.temasSeleccionadosVarios = null;
        this.temasEnTabla = SubjectService.getSubjects();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Temas eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Subjects");
        PrimeFaces.current().executeScript("PF('dtSubjects').clearFilters()");
    }

    public List<SubjectDTO> getSubjects() {
        return temasEnTabla;
    }

    public void setSubjects(List<SubjectDTO> Subjects) {
        this.temasEnTabla = Subjects;
    }

    public SubjectDTO getSelectedSubject() {
        return temaSeleccionado;
    }

    public void setSelectedSubject(SubjectDTO selectedSubject) {
        this.temaSeleccionado = selectedSubject;
    }

    public List<SubjectDTO> getSelectedSubjects() {
        return temasSeleccionadosVarios;
    }

    public void setSelectedSubjects(List<SubjectDTO> selectedSubjects) {
        this.temasSeleccionadosVarios = selectedSubjects;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}

