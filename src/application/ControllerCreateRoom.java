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
import java.util.HashSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * The ControllerCreateRoom class is a controller for the Create Room view in the accommodation management system.
 * It handles the logic and actions related to creating a new room.
 */

public class ControllerCreateRoom implements Initializable{
	
	@FXML
    private TextField ageMax;

    @FXML
    private TextField ageMin;
    
    @FXML
    private TextField numberBeds;

    @FXML
    private Text messageRegister;

    @FXML
    private RadioButton rButton1;

    @FXML
    private RadioButton rButton2;

    @FXML
    private RadioButton rButton3;

    @FXML
    private Text titleRegister;

    @FXML
    private TextField titleRoom;
	
    /**
     * Initializes the controller.
     *
     * @param arg0 the URL to the FXML file
     * @param arg1 the resource bundle
     */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
    /**
     * Creates a new room based on the entered information.
     *
     * @param e the action event
     */
	
	public void createRoom(ActionEvent e) {
		
		
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		
		List<String> genders = new ArrayList<>();
		
		boolean fieldsFilled = true; 
		boolean nameAllowed = true;
		boolean isGenderRestricted = false;
		boolean isAgeRestricted = false;
		boolean bedsAllowed = true;
		boolean ageAllowed = true;
		
		String roomName = titleRoom.getText();
		
		
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
		
		for(Room room : roomList) {
			if(roomName.equals(room.getTitle())) {
				nameAllowed = false;
				messageRegister.setText("Name already used !");
				return;
			}
		}
		
		
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
		
		String resultGenders = genders.stream().collect(Collectors.joining(","));

		if((fieldsFilled) && (nameAllowed) && (bedsAllowed) && (ageAllowed)&&(isGenderRestricted)) {
			ManageDB mDB = new ManageDB();
			mDB.addRoom(Integer.parseInt(numberBeds.getText()), titleRoom.getText(), isAgeRestricted,  isGenderRestricted, Integer.parseInt(ageMin.getText()), Integer.parseInt(ageMax.getText()), resultGenders);
			messageRegister.setText("Room succesfully created !");
			numberBeds.setText("");
			titleRoom.setText("");
			ageMin.setText("");
			ageMax.setText("");
			rButton1.setSelected(false);
			rButton2.setSelected(false);
			rButton3.setSelected(false);					//Everything is cleared to avoid spam
		}else {
			messageRegister.setText("Please fill in all the fields !");
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