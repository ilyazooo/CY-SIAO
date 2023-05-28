package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderWidths;


/**
 * Controller class for occupation planning in the application.
 */

public class ControllerOccupationPlanning implements Initializable {
	
	@FXML
	private Button goBack;
	
	@FXML
	private Button bedPlanning;

	LocalDate dateFocus;
    LocalDate today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private ListView<String> listViewBed;
    
    @FXML
    private Text messageRegister2;

    @FXML
    private Label titleChooseBed;
    
    @FXML
    private ChoiceBox<String> boite;

    @FXML
    private Text titleDeleteBed;
    
    private String bedChoice; 
    
    private People matchingPerson;
    @FXML
    private FlowPane calendar;

    
    /**
     * Initializes the controller class.
     *
     * @param url            The URL location of the FXML file.
     * @param resourceBundle The ResourceBundle used for localization.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	bedPlanning.setVisible(false);
    	messageRegister2.setVisible(false);
    	
        dateFocus = LocalDate.now();
        today = LocalDate.now();
    	
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
		listViewBed.setVisible(false);
		
		boite.getItems().addAll(rooms);
		
        drawCalendar();
    }

    /**
     * Moves the calendar back by one month.
     *
     * @param event The ActionEvent representing the event.
     */
    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    
    /**
     * Moves the calendar forward by one month.
     *
     * @param event The ActionEvent representing the event.
     */
    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }
    
    

    /**
     * Searches for available beds based on the selected room.
     *
     * @param e The ActionEvent representing the event.
     */
    public void searchBed(ActionEvent e) {
    	
		listViewBed.getItems().clear();
		
		InitializeDB db = new InitializeDB();
		List<Room> roomList = db.retrieveAllRooms();
		List<Bed> bedList = db.retrieveAllBeds();
		List<String> newBedList = new ArrayList<>();
		
		String roomChoice = "";
		int roomId = 0;
		
		roomChoice = boite.getValue();
		
		if(roomChoice != null) {
			messageRegister2.setVisible(false);
			for(Room room : roomList) {
				if(roomChoice.equals(room.getTitle())) {
					roomId = room.getId();
				}
			}
		}else {
			messageRegister2.setVisible(true);
			return;
		}
		
		for (Bed bed : bedList) {
			if(bed.getIdRoom() == roomId) {
				String value = "(" +bed.getId()+") - "+ roomChoice;
				newBedList.add(value);
			}
		}
		listViewBed.getItems().addAll(newBedList);
		bedPlanning.setVisible(true);
		titleChooseBed.setVisible(true);
		listViewBed.setVisible(true);
    }
    
    
    /**
     * Shows the planning for the selected bed.
     *
     * @param e The ActionEvent representing the event.
     */
    public void showBedPlanning(ActionEvent e) {
    	
    	bedChoice = listViewBed.getSelectionModel().getSelectedItem();
    	if (bedChoice == null) {
    		messageRegister2.setText("Please choose a bed");
    		messageRegister2.setVisible(true);
			return;
    	}else {
    		drawCalendar();
    	}
    }

    
    /**
     * Draws the calendar with bed occupancy information.
     */
	private void drawCalendar(){
    	
    	calendar.getChildren().clear();
    	messageRegister2.setVisible(false);
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));
        
        InitializeDB db = new InitializeDB();
		List<People> personList = db.retrieveAllPeople();
		List<BedOccupancy> listBedOccupancy = db.retrieveAllBedOccupancy();

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();


        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1).getDayOfWeek().getValue();
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {

                VBox rectangle = new VBox();
                rectangle.setAlignment(Pos.CENTER);
                rectangle.setStyle("-fx-background-color: transparent; -fx-border-color: black;");

                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setPrefWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setPrefHeight(rectangleHeight);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        rectangle.getChildren().add(date);
                        if (bedChoice != null) {

                			int idBed = 0;
                		
                			String patternString = "\\((\\d+)\\)";
                			Pattern pattern = Pattern.compile(patternString);
                			Matcher matcher = pattern.matcher(bedChoice);
                		
                			if (matcher.find()) {
                				String idString = matcher.group(1);
                				idBed = Integer.parseInt(idString);
                			}
                			
                			for(BedOccupancy bOccupancy : listBedOccupancy) {
                				if (listBedOccupancy.size() > 0) {
                					if (bOccupancy.getBedId() == idBed) {
                				
                						Date sDate = bOccupancy.getStartDate();
                						Date eDate = bOccupancy.getEndDate();
                					
                						int annee = dateFocus.getYear();
                						int mois = dateFocus.getMonthValue();
                						LocalDate _dateRectangle = LocalDate.of(annee,mois,currentDate);
                						Date dateRectangle  = Date.from(_dateRectangle.atStartOfDay(ZoneId.systemDefault()).toInstant());
                					
                						if ((dateRectangle.before(eDate) || dateRectangle.equals(eDate)) && (dateRectangle.after(sDate) || dateRectangle.equals(sDate))) {
                							rectangle.setStyle("-fx-background-color: #4474c5; -fx-border-color: white;");
                							date.setFill(Color.WHITE);
                							
                							int idPerson = bOccupancy.getPersonId();
                							int k = 0;
                							while (personList.get(k).getId() != idPerson) {
                								k+=1;
                							}
                							
                							matchingPerson = personList.get(k);

                							String _lastName = matchingPerson.getLastName();
                							if (_lastName.length() > 5) {
                								_lastName = _lastName.substring(0,5)+".";
                								
                						
                							}
                							String _finalOccupancy = "";
                							Date finalDate = db.retrieveMaxDateForSomeone(matchingPerson.getId());
                							if (eDate.equals(finalDate)) {
                								_finalOccupancy += "(F)";
                							}
	                						Text name = new Text(_lastName + " ("+ matchingPerson.getId()+") "+_finalOccupancy);
	                						name.setFill(Color.WHITE);
	                						rectangle.getChildren().add(name);
	                						
                						}
                					}
                				}
                			}
                		}
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                    	
                    	String currentStyle = rectangle.getStyle();
                    	String newStyle = currentStyle + "-fx-border-color: red; -fx-border-width: 2;";
                    	rectangle.setStyle(newStyle);
                    }
                }
                calendar.getChildren().add(rectangle);
            }
        }
    }

    /**
     * Handles the action of going back to the previous screen.
     *
     * @param e The ActionEvent representing the event.
     */
    public void goBack(ActionEvent e) {
		try {
			/// Close previous MENU
			Stage stage = (Stage) goBack.getScene().getWindow();
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
	}}
