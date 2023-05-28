package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;



/**
 * The controller class for the menu to manage rooms in the application.
 * It handles user actions and navigation between different views related to room management.
 */
public class ControllerMenuManageRooms implements Initializable{
	
	@FXML
    private Text titleMenu;
	

	/**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It can be used to initialize components, set initial values, etc.
     *
     * @param arg0      the location used to resolve relative paths for the root object,
     *                  or null if the location is not known
     * @param arg1      the resources used to localize the root object,
     *                  or null if the root object was not localized
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	

	/**
     * Handles the event when the user clicks the "Return to Menu" button.
     * Closes the current window and returns to the main menu.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void returnMenu(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

	/**
     * Handles the event when the user clicks the "Modify Room" button.
     * Closes the current window and opens the modify room window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void modifyRoom(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("ModifyRoom.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	

	/**
     * Handles the event when the user clicks the "Create Room" button.
     * Closes the current window and opens the create room window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void createRoom(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("CreateRoom.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
     * Handles the event when the user clicks the "Delete Room" button.
     * Closes the current window and opens the delete room window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void deleteRoom(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("DeleteRoom.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	
}