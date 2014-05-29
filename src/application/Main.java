package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass()
					.getResource("main_window.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			
			primaryStage.show();
			primaryStage.setResizable(true);
			primaryStage.setMaxWidth(750);        
		    primaryStage.setMaxHeight(650);
		    primaryStage.setMinWidth(750);
		    primaryStage.setMinHeight(650);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

