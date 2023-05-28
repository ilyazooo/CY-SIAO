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
 * The ControllerDeleteBed class is a controller for the Delete Bed view in the accommodation management system.
 * It handles the logic and actions related to deleting a bed from a room.
 */

public class ControllerDeleteBed implements Initializable{
	
    
	@FXML
    private Button buttonDeleteBed;

    @FXML
    private ChoiceBox<String> choiceBox2;

    @FXML
    private ListView<String> listViewBed;

    @FXML
    private Text messageRegister2;

    @FXML
    private Label titleChooseBed;

    @FXML
    private Text titleDeleteBed;
    
    private String bedChoice; 


    /**
     * Initializes the controller.
     *
     * @param arg0 the URL to the FXML file
     * @param arg1 the resource bundle
     */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/// INITIALIZE CREATE ROOM
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		
		String[] rooms = new String[roomList.size()];
		
		int i = 0; 
		
		for (Room room : roomList) {
			rooms[i] = room.getTitle();
			i++;
		}
		
		
		//// INITIALIZE DELETE ROOM
		titleChooseBed.setVisible(false);
		buttonDeleteBed.setVisible(false);
		listViewBed.setVisible(false);
		
		choiceBox2.getItems().addAll(rooms);
	}
	
	

    /**
     * Searches for beds in a selected room and populates the bed list view.
     *
     * @param e the action event
     */
	
	public void searchBed(ActionEvent e) {
		
		listViewBed.getItems().clear();
		
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		List<Bed> bedList = db.retrieveAllBeds();
		List<String> newBedList = new ArrayList<>();
		
		String roomChoice = "";
		int roomId = 0;
		
		roomChoice = choiceBox2.getValue();
		
		if(roomChoice != null) {
			for(Room room : roomList) {
				if(roomChoice.equals(room.getTitle())) {
					roomId = room.getId();
				}
			}
		}else {
			messageRegister2.setText("Please choose a room");
			return;
		}
		
		for (Bed bed : bedList) {
			if(bed.getIdRoom() == roomId) {
				String value = "(" +bed.getId()+") - "+ roomChoice;
				newBedList.add(value);
			}
		}
		
		
		listViewBed.getItems().addAll(newBedList);
		messageRegister2.setText("Choose a bed to delete");
		
		titleChooseBed.setVisible(true);
		buttonDeleteBed.setVisible(true);
		listViewBed.setVisible(true);
		
		
		
		
	}
	

    /**
     * Deletes the selected bed from the database.
     *
     * @param e the action event
     */
	
	public void deleteBed(ActionEvent e) {
		InitializeDB db = new InitializeDB();
		List<BedOccupancy> listBedOccupancy = db.retrieveAllBedOccupancy();
		
		boolean isOccupied = false;
		
		bedChoice = listViewBed.getSelectionModel().getSelectedItem();
		
		
		if(bedChoice == null) {
        	messageRegister2.setText("Choose a bed ! ");
        	return;
        }
		
		int idBed = 0;
		
		String patternString = "\\((\\d+)\\)";
		Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(bedChoice);
		
        if (matcher.find()) {
            String idString = matcher.group(1);
            idBed = Integer.parseInt(idString);
        } 
        
        
        for(BedOccupancy bOccupancy : listBedOccupancy) {
        	if(bOccupancy.getBedId() == idBed && !bOccupancy.isFinished()) {
        		isOccupied = true; 
        		messageRegister2.setText("Bed is already booked");
        		return;
        	}
        }
        
        if(!isOccupied) {
        	ManageDB mDB = new ManageDB();
        	mDB.deleteBed(idBed);
        	messageRegister2.setText("Bed successfully deleted");
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
			Stage stage = (Stage) titleDeleteBed.getScene().getWindow();
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

