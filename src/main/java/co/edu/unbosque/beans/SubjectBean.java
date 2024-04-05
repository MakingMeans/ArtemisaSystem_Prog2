package co.edu.unbosque.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import co.edu.unbosque.model.SubjectDTO;
import co.edu.unbosque.service.SubjectService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
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
	 * Crea un pdf sobre todos los registros de este objeto.
	 * @throws com.itextpdf.text.DocumentException
	 */
	public void exportToPDF() throws com.itextpdf.text.DocumentException {
		Document document = new Document();
		try {
			String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")
					+ "SubjectsHistorial.pdf";
			try {
				PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));
			} catch (com.itextpdf.text.DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			document.open();
			try {
				document.add(new Paragraph("Lista de temas\n\n"));
			} catch (com.itextpdf.text.DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (temasEnTabla != null && !temasEnTabla.isEmpty()) {
				for (SubjectDTO tema : temasEnTabla) {
					try {
						document.add(new Paragraph("ID: " + tema.getId()));
	                    document.add(new Paragraph("Nombre: " + tema.getName()));
	                    document.add(new Paragraph("Descripción: " + tema.getDesc()));
	                    document.add(new Paragraph("Dificultad: " + tema.getDiff()));
	                    document.add(new Paragraph("Categoría: " + tema.getCategoryOf()));
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
    
    public List<String> getCertainSubjects(String category) {
    	List<String> subjectNames = subjectService.getSubjectNamesByCategory(category);
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

