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

import co.edu.unbosque.model.CodeDTO;
import co.edu.unbosque.service.CodeService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * 
 * Bean de Code.
 * 
 * @author WilmerR
 *
 */
@Named("codeBean")
@SessionScoped
public class CodeBean implements Serializable {
	/**
	 * Corresponde al serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Servicio para la gestión de codigos.
     */
    @Inject
    private CodeService codeService;

    /**
     * Lista de codigos mostrados en la tabla.
     */
    private List<CodeDTO> codesInTable;
    
    /**
     * Lista de codigos filtrados en la tabla.
     */
    private List<CodeDTO> certainCodesInTable;

    /**
     * Codigo seleccionado actualmente.
     */
    private CodeDTO selectedCodes;

    /**
     * Lista de codigos seleccionados.
     */
    private List<CodeDTO> selectedCodesVarious;

    /**
     * Inicializa el bean. Carga la lista de codigos al iniciar la sesión.
     */
    @PostConstruct
    public void init() {
        this.codesInTable = new ArrayList<>();
        this.codesInTable = codeService.getCodes();
        this.selectedCodesVarious = new ArrayList<CodeDTO>();
    }

    /**
     * Abre un nuevo formulario para agregar un codigo.
     */
    public void openNew() {
        this.selectedCodes = new CodeDTO();
    }
    
    /**
	 * Crea un pdf sobre todos los registros de este objeto.
	 * @throws com.itextpdf.text.DocumentException
	 */
	public void exportToPDF() throws com.itextpdf.text.DocumentException {
		Document document = new Document();
		try {
			String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")
					+ "CodesHistorial.pdf";
			try {
				PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));
			} catch (com.itextpdf.text.DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			document.open();
			try {
				document.add(new Paragraph("Lista de Codigos\n\n"));
			} catch (com.itextpdf.text.DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (codesInTable != null && !codesInTable.isEmpty()) {
				for (CodeDTO code : codesInTable) {
					try {
						document.add(new Paragraph("ID: " + code.getId()));
	                    document.add(new Paragraph("Lenguaje de programación: " + code.getLanguage()));
	                    document.add(new Paragraph("Contenido: " + code.getContent()));
	                    document.add(new Paragraph("Tema: " + code.getSubjectOf()));
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
     * Guarda un codigo en la base de datos.
     */
    public void saveCode() {
        if (this.selectedCodes.getId() == 0) {
            this.selectedCodes.setId(0);
            codeService.create(selectedCodes);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigo agregado"));
        } else {
            codeService.update(selectedCodes.getId(), selectedCodes);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigo actualizado"));
        }
        this.codesInTable = codeService.getCodes();
        PrimeFaces.current().executeScript("PF('manageCodeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
    }

    /**
     * Elimina un codigo seleccionado.
     */
    public void deleteCode() {
        codeService.delete(this.selectedCodes.getId());
        this.selectedCodesVarious.remove(this.selectedCodes);
        this.selectedCodes = null;
        this.codesInTable = codeService.getCodes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigo eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }

    /**
     * Obtiene el mensaje para el botón de eliminar.
     */
    public String getDeleteButtonMessage() {
        if (hasSelectedCodes()) {
            int size = this.selectedCodesVarious.size();
            return size > 1 ? size + " codigos seleccionados" : "1 codigo seleccionado";
        }
        return "Eliminado";
    }

    /**
     * Verifica si hay codigos seleccionados.
     */
    public boolean hasSelectedCodes() {
        return this.selectedCodesVarious != null && !this.selectedCodesVarious.isEmpty();
    }

    /**
     * Elimina varios codigos seleccionados.
     */
    public void deleteSelectedCodes() {
        for (CodeDTO p : selectedCodesVarious) {
            codeService.delete(p.getId());
        }
        this.selectedCodesVarious = null;
        this.codesInTable = codeService.getCodes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigos eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Codes");
        PrimeFaces.current().executeScript("PF('dtCodes').clearFilters()");
    }
    
    public String getLanguageIcon(String language) {
        if (language.equalsIgnoreCase("C++")) {
            return "assets/c++_icon.png";
        } else if (language.equalsIgnoreCase("Java")) {
            return "assets/java_icon.png";
        } else if (language.equalsIgnoreCase("Python")) {
            return "assets/python_icon.png";
        } else {
            // En caso de que el lenguaje no esté en la lista, puedes devolver una imagen genérica
            return "assets/generic_icon.png";
        }
    }
    
    public void filterCodesBySubjects(List<String> subjects) {
        List<CodeDTO> filteredCodes = new ArrayList<>();
        for (CodeDTO code : codeService.getCodes()) {
            if (subjects.contains(code.getSubjectOf())) {
                filteredCodes.add(code);
            }
        }
        certainCodesInTable = filteredCodes;
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
		.handleNavigation(FacesContext.getCurrentInstance(), null, "code.xhtml");
    }



    /**
     * Obtiene la lista de codigos.
     */
    public List<CodeDTO> getCodes() {
        return codesInTable;
    }

    /**
     * Establece la lista de codigos.
     */
    public void setCodes(List<CodeDTO> Codes) {
        this.codesInTable = Codes;
    }

    /**
     * Obtiene la lista de codigos filtrados.
     */
    public List<CodeDTO> getCertainCodesInTable() {
		return certainCodesInTable;
	}

    /**
     * Establece la lista de codigos filtrados.
     */
	public void setCertainCodesInTable(List<CodeDTO> certainCodesInTable) {
		this.certainCodesInTable = certainCodesInTable;
	}

	/**
     * Obtiene el codigo seleccionado.
     */
    public CodeDTO getSelectedCode() {
        return selectedCodes;
    }

    /**
     * Establece el codigo seleccionado.
     */
    public void setSelectedCode(CodeDTO selectedCode) {
        this.selectedCodes = selectedCode;
    }

    /**
     * Obtiene la lista de codigos seleccionados.
     */
    public List<CodeDTO> getSelectedCodes() {
        return selectedCodesVarious;
    }

    /**
     * Establece la lista de codigos seleccionados.
     */
    public void setSelectedCodes(List<CodeDTO> selectedCodes) {
        this.selectedCodesVarious = selectedCodes;
    }

    /**
     * Obtiene el serialVersionUID.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
