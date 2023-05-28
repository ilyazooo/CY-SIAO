package application;

import java.sql.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * The ControllerCreateBed class is a controller for the Create Bed view in the accommodation management system.
 * It handles the logic and actions related to creating a new bed.
 */

public class ControllerCreateBed implements Initializable{
	
	@FXML
	private Text titleRegister;
	
	@FXML
	private Text messageRegister;
	
	@FXML
	private ChoiceBox<String> choiceBox;
	
    @FXML
    private RadioButton rButton2, rButton3;
	
    /**
     * Initializes the controller by retrieving all rooms from the database and populating the choice box with room titles.
     *
     * @param arg0 the URL to the FXML file
     * @param arg1 the resource bundle
     */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		
		String[] rooms = new String[roomList.size()];
		
		int i = 0; 
		
		for (Room room : roomList) {
			rooms[i] = room.getTitle();
			i++;
		}
		
		choiceBox.getItems().addAll(rooms);
	}
	
	 /**
     * Creates a new bed based on the selected bed size and room choice.
     *
     * @param e the action event
     */
	
	public void createBed(ActionEvent e) {
		
		
		InitializeDB db = new InitializeDB();
		
		String bedSize = "0";
		String roomChoice = "";
		int roomId = 0;
		boolean isSelected = false;
		
		int nbMaxOfBed = 0;
		
		roomChoice = choiceBox.getValue();
		
		List<Room> roomList = db.retrieveAllRooms();
		
		if(roomChoice != null) {
			for(Room room : roomList) {
				if(roomChoice.equals(room.getTitle())) {
					roomId = room.getId();
					nbMaxOfBed = room.getNumberOfBeds();   //Maximum possible number of beds in the room, defined at the creation of the room
				}
			}
		}
		
		
		
		List<Bed> bedList = db.retrieveAllBeds();
		int nbBed = 0;
		
		
		 for(Bed bed : bedList) { 	
				if (bed.getIdRoom() == roomId) {
					++nbBed;								//Number of beds already in the room
				}
		 }
		
		if (nbBed >= nbMaxOfBed){
			messageRegister.setText("Room already full !");
		}
		
		else {
		
			if (rButton2.isSelected()) {
				bedSize = "1";
				isSelected = true;
			}
			else if (rButton3.isSelected()) {
				bedSize = "2";
				isSelected = true;
			}
		
			if(isSelected && (roomChoice != null)) {
				ManageDB mDB = new ManageDB();
				mDB.addBed(bedSize, roomId);
				messageRegister.setText("Bed succesfully created !");
			}else {
				messageRegister.setText("Please fill in all the fields !");
			}
		
		}
		

		rButton2.setSelected(false);
		rButton3.setSelected(false);
	}
	
	
	 /**
     * Returns to the main menu view.
     *
     * @param e the action event
     */
	
	public void returnMenu(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleRegister.getScene().getWindow();
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
	
	
}