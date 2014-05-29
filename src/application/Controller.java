package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class Controller implements Initializable {
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	private Button btnCalculate;
	@FXML
	private Button btnModify;
	@FXML
	private TextField txtFormula;
	@FXML
	private TextField txtDomainFrom;
	@FXML
	private TextField txtDomainTo;
	@FXML
	private TextField txtStep;
	@FXML
	private TextField txtXFrom;
	@FXML
	private TextField txtXTo;
	@FXML
	private TextField txtYFrom;
	@FXML
	private TextField txtYTo;
	@FXML
	private TextField txtXAxisDescription;
	@FXML
	private TextField txtYAxisDescription;
	@FXML
	private TextField txtXScale;
	@FXML
	private TextField txtYScale;
	@FXML
	private ComboBox<String> cbStyle;
	@FXML
	private ColorPicker clPicker;

	@FXML
	private LineChart<Number, Number> lc;
	@FXML
	private ListView<Function> funListView;

	ArrayList<Function> functionList = new ArrayList<>();
	Function temp;
	int edit = 0;

	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		if (funListView.getSelectionModel().getSelectedItem() != null) {
			edit = 1;
			btnModify.setDisable(false);
			temp = funListView.getSelectionModel().getSelectedItem();
			txtFormula.setText(funListView.getSelectionModel()
					.getSelectedItem().getFormula());
			txtDomainFrom.setText(Double.toString(funListView
					.getSelectionModel().getSelectedItem().getFrom()));
			txtDomainTo.setText(Double.toString(funListView.getSelectionModel()
					.getSelectedItem().getTo()));
			txtStep.setText(Double.toString(funListView.getSelectionModel()
					.getSelectedItem().getStep()));
			clPicker.setValue(funListView.getSelectionModel().getSelectedItem()
					.getColor());
			cbStyle.setValue(funListView.getSelectionModel().getSelectedItem()
					.getStyle());
		}
	}

	@FXML
	private void btSet() {
		
		
		((NumberAxis) (lc.getXAxis())).setAutoRanging(false);
		((NumberAxis) (lc.getYAxis())).setAutoRanging(false);

		if (!txtXAxisDescription.getText().isEmpty())
			lc.getXAxis().setLabel(txtXAxisDescription.getText());
		else{
			lc.getXAxis().setLabel("");
		}
		if (!txtXFrom.getText().isEmpty())
			((NumberAxis) (lc.getXAxis())).setLowerBound(Double
					.parseDouble(txtXFrom.getText()));
		if (!txtXTo.getText().isEmpty())
			((NumberAxis) (lc.getXAxis())).setUpperBound(Double
					.parseDouble(txtXTo.getText()));

		/*
		 * if(!txtXScale.getText().isEmpty())
		 * ((NumberAxis)(lc.getXAxis())).setScaleX
		 * (Double.parseDouble(txtXScale.getText()));
		 */

		if (!txtYAxisDescription.getText().isEmpty())
			lc.getYAxis().setLabel(txtYAxisDescription.getText());
		else{
			lc.getYAxis().setLabel("");
		}
		if (!txtYFrom.getText().isEmpty())
			((NumberAxis) (lc.getYAxis())).setLowerBound(Double
					.parseDouble(txtYFrom.getText()));
		if (!txtYTo.getText().isEmpty())
			((NumberAxis) (lc.getYAxis())).setUpperBound(Double
					.parseDouble(txtYTo.getText()));

		/*
		 * if(!txtYScale.getText().isEmpty())
		 * ((NumberAxis)(lc.getYAxis())).setScaleY
		 * (Double.parseDouble(txtYScale.getText()));
		 */
	}

	@FXML
	private void btClear() {
		btnModify.setDisable(true);
		edit = 0;
		temp=null;
		functionList.clear();
		lc.getData().clear();
		ObservableList<Function> items = FXCollections
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
	private void btModify() {
		// System.out.println("formula  = "+formula+"\t from = "+from+"\t to = "+to+"\t step = "+step);
		if (edit == 1) {
			// System.out.println("edit mode!");
			functionList.remove(temp);
			edit = 0;
			btnModify.setDisable(true);
		}
		calculate();

	}

	@FXML
	private void btCalculate() throws UnknownFunctionException,
			UnparsableExpressionException {
	
		calculate();

	}

	private void calculate() {
		String formula, sFrom, sTo, sStep;
		double from, to, step;

		((NumberAxis) (lc.getXAxis())).setAutoRanging(true);
		((NumberAxis) (lc.getYAxis())).setAutoRanging(true);

		sFrom = txtDomainFrom.getText();
		sTo = txtDomainTo.getText();
		sStep = txtStep.getText();

		String style = cbStyle.getValue();
		Color cl = clPicker.getValue();
		from = Double.parseDouble(sFrom);
		to = Double.parseDouble(sTo);
		step = Double.parseDouble(sStep);
		formula = txtFormula.getText();

		Function fun;
		StringBuilder styleBuilder = new StringBuilder();

		String lineColor = "#"
				+ Integer.toHexString(clPicker.getValue().hashCode());
		styleBuilder.append("-fx-stroke: " + lineColor + ";");

		switch (style) {
		case "Dotted":
			styleBuilder.append("-fx-stroke-dash-array: 0.1 5;");
			break;
		case "Dashed":
			styleBuilder.append("-fx-stroke-dash-array: 5 5;");
			break;
		case "Solid":

			break;

		}
		
		System.out.println(formula.matches(".*\\?{1}.+\\:{1}.+"));
		if (formula.matches(".*\\?{1}.+\\:{1}.+")) {
			String array[] = formula.split("\\?");
			String array2[] = array[1].split("\\:");
			System.out.println("split " + "\n" + array[0] + "\n" + array2[0]
					+ "\n" + array2[1] + "\n");

			try {
				fun = new Function(formula, array[0], array2[0], array2[1],
						from, to, step, cl, style, styleBuilder);
			} catch (UnknownFunctionException | UnparsableExpressionException e) {
				e.printStackTrace();
				txtFormula.setStyle("-fx-border-color: red;");
				return;
			}
		
		} else {

			try {
				fun = new Function(formula, from, to, step, cl, style,
						styleBuilder);
			} catch (UnknownFunctionException | UnparsableExpressionException e) {
				e.printStackTrace();
				txtFormula.setStyle("-fx-border-color: red;");
				return;
			}
		}
		txtFormula.setStyle("-fx-border-color: blue;");

		functionList.add(fun);
		lc.getData().clear();

		ObservableList<Function> items = FXCollections
				.observableArrayList(functionList);

		funListView.setItems(items);

		for (int i = 0; i < functionList.size(); i++) {
			lc.getData().add(functionList.get(i).getSeries());
			// int nSeries = functionList.size() - 1;
			Set<Node> nodes = lc.lookupAll(".series" + i);

			for (Node n : nodes) {
				n.setStyle(functionList.get(i).getStyleBuilder().toString());
			}
		}
	}
}
