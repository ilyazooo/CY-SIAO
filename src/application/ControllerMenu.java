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

/**
 * The controller class for the main menu in the application.
 * It handles user actions and navigation between different views.
 */
public class ControllerMenu {
	
	@FXML
	private Text titleMenu;
	

    /**
     * Handles the event when the user clicks the "Search" button.
     * Closes the current window and opens the search window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void search(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for manageRooms
			AnchorPane root = FXMLLoader.load(getClass().getResource("Search.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	  /**
     * Handles the event when the user clicks the "Manage Beds" button.
     * Closes the current window and opens the manage beds window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void manageBeds(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for manageRooms
			AnchorPane root = FXMLLoader.load(getClass().getResource("MenuManageBeds.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	

    /**
     * Handles the event when the user clicks the "Manage Rooms" button.
     * Closes the current window and opens the manage rooms window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void manageRooms(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for manageRooms
			AnchorPane root = FXMLLoader.load(getClass().getResource("MenuManageRooms.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	  /**
     * Handles the event when the user clicks the "Register Someone" button.
     * Closes the current window and opens the register window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void registerSomeone(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("Register.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	

    /**
     * Handles the event when the user clicks the "Explore" button.
     * Closes the current window and opens the explore window.
     *
     * @param e the ActionEvent triggered by the button click
     */
	public void explore(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleMenu.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("Explore.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
