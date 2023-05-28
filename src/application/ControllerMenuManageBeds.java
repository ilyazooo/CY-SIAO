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
 * The controller class for the menu to manage beds in the application.
 * It handles user actions and navigation between different views related to bed management.
 */
public class ControllerMenuManageBeds implements Initializable{
	
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
     * Handles the event when the user clicks the "Modify Bed" button.
     * Closes the current window and opens the modify bed window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void modifyBed(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("ModifyBed.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
     * Handles the event when the user clicks the "Create Bed" button.
     * Closes the current window and opens the create bed window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void createBed(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("CreateBed.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	 /**
     * Handles the event when the user clicks the "Delete Bed" button.
     * Closes the current window and opens the delete bed window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void deleteBed(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("DeleteBed.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	
}