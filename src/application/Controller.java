package application;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class Controller {
	@FXML
	private Button btnCalculate;
	@FXML
	private TextField txtFormula;
	@FXML
	private TextField txtDomainFrom;
	@FXML
	private TextField txtDomainTo;
	@FXML
	private TextField txtStep;
	@FXML
	private TextArea txtArea;
	@FXML
	private LineChart<Number,Number> lc;

	@FXML
	private void btCalculate() throws UnknownFunctionException,
		UnparsableExpressionException {
		String formula, sFrom, sTo, sStep;
		double from, to, step;

		sFrom = txtDomainFrom.getText();
		sTo = txtDomainTo.getText();
		sStep = txtStep.getText();

		lc.setTitle("hmmmm, :DD");
		
		from = Double.parseDouble(sFrom);
		to = Double.parseDouble(sTo);
		step = Double.parseDouble(sStep);

		double fromTo = to - from;

		formula = txtFormula.getText();
		
		XYChart.Series series1 = new XYChart.Series();
		
		//dseries1.setName("gowno");
		System.out.println("gowwwwwwwwwwwno");
		for (double i = 0; i < fromTo; i += step) {
			float xVal = (float) (from + i);
			float ret = (float) Calculator.calculate(formula, "x", xVal);
			//txtArea.appendText("f(" + xVal + ")=" + ret + "\n");
			
			series1.getData().add(new XYChart.Data(xVal, ret));
		
		}
		   
	      
	   
	        lc.getData().add(series1);
	        System.out.println("gowwwwwwwwwwwno");
	        
	}

}
