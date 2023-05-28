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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;



/**
 * The controller class for modifying a bed in the application.
 * It handles user actions and updates the bed details in the database.
 */
public class ControllerModifyBed implements Initializable{
	

    @FXML
    private Button buttonValidate;

    @FXML
    private ChoiceBox<String> choiceBoxBed;

    @FXML
    private Text messageRegister;

    @FXML
    private ChoiceBox<String> choiceBoxRoom;

    @FXML
    private Label titleRoom;
    
    @FXML
    private Label titleBed;
	
    private String bedChoice;
    
    private String roomChoice;
    
    private int bedId;
    
    private int roomId;
    
    
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
	//// INITIALIZE BED LIST 
	    	InitializeDB db = new InitializeDB();
	    	 List<Room> roomList = db.retrieveAllRooms();
			 List<Bed> bedList = db.retrieveAllBeds();
			 List<BedOccupancy> bedOccupancyList = db.retrieveAllBedOccupancy();
			 List<String> bedsAllowed = new ArrayList<>();
			 
			 
			 for(Room room : roomList) { 	
					for (Bed bed : bedList) {
						if (bed.getIdRoom() == room.getId()) {	
								boolean isOccupied = false; 
								for(BedOccupancy occupancy : bedOccupancyList) {
									if(occupancy.getBedId() == bed.getId() && (!occupancy.isFinished())) {
										isOccupied = true;
									}
								}
								if(!isOccupied) {
									String value = "(" +bed.getId()+") - "+ room.getTitle();
									bedsAllowed.add(value);
								}
						}
					}
			 }
			 
				
			choiceBoxBed.getItems().addAll(bedsAllowed);
				
			titleRoom.setVisible(false);
			choiceBoxRoom.setVisible(false);
			buttonValidate.setVisible(false);
			
	}
	
	/**
	 * Handles the event when the user selects a bed from the choice box.
	 * Retrieves the selected bed's ID and displays the available rooms in another choice box.
	 *
	 * @param e the ActionEvent triggered by the selection of a bed
	 */
	public void chooseBed(ActionEvent e) {
		
		InitializeDB db = new InitializeDB();
		List<Bed> bedList = db.retrieveAllBeds();
		List<Room> roomList = db.retrieveAllRooms();
		List<String> roomsAllowed = new ArrayList<>();
		
		int nbBed = 0;
		int nbMaxOfBed = 0;
			for (Room room  : roomList) {
				nbMaxOfBed = room.getNumberOfBeds();
				for(Bed bed : bedList) { 	
					if (bed.getIdRoom() == room.getId()) {
						++nbBed;								//Number of beds already in the room
					}
					
				}
				if (nbBed < nbMaxOfBed){
					String value = room.getTitle();
					roomsAllowed.add(value);
				}
		}
			
		choiceBoxRoom.getItems().clear();	
		choiceBoxRoom.getItems().addAll(roomsAllowed);	
			
		bedChoice = choiceBoxBed.getValue();
		
		if(bedChoice == null) {
			messageRegister.setText("Please select a bed");
			return;
		}
		
		int id = 0; 
		
		String patternString = "\\((\\d+)\\)";
		Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(bedChoice);
        
        if (matcher.find()) {
            String idString = matcher.group(1);
            id = Integer.parseInt(idString);
            this.bedId = id;
        } 
        
		
		titleRoom.setVisible(true);
		choiceBoxRoom.setVisible(true);
		buttonValidate.setVisible(true);
		
	}
	
	/**
	 * Handles the event when the user clicks the "Validate" button.
	 * Modifies the selected bed's room assignment in the database and updates the bed list.
	 *
	 * @param e the ActionEvent triggered by the button click
	 */
	public void modifyBed(ActionEvent e) {
		
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		List<Bed> bedList = db.retrieveAllBeds();
		
		roomChoice = choiceBoxRoom.getValue();
		
		if(roomChoice != null) {
			for (Room room : roomList) {
				if(roomChoice.equals(room.getTitle())) {
					roomId = room.getId();
				}
			}
		}else {
			messageRegister.setText("Please select a room");
			return;
		}
		
		for (Bed bed : bedList) {
			if (bedId == bed.getId()) {
				ManageDB mDB = new ManageDB();
				mDB.updateBed(bedId, bed.getSize(), 0, roomId);
				messageRegister.setText("Bed succesfully updated !");
				
				
				List<Bed> newBedList = db.retrieveAllBeds();
				List<Room> newRoomList = db.retrieveAllRooms();
				List<BedOccupancy> newBedOccupancyList = db.retrieveAllBedOccupancy();
				
				
				List<String> bedsAllowed = new ArrayList<>();
				
				 for(Room room : newRoomList) { 	
						for (Bed newBed : newBedList) {
							if (newBed.getIdRoom() == room.getId()) {	
									boolean isOccupied = false; 
									for(BedOccupancy occupancy : newBedOccupancyList) {
										if(occupancy.getBedId() == newBed.getId() && (!occupancy.isFinished())) {
											isOccupied = true;
										}
									}
									if(!isOccupied) {
										String value = "(" +newBed.getId()+") - "+ room.getTitle();
										bedsAllowed.add(value);
									}
							}
						}
				 }
				 
				choiceBoxBed.getItems().clear();
				choiceBoxBed.getItems().addAll(bedsAllowed);
				titleRoom.setVisible(false);
				choiceBoxRoom.setVisible(false);
				buttonValidate.setVisible(false);
				
				
			}
		}
	}
	
	
	 /**
     * Returns to the main menu.
     * @param e The ActionEvent representing the event.
     */
	public void returnMenu(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleRoom.getScene().getWindow();
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