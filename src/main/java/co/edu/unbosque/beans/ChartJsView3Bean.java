package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import co.edu.unbosque.model.Code;
import co.edu.unbosque.model.persistence.CodeDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * La clase ChartJsView3Bean es un bean de respaldo para la vista que utiliza la biblioteca Chart.js.
 * 
 * Este bean se encarga de generar un gráfico de pastel que muestra la distribución de lenguajes de programación.
 * 
 * @author DavidG
 * @version 1.0
 */
@Named("chartJsView3Bean")
@RequestScoped
public class ChartJsView3Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private PieChartModel pieModel;
    private CodeDAO codeDAO;

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
        codeDAO = new CodeDAO();
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        int countJava = 0;
        int countCpp = 0;
        int countPython = 0;

        for (Code obj : codeDAO.readAll()) {
            String language = obj.getLanguage();
            if (language.equalsIgnoreCase("Java")) {
                countJava++;
            } else if (language.equalsIgnoreCase("C++")) {
                countCpp++;
            } else if (language.equalsIgnoreCase("Python")) {
                countPython++;
            }
        }

        values.add(countJava);
        values.add(countCpp);
        values.add(countPython);

        dataSet.setData(values);

        List<String> bgColors = Arrays.asList("rgb(255, 99, 132)", "rgb(54, 162, 235)", "rgb(75, 192, 192)"); 
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Java");
        labels.add("C++");
        labels.add("Python");
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
