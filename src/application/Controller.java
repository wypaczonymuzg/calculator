package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class Controller implements Initializable{
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Calculator.initFunctions();
	}
	
	
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
	private ListView<Function> funListView;

	ArrayList<Function> functionList = new ArrayList<>();
	Function temp;
	int edit = 0;

	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		if (funListView.getSelectionModel().getSelectedItem() != null) {
			edit = 1;
			temp = funListView.getSelectionModel().getSelectedItem();
			txtFormula.setText(funListView.getSelectionModel()
					.getSelectedItem().getExpression());
			txtDomainFrom.setText(Double.toString(funListView
					.getSelectionModel().getSelectedItem().getFrom()));
			txtDomainTo.setText(Double.toString(funListView.getSelectionModel()
					.getSelectedItem().getTo()));
			txtStep.setText(Double.toString(funListView.getSelectionModel()
					.getSelectedItem().getStep()));
		}
	}

	@FXML
	private void btClear() {
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
		formula = txtFormula.getText();
	
		
		
			// System.out.println("formula  = "+formula+"\t from = "+from+"\t to = "+to+"\t step = "+step);
			if (edit == 1) {
				// System.out.println("edit mode!");
				functionList.remove(temp);
				edit = 0;
			}
			Function fun;
			try{
				fun = new Function(formula, from, to, step);
			}
			catch(UnknownFunctionException | UnparsableExpressionException e){
				
		      e.printStackTrace();
		      txtFormula.setStyle("-fx-border-color: red;");
				return;
			}
		      txtFormula.setStyle("-fx-border-color: blue;");
			 
		
			functionList.add(fun);
			lc.getData().clear();
			ObservableList<Function> items = FXCollections
					.observableArrayList(functionList);

			funListView.setItems(items);

			for (int i = 0; i < functionList.size(); i++) {
				lc.getData().add(functionList.get(i).getSeries());
			}

		

	}

	
}
