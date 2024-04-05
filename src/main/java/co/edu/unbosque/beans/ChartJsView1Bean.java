package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.persistence.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * La clase ChartJsView1Bean es un bean de respaldo para la vista que utiliza la biblioteca Chart.js.
 * 
 * Este bean se encarga de generar un gráfico de pastel que muestra la cantidad de usuarios por semestre.
 * 
 * @author DavidG
 * @version 1.0
 */
@Named("chartJsView1Bean")
@RequestScoped
public class ChartJsView1Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private PieChartModel pieModel;
    private UserDAO userDAO;

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
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            int count = 0;
            for (User obj : userDAO.readAll()) {
                if (obj.getSemester() == (i + 1)) {
                    count++;
                }
            }
            values.add(count);
        }
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(235, 64, 52)");
        bgColors.add("rgb(39, 230, 32)");
        bgColors.add("rgb(230, 32, 197)");
        bgColors.add("rgb(224, 128, 18)");
        bgColors.add("rgb(45, 162, 235)");
        bgColors.add("rgb(67, 205, 86)");
        bgColors.add("rgb(33, 99, 132)");
        bgColors.add("rgb(22, 162, 235)");
        bgColors.add("rgb(71, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("7");
        labels.add("8");
        labels.add("9");
        labels.add("10");
        labels.add("11");
        labels.add("12");
        labels.add("13");
        labels.add("14");
        labels.add("15");
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
