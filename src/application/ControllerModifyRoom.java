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
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


/**
 * Controller class for modifying a room in the application.
 */
public class ControllerModifyRoom implements Initializable{
	
	@FXML
    private TextField ageMax;

    @FXML
    private TextField ageMin;

    @FXML
    private Button buttonValidate;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Text messageRegister;

    @FXML
    private TextField numberBeds;

    @FXML
    private RadioButton rButton1;

    @FXML
    private RadioButton rButton2;

    @FXML
    private RadioButton rButton3;

    @FXML
    private Text titleModifyRoom;

    @FXML
    private Label titleNewAgeMax;

    @FXML
    private Label titleNewAgeMin;

    @FXML
    private Label titleNewBedsNumber;

    @FXML
    private Label titleNewGenderRestrictions;

    @FXML
    private Label titleNewName;

    @FXML
    private TextField titleRoom;
	
    private String roomChoice;
    
    private int roomId;
    
    
    /**
     * Initializes the controller class.
     * @param arg0 The URL location of the FXML file.
     * @param arg1 The ResourceBundle used for localization.
     */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	//// INITIALIZE ROOM LIST 
	    	InitializeDB db = new InitializeDB();
			List<Room> roomList = db.retrieveAllRooms();
				
			List<String> rooms = new ArrayList<>();

			for (Room room : roomList) {
				rooms.add(room.getTitle());
			}
				
			choiceBox.getItems().addAll(rooms);
				
			titleNewName.setVisible(false);
			titleNewAgeMin.setVisible(false);
			titleNewAgeMax.setVisible(false);
			titleNewGenderRestrictions.setVisible(false);
			titleNewBedsNumber.setVisible(false);
			buttonValidate.setVisible(false);
			rButton1.setVisible(false);
			rButton2.setVisible(false);
			rButton3.setVisible(false);
			titleRoom.setVisible(false);
			numberBeds.setVisible(false);
			ageMin.setVisible(false);
			ageMax.setVisible(false);
	}
	
	
	 /**
     * Handles the event when a room is selected from the choice box.
     * @param e The ActionEvent representing the event.
     */
	public void chooseRoom(ActionEvent e) {
		
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		
		
		
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
		
		
		int _AgeMin = 0;
		int _AgeMax = 0;
		boolean _isMale = false;
		boolean _isFemale = false;
		boolean _isOther = false;
		int _NumberBeds = 0;
		String _Name = "";
		
		for (Room room : roomList) {
			if(room.getId() == roomId) {
				_AgeMin = room.getAgeMin();
				_AgeMax = room.getAgeMax();
				_NumberBeds = room.getNumberOfBeds();
				_Name = room.getTitle();
				
				Set<Gender> genderRestrictions = room.getGenderRestriction();
				for(Gender gender : genderRestrictions) {
					if(gender == Gender.MALE) {
						_isMale = true;
					}
					if(gender == Gender.FEMALE) {
						_isFemale = true;
					}
					if(gender == Gender.OTHER) {
						_isOther = true;
					}
				}
			}
		}
		
		
		ageMin.setText(String.valueOf(_AgeMin));
		ageMax.setText(String.valueOf(_AgeMax));
		numberBeds.setText(String.valueOf(_NumberBeds));
		titleRoom.setText(_Name);
		
		if(_isMale) {
			rButton1.setSelected(true);
		}else {
			rButton1.setSelected(false);
		}
		
		if(_isFemale) {
			rButton2.setSelected(true);
		}else {
			rButton2.setSelected(false);
		}
		
		if(_isOther) {
			rButton3.setSelected(true);
		}else {
			rButton3.setSelected(false);
		}
		
		
		titleNewName.setVisible(true);
		titleNewAgeMin.setVisible(true);
		titleNewAgeMax.setVisible(true);
		titleNewGenderRestrictions.setVisible(true);
		titleNewBedsNumber.setVisible(true);
		buttonValidate.setVisible(true);
		rButton1.setVisible(true);
		rButton2.setVisible(true);
		rButton3.setVisible(true);
		titleRoom.setVisible(true);
		numberBeds.setVisible(true);
		ageMin.setVisible(true);
		ageMax.setVisible(true);
	}
	
	
	 /**
     * Modifies the selected room with the updated information.
     * @param e The ActionEvent representing the event.
     */
	public void modifyRoom(ActionEvent e) {
		
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		List<People> peopleList = db.retrieveAllPeople();
		List<Bed> bedList = db.retrieveAllBeds();
		List<BedOccupancy> bedOccupancyList = db.retrieveAllBedOccupancy();
		
		List<String> genders = new ArrayList<>();
		
		boolean fieldsFilled = true; 
		boolean nameAllowed = true;
		boolean isGenderRestricted = false;
		boolean isAgeRestricted = false;
		boolean bedsAllowed = true;
		boolean ageAllowed = true;
		
		String roomName = titleRoom.getText();
		
		/// CHECK IF THERE IS NO EMPTY FIELD 
		if(ageMin.getText().length() == 0 || ageMax.getText().length() == 0) {
			fieldsFilled = false; 
		}else {
			isAgeRestricted = true;
		}
		
		if(titleRoom.getText().length() == 0) {
			fieldsFilled = false; 
		}
		
		if(numberBeds.getText().length() == 0) {
			fieldsFilled = false; 
		}
		
		if(Integer.parseInt(numberBeds.getText()) <= 0 ) {
			bedsAllowed = false;
			messageRegister.setText("Number of beds must be greater than 0");
			return;
		}
		
		if(Integer.parseInt(ageMin.getText()) > Integer.parseInt(ageMax.getText())) {
			ageAllowed = false;
			messageRegister.setText("Minimum age must be greater than maximum age");
			return;
		}
		
		
		/// CHECK IF ANOTHER ROOM HAS THE SAME NAME 
		for(Room room : roomList) {
			if(room.getId() != roomId) {
				if(roomName.equals(room.getTitle())) {
					nameAllowed = false;
					messageRegister.setText("Name already used !");
					return;
				}
			}
		}
		
		/// CHECK WHICH RADIO BUTTON IS SELECTED
		if (rButton1.isSelected()) {
			genders.add("0");
			isGenderRestricted = true;
		}
		if (rButton2.isSelected()) {
			genders.add("1");
			isGenderRestricted = true;
		}
		if (rButton3.isSelected()) {
			genders.add("2");
			isGenderRestricted = true;
		}
		if (!(isGenderRestricted)) {
			messageRegister.setText("Choose at least one gender !");
			return;
		}
		
		/// CHECK IF THERE IS NO CONFLICTS BETWEEN BEDOCCUPATIONS AND THE NEW ageMin AND THE NEW ageMAx
		/// ALSO CHECK CONFLICTS WITH NEW GENDER LIST
		boolean isAgeConflict = false;
		boolean isGenderConflict = false;
		int newAgeMin = Integer.parseInt(ageMin.getText());
		int newAgeMax = Integer.parseInt(ageMax.getText());
		for(Bed bed : bedList) {
			for(BedOccupancy bOccupancy : bedOccupancyList) {
				if(bed.getIdRoom() == roomId && bOccupancy.getBedId() == bed.getId() && !bOccupancy.isFinished() && (nameAllowed) && (bedsAllowed) && (ageAllowed)) {
					for(People person : peopleList) {
						if(bOccupancy.getPersonId() == person.getId()) {
							if(person.getAge() < newAgeMin || person.getAge() > newAgeMax) {
								isAgeConflict = true;
								messageRegister.setText("Age conflict with occupants !");
								return;
							}
							int gender = person.getGender().ordinal();
							String genderString = String.valueOf(gender);
							boolean exists = genders.contains(genderString);
							if(!exists) {
								isGenderConflict = true; 
								messageRegister.setText("Gender conflict with occupants !");
								return;
							}
						}
					}
				}
			}
		}
		
		//// CHECK IF THERE IS NO CONFLICT WITH BED NUMBERS 
		boolean isNumberBedConflict = false;
		int newNumberBeds = Integer.parseInt(numberBeds.getText());
		int nbBed = 0;
		 for(Bed bed : bedList) { 	
				if (bed.getIdRoom() == roomId) {
					++nbBed;								//Number of beds already in the room
				}
		 }
		 
		 if (newNumberBeds < nbBed){
			 	isNumberBedConflict = true;
				messageRegister.setText("Conflict with Bed Number !");
				return;
			}
			
		
		 
		String resultGenders = genders.stream().collect(Collectors.joining(","));
		
		if((!isGenderConflict) && (!isAgeConflict) && (!isNumberBedConflict) && fieldsFilled &&isGenderRestricted) {
			ManageDB mDB = new ManageDB();
			mDB.updateRoom(roomId, newNumberBeds, roomName, isAgeRestricted,  isGenderRestricted, newAgeMin, newAgeMax, resultGenders);
			messageRegister.setText("Room succesfully updated !");
			
			titleNewName.setVisible(false);
			titleNewAgeMin.setVisible(false);
			titleNewAgeMax.setVisible(false);
			titleNewGenderRestrictions.setVisible(false);
			titleNewBedsNumber.setVisible(false);
			buttonValidate.setVisible(false);
			rButton1.setVisible(false);
			rButton2.setVisible(false);
			rButton3.setVisible(false);
			titleRoom.setVisible(false);
			numberBeds.setVisible(false);
			ageMin.setVisible(false);
			ageMax.setVisible(false);
			
			InitializeDB db2 = new InitializeDB();
			List<Room> newRoomList = db2.retrieveAllRooms();
				
			List<String> rooms = new ArrayList<>();

			for (Room room : newRoomList) {
				rooms.add(room.getTitle());
			}
			
			choiceBox.getItems().clear();
			choiceBox.getItems().addAll(rooms);
			
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