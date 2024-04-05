package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import co.edu.unbosque.model.persistence.CodeDAO;
import co.edu.unbosque.model.persistence.SubjectDAO;
import co.edu.unbosque.model.persistence.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * La clase ChartJsView4Bean es un bean de respaldo para la vista que utiliza la biblioteca Chart.js.
 * 
 * Este bean se encarga de generar un gráfico de pastel que muestra el número de usuarios, códigos y temas en la base de datos.
 * 
 * @author DavidG
 * @version 1.0
 */
@Named("chartJsView4Bean")
@RequestScoped
public class ChartJsView4Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private PieChartModel pieModel;
    private UserDAO userDAO;
    private CodeDAO codeDAO;
    private SubjectDAO subjectDAO;

    /**
     * Inicializa el bean después de su construcción.
     */
    @PostConstruct
    public void init() {
        createPieModel();
    }

    /**
     * Crea el modelo de gráfico de pastel.
     */
    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();
        userDAO = new UserDAO();
        codeDAO = new CodeDAO();
        subjectDAO = new SubjectDAO();
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        
        int countUsers = userDAO.readAll().size();
        int countCodes = codeDAO.readAll().size();
        int countSubjects = subjectDAO.readAll().size();

        values.add(countUsers);
        values.add(countCodes);
        values.add(countSubjects);

        dataSet.setData(values);

        List<String> bgColors = Arrays.asList("rgb(255, 99, 132)", "rgb(54, 162, 235)", "rgb(255, 205, 86)"); 
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Usuarios");
        labels.add("Códigos");
        labels.add("Temas");
        data.setLabels(labels);

        pieModel.setData(data);
    }

    /**
     * Maneja el evento de selección de un ítem del gráfico de pastel.
     * 
     * @param event El evento de selección del ítem.
     */
    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento seleccionado",
                "Índice del ítem: " + event.getItemIndex() + ", Índice del conjunto de datos: " + event.getDataSetIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Obtiene el modelo de gráfico de pastel.
     * 
     * @return El modelo de gráfico de pastel.
     */
    public PieChartModel getPieModel() {
        return pieModel;
    }

    /**
     * Establece el modelo de gráfico de pastel.
     * 
     * @param pieModel El modelo de gráfico de pastel.
     */
    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

}
