package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * La clase ChartJsView2Bean es un bean de respaldo para la vista que utiliza la biblioteca Chart.js.
 * 
 * Este bean se encarga de generar un gráfico de pastel que muestra la fortaleza de las contraseñas de los usuarios.
 * 
 * @author DavidG
 * @version 1.0
 */
@Named("chartJsView2Bean")
@RequestScoped
public class ChartJsView2Bean implements Serializable {

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
        Map<String, Integer> passwordStrengths = new HashMap<>();
        passwordStrengths.put("Vulnerable", 0);
        passwordStrengths.put("Decente", 0);
        passwordStrengths.put("Segura", 0);

        for (User obj : userDAO.readAll()) {
            String password = obj.getPassword();
            if (isWeakPassword(password)) {
                passwordStrengths.put("Vulnerable", passwordStrengths.get("Vulnerable") + 1);
            } else if (isStrongPassword(password)) {
                passwordStrengths.put("Segura", passwordStrengths.get("Segura") + 1);
            } else {
                passwordStrengths.put("Decente", passwordStrengths.get("Decente") + 1);
            }
        }

        values.add(passwordStrengths.get("Vulnerable"));
        values.add(passwordStrengths.get("Decente"));
        values.add(passwordStrengths.get("Segura"));

        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)"); // Color para contraseñas vulnerables
        bgColors.add("rgb(54, 162, 235)"); // Color para contraseñas decentes
        bgColors.add("rgb(75, 192, 192)"); // Color para contraseñas seguras
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        labels.add("Vulnerables");
        labels.add("Decentes");
        labels.add("Seguras");
        data.setLabels(labels);

        pieModel.setData(data);
    }

    /**
     * Verifica si una contraseña es débil.
     * 
     * @param password La contraseña a verificar.
     * @return true si la contraseña es débil, false de lo contrario.
     */
    private boolean isWeakPassword(String password) {
        // Verificar si la longitud de la contraseña es menor que 8 caracteres
        if (password.length() < 8) {
            return true;
        }
        
        // Verificar si la contraseña contiene solo letras o solo números
        if (password.matches("[a-zA-Z]+") || password.matches("[0-9]+")) {
            return true;
        }

        // Otras condiciones que consideres apropiadas para clasificar una contraseña como débil
        // Por ejemplo, contraseñas comunes, contraseñas basadas en información personal, etc.
        
        return false;
    }

    /**
     * Verifica si una contraseña es fuerte.
     * 
     * @param password La contraseña a verificar.
     * @return true si la contraseña es fuerte, false de lo contrario.
     */
    private boolean isStrongPassword(String password) {
        // Verificar si la longitud de la contraseña es al menos 8 caracteres
        if (password.length() < 8) {
            return false;
        }

        // Verificar si la contraseña contiene al menos una letra mayúscula
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Verificar si la contraseña contiene al menos una letra minúscula
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Verificar si la contraseña contiene al menos un dígito
        if (!password.matches(".*[0-9].*")) {
            return false;
        }

        // Verificar si la contraseña contiene al menos un carácter especial
        if (!password.matches(".*[!@#$%^&*()-_=+\\\\|[{]};:'\",<.>/?].*")) {
            return false;
        }

        // Otras condiciones que consideres apropiadas para clasificar una contraseña como fuerte
        
        return true;
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
