package sk.elct.java.user_management.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.transaction.jta.UserTransactionAdapter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.elct.java.user_management.MysqlUserDao;
import sk.elct.java.user_management.User;
import sk.elct.java.user_management.UserDao;
import sk.elct.java.user_management.DaoFactory;

public class PrvyController {

    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	
    private UserFxModel newUserModel = new UserFxModel();
    
    private ObservableList<User> observableUsers;
 
    private User selectedUser;

    @FXML
    private Label selectedUserLabel;
   
    @FXML
    private TableView<User> usersTableView;
    
    @FXML
    private TextField loginTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button pridatButton;

    @FXML
    private Button stlacMaButton;

    @FXML
    private ListView<User> usersListView;
    
    
    //metoda ktora sa spusti po nakresleni okna
    //obsluha udalosti, naplnenie hodnotami

    @FXML    //FXML tu musi byt, inak to nebude fungovat
    void initialize() {
    	
    	loginTextField.textProperty().bindBidirectional(newUserModel.nameProperty());
    	emailTextField.textProperty().bindBidirectional(newUserModel.emailProperty());
    	
    	pridatButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				userDao.add(newUserModel.getUser());
				observableUsers.setAll(userDao.getAll());
				//vyprazdnenie textfieldu
				newUserModel.setName(null);
				newUserModel.setEmail(null);
				
			}
		});
    	
    	stlacMaButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {				
				System.out.println("Klik na stlac ma!");
		
			}
		});
    	List<User> users = userDao.getAll();
    	observableUsers = FXCollections.observableArrayList(users);
    	usersListView.setItems(observableUsers);
    	
    	TableColumn<User, String> nameColumn = new TableColumn<>("Meno");
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	usersTableView.getColumns().add(nameColumn);
    	
    	TableColumn<User, String> emailColumn = new TableColumn<>("Email");
    	emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    	usersTableView.getColumns().add(emailColumn);
    	
    	usersTableView.setItems(observableUsers);
    	
    	usersTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				
				if(newValue != null) {
				selectedUserLabel.setText(newValue.getName());
				selectedUser = newValue;
				}else {
					selectedUserLabel.setText("nikto");
					selectedUser = null;
				}
				
			}
		});
    	
    	usersTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if( event.getClickCount() == 2) {
					//idem editovat selectedUser
					try {
						EditUserController controller = new EditUserController(selectedUser);
						
						FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("EditUser.fxml"));
						fmxlLoader.setController(controller);
						Parent rootPane = fmxlLoader.load();
							
						Scene scene = new Scene(rootPane);
						Stage stage = new Stage();
						stage.setTitle("Edit user " + selectedUser.getName());
						stage.setScene(scene);
						stage.initModality(Modality.APPLICATION_MODAL);
						stage.showAndWait();
						//toto sa vykona az po zatvoreni vyskoceneho okna
						observableUsers.setAll(userDao.getAll());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
    	
    }
}

