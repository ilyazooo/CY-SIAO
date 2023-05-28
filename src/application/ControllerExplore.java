package application;

import javafx.collections.ObservableList;
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

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


	/**
	
	The ControllerExplore class is the controller for the Explore view in the application.
	It implements the Initializable interface to initialize the view and handle user actions.
	The Explore view displays information about rooms, bed occupancy, and people.
	*/

public class ControllerExplore implements Initializable{
	
	
	@FXML
	private VBox VBox1;
	
	@FXML
	private Text titleExplore;
	
	@FXML
    private TableColumn<Bed, Integer> bedId;

    @FXML
    private TableColumn<Bed, Integer> occupantID;

    @FXML
    private TableColumn<Bed, Integer> roomId;

    @FXML
    private TableColumn<Bed, String> size;

    @FXML
    private TableView<Bed> tableVIew1;
    
    
    /// Table VIEW 2
    @FXML
    private TableColumn<People, Date> birthDate;

    @FXML
    private TableColumn<People, String> firstName;

    @FXML
    private TableColumn<People, Gender> gender;

    @FXML
    private TableColumn<People, Integer> idPerson;

    @FXML
    private TableColumn<People, String> lastName;

    @FXML
    private TableView<People> tableVIew2;

    
    /**
     * Initializes the Explore view.
     * Retrieves data from the database and populates the view with room information and bed occupancy.
     * Binds data to the table view for beds and people.
     */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		VBox1.getChildren().clear();
		InitializeDB db = new InitializeDB();
		
		List<Room> rooms = db.retrieveAllRooms();
		List<BedOccupancy> bedOccupancyList = db.retrieveAllBedOccupancy();
		List<Bed> BedList = db.retrieveAllBeds();
		
		for (Room room : rooms) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoomTemplate.fxml"));
                HBox cell = fxmlLoader.load();
                Text nameText = (Text) fxmlLoader.getNamespace().get("text1");
                nameText.setText(room.getTitle());
                
                ProgressBar progressBar = (ProgressBar) fxmlLoader.getNamespace().get("progressBar1");
                
                double percentageOccupancy = 0;
                double occupations = 0;
                for(BedOccupancy bOccupancy : bedOccupancyList) {
                	for(Bed bed : BedList) {
                		if(bed.getId() == bOccupancy.getBedId() && bed.getIdRoom() == room.getId()) {
                			 Date currentDate = new Date();
                			 if(bOccupancy.isOccupied(currentDate, currentDate)) {
                				 occupations = occupations + 1;
                			 }
        
                		}
                	}
                }
                
                percentageOccupancy = occupations / room.getNumberOfBeds();
                progressBar.setProgress(percentageOccupancy);
                
                if (percentageOccupancy > 0.8) {
                	progressBar.setStyle("-fx-accent: red;");
                } else if (percentageOccupancy >= 0.4 && percentageOccupancy <= 0.8) {
                	progressBar.setStyle("-fx-accent: orange;");
                }
               
                VBox1.getChildren().add(cell);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		bedId.setCellValueFactory(new PropertyValueFactory<Bed, Integer>("id"));
		roomId.setCellValueFactory(new PropertyValueFactory<Bed, Integer>("idRoom"));
		occupantID.setCellValueFactory(new PropertyValueFactory<Bed, Integer>("idOccupant"));
		size.setCellValueFactory(new PropertyValueFactory<Bed, String>("size"));
		
		
		List<Bed> beds = db.retrieveAllBeds();
		ObservableList<Bed> bedList = tableVIew1.getItems();
		
		for(Bed bed : beds) {
			try {
               bedList.add(bed);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		
		tableVIew1.setItems(bedList);
		
		idPerson.setCellValueFactory(new PropertyValueFactory<People, Integer>("id"));
		firstName.setCellValueFactory(new PropertyValueFactory<People, String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<People, String>("lastName"));
		birthDate.setCellValueFactory(new PropertyValueFactory<People, Date>("dateOfBirth"));
		gender.setCellValueFactory(new PropertyValueFactory<People, Gender>("gender"));
		
		List<People> people = db.retrieveAllPeople();
		ObservableList<People> peopleList = tableVIew2.getItems();
		
		for(People person : people) {
			try {
               peopleList.add(person);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}

		tableVIew2.setItems(peopleList);
		
	}
	
	
	/**
	 * Handles the event when the user clicks the "Return to Menu" button.
	 * Closes the current window and opens the main menu window.
	 * 
	 * @param e the ActionEvent triggered by the button click
	 */
	public void returnMenu(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleExplore.getScene().getWindow();
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
	 * Handles the event when the user clicks the "Occupation Planning" button.
	 * Closes the current window and opens the occupation planning window.
	 * 
	 * @param e the ActionEvent triggered by the button click
	 */
	public void occupationPlanning(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) titleExplore.getScene().getWindow();
			stage.close();
			
			/// Create new stage for register
			AnchorPane root = FXMLLoader.load(getClass().getResource("OccupationPlanning.fxml"));
			Stage registerStage = new Stage();
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}