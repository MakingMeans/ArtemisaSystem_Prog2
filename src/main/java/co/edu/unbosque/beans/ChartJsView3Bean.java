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

@Named("chartJsView3Bean")
@RequestScoped
public class ChartJsView3Bean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PieChartModel pieModel;
	private CodeDAO codeDAO;

	@PostConstruct
	public void init() {
		createPieModel();
		
	}

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

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
				"Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

}
