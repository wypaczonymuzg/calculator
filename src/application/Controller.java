package application;

import javafx.fxml.FXML;
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
	private TextArea txtArea;

	@FXML
	private void btCalculate() throws UnknownFunctionException,
			UnparsableExpressionException {
		String formula, sFrom, sTo;
		double from, to;
		
		sFrom = txtDomainFrom.getText();
		sTo = txtDomainTo.getText();

		from = Double.parseDouble(sFrom);
		to = Double.parseDouble(sTo);
		
		double fromTo = to - from;
		
		formula = txtFormula.getText();
		
		for (double i = 0; i < fromTo; i++) {
			double xVal=from+i;
			double ret = Calculator.calculate(formula, "x", xVal);
			txtArea.appendText("f("+xVal+")="+ret+"\n");
		}

	}

}
