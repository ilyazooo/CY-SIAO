
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * The ControllerDeleteRoom class is a controller for the Delete Room view in the accommodation management system.
 * It handles the logic and actions related to deleting a room and its associated beds.
 */

public class ControllerDeleteRoom implements Initializable{
	

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Text messageRegister;

    @FXML
    private Text titleDeleteRoom;
    
    
    private String roomChoice;
	
    
    
    /**
     * Initializes the controller.
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
     * Deletes the selected room and its associated beds.
     *
     * @param e the action event
     */
	
	public void deleteRoom(ActionEvent e) {
		
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		List<Bed> bedList = db.retrieveAllBeds();
		List<BedOccupancy> listBedOccupancy = db.retrieveAllBedOccupancy();
		
		int roomId = 0;
		boolean isOccupied = false; 
		
		roomChoice = choiceBox.getValue();
		
		if(roomChoice != null) {
			for(Room room : roomList) {
				if(roomChoice.equals(room.getTitle())) {
					roomId = room.getId();
				}
			}
		}else {
			messageRegister.setText("Please select a room");
			return;
		}
		
	
		for (Bed bed : bedList) {
			if(bed.getIdRoom() == roomId) {
				for(BedOccupancy bOccupancy : listBedOccupancy) {
					if(bOccupancy.getBedId() == bed.getId() && !bOccupancy.isFinished()) {
						isOccupied = true; 
						messageRegister.setText("This room is occupied");
						return; 
					}
				}
			}
		}
		
		if(!isOccupied) {
			ManageDB mDB = new ManageDB();
        	mDB.deleteRoom(roomId);
        	
        	/// DELETE ALL BED OF THE ROOM
        	for(Bed bed : bedList) {
        		if(bed.getIdRoom() == roomId) {
        			mDB.deleteBed(bed.getId());
        		}
        	}
        	
        	messageRegister.setText("Room successfully deleted");
        	
        	
        	/// WE RESET THE CHOICEBOX WITH THE NEW ROOMS 
        	
        	List<Room> newRoomList = db.retrieveAllRooms();
    		List<String> rooms = new ArrayList<>();

    		for (Room room : newRoomList) {
    			rooms.add(room.getTitle());
    		}
    		
    		choiceBox.getItems().clear();
    		choiceBox.getItems().addAll(rooms);
		}
		
	}
	
	 /**
     * Returns to the main menu view.
     *
     * @param e the action event
     */
	
	public void returnMenu(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleDeleteRoom.getScene().getWindow();
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
	