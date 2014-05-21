package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
	private LineChart<Number, Number> lc;
	@FXML
	private ListView<XYChart.Series<Number, Number>> funListView;

	ArrayList<XYChart.Series<Number, Number>> functionList = new ArrayList<>();
	
	@FXML 
	public void handleMouseClick(MouseEvent arg0) {
	    System.out.println("clicked on " + funListView.getSelectionModel().getSelectedItem());
	    functionList.clear();
	}
	
	@FXML
	private void btClear() {
		functionList.clear();
		lc.getData().clear();
		ObservableList<XYChart.Series<Number, Number>> items = FXCollections
				.observableArrayList(functionList);
		funListView.setItems(items);
	}

	@FXML
	private void btExport() {
		WritableImage image = lc.snapshot(new SnapshotParameters(), null);

		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"png files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",
						file);
			} catch (IOException e) {
				// TODO: handle exception here
			}
		}
	}

	@FXML
	private void btCalculate() throws UnknownFunctionException,
			UnparsableExpressionException {
		String formula, sFrom, sTo, sStep;
		double from, to, step;

		sFrom = txtDomainFrom.getText();
		sTo = txtDomainTo.getText();
		sStep = txtStep.getText();

		// lc.setTitle("hmmmm, :DD");

		from = Double.parseDouble(sFrom);
		to = Double.parseDouble(sTo);
		step = Double.parseDouble(sStep);

		double fromTo = to - from;

		formula = txtFormula.getText();

		XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number>();

		series1.setName(formula);
		for (double i = 0; i <= fromTo; i += step) {
			float xVal = (float) (from + i);
			float ret = (float) Calculator.calculate(formula, "x", xVal);

			series1.getData().add(new XYChart.Data<Number, Number>(xVal, ret));

		}
		functionList.add(series1);
		lc.getData().clear();
		ObservableList<XYChart.Series<Number, Number>> items = FXCollections
				.observableArrayList(functionList);

		funListView.setItems(items);
		lc.getData().addAll(functionList);

	}

}
