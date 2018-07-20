package sk.elct.java.user_management.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserManagementApp extends Application {
		public void start(Stage stage) throws Exception {
/*		Button button = new Button("stlač ma!");
//		SysoutHandler handler = new SysoutHandler();		
		button.setOnAction(new EventHandler<ActionEvent>() {         //anonymna vnutorna trieda, pouziva sa preto aby sme nemuseli mat tisic class pre tisic eventov
			
			public void handle(ActionEvent event) {
				System.out.println("Klik");
			}
		});
		AnchorPane rootPane = new AnchorPane();
		rootPane.getChildren().add(button);
		rootPane.setPrefSize(400, 300);*/

		PrvyController controller = new PrvyController();
		
		FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("prvy.fxml"));
		fmxlLoader.setController(controller);
		Parent rootPane = fmxlLoader.load();
			
//		Parent rootPane = FXMLLoader.load(getClass().getResource("prvy.fxml"));
		Scene scene = new Scene(rootPane);
		stage.setTitle("User management" );
		stage.setScene(scene);
		stage.show();
		}
	
	public static void main(String[] args) {
		launch(args); 
	}
}
