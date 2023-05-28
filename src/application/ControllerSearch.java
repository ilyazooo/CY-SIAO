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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Controller class for the search functionality of the application.
 * This class handles user interactions and logic related to searching for available beds for a person.
 */
public class ControllerSearch implements Initializable{
	
	 @FXML
	 private ListView<String> ListView1;

	 @FXML
	 private ListView<String> ListView2;

	 @FXML
	 private DatePicker endDateInput;

	 @FXML
	 private Text messageRegister;

	 @FXML
	 private Text messageRegister2;
	 
	 @FXML
	 private DatePicker startDateInput;

	 @FXML
	 private Label titleCorrespondingBed;

	 @FXML
	 private Text titleRegister;
	 
	 @FXML 
	 private Button buttonValidate; 
	 
	 private Button addToSearch;
	 
	 private Button clear;
	 
	 private String personChoice;
	 
	 private String bedChoice;
	 
	 private int idPersonChoice;
	 
	 @FXML
	 private RadioButton radiobutton1, radiobutton2;
	 
	 private HashMap<Integer, String> idRoomAllowed = new HashMap<>();;
	 
	 
	/**
	* Initialize the window.
	*/
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		InitializeDB db = new InitializeDB();
		List<People> peopleList = db.retrieveAllPeople();
		
		String[] people = new String[peopleList.size()];
		
		int i = 0; 
		
		for (People person : peopleList) {
			people[i] = "(" +person.getId()+") - "+person.getFirstName()+ " " + person.getLastName();
			i++;
		}
		
		ListView1.getItems().addAll(people);
		
		ListView2.setVisible(false);
		buttonValidate.setVisible(false);
		titleCorrespondingBed.setVisible(false);
		
	}
	
	
	Set<Integer> peopleForSearch = new HashSet<>();
	
	/**
	* Add a people to the search list.
	*/
	
	public void addToSearch(ActionEvent e) {
		ListView2.getItems().clear();
	    personChoice = ListView1.getSelectionModel().getSelectedItem();
	    

	    if (personChoice == null) {
	        messageRegister.setText("Choose a person!");
	        return;
	    }
	    int id = 0;
	    String name = "";

	    String patternString = "\\((\\d+)\\) - (.+)";
	    Pattern pattern = Pattern.compile(patternString);
	    Matcher matcher = pattern.matcher(personChoice);

	    if (matcher.find()) {
	        String idString = matcher.group(1);
	        id = Integer.parseInt(idString);
	        this.idPersonChoice = id;
	        peopleForSearch.add(id);
	    }
		
	    
	    InitializeDB db = new InitializeDB();
	    List<People> peopleList = db.retrieveAllPeople();
	    List<String> selectedNames = new ArrayList<>();
	    for(Integer personId : peopleForSearch) {
	    	for(People person : peopleList) {
	    		if(personId == person.getId()) {
	    			String selectedName = person.getFirstName()+" "+person.getLastName();
	    			selectedNames.add(selectedName);
	    		}
	    	}
	    }
	    messageRegister2.setText("Selected : "+ selectedNames);
	}
	
	/**
	* Empty the search list.
	*/
	
	public void clear(ActionEvent e) {
		peopleForSearch.clear();
		messageRegister2.setText("");
	}
	
    /**
     * Performs a search for one person and add the reservation to the database for the time of the research.
     * This prevents to propose the same reservation for two different people in the same research.
     */
	
	public void searchForOnePeople (List<Integer> listID, Date startDate, Date endDate) {
		
		if (listID.size() == 0) {
			return;
		}
		
		InitializeDB db = new InitializeDB();
		int id = listID.get(0);
		
	    String name = "";
	    People matchingPerson = null;
	    
	    if(radiobutton2.isSelected()) {
	        List<People> personList = db.retrieveAllPeople();

	        for (People person : personList) {
	            if (person.getId() == id) {
	                matchingPerson = person;
	                name = person.getFirstName() +" " +person.getLastName();
	            }
	        }
	        List<Room> roomList = db.retrieveAllRooms();
	        for (Room room : roomList) {
	            if (matchingPerson.isAllowed(room)) {
	                this.idRoomAllowed.put(room.getId(), room.getTitle());
	            }
	        }
	    }


        ListView2.setVisible(true);
        buttonValidate.setVisible(true);
        titleCorrespondingBed.setVisible(true);

        List<People> personList = db.retrieveAllPeople();

        for (People person : personList) {
            if (person.getId() == id) {
                matchingPerson = person;
                name = person.getFirstName() +" " +person.getLastName();
            }
        }
        
        

        List<Bed> bedList = db.retrieveAllBeds();
        List<BedOccupancy> bedOccupancyList = db.retrieveAllBedOccupancy();
        
        CustomDate cutomStartDate = CustomDate.convertFromUtilDate(startDate);
        CustomDate cutomendDate = CustomDate.convertFromUtilDate(endDate);

        List<String> bedsAllowed = findAvailableBeds(bedList, bedOccupancyList, cutomStartDate, cutomendDate);
        bedsAllowed = Rearrange(bedsAllowed);

        ManageDB mDB = new ManageDB();
        
        if (!bedsAllowed.isEmpty()) {
        	ListView2.getItems().add(name+" :");
            ListView2.getItems().addAll(bedsAllowed);
            messageRegister.setText("");
            
            
    	    for (String bedChoice : bedsAllowed) {
    	        
    	        int idBed = 0;
    	        CustomDate possiblestartDate = null;
    	        CustomDate possibleendDate = null;
    	        
    	        String patternString = "\\((\\d+)\\) - .*\\((\\d{2}/\\d{2}/\\d{4}) to (\\d{2}/\\d{2}/\\d{4})\\)";
    	        Pattern pattern = Pattern.compile(patternString);
    	        Matcher matcher = pattern.matcher(bedChoice);
    	        
    	        if (matcher.find()) {
    	            String idString = matcher.group(1);
    	            idBed = Integer.parseInt(idString);
    	            String startDateString = matcher.group(2);
    	            String endDateString = matcher.group(3);
    	            possiblestartDate = new CustomDate(startDateString);
    	            possibleendDate = new CustomDate(endDateString);
    	        }
    	        
    	        Date startUtilDate = possiblestartDate.toUtilDate();
    	        Date endUtilDate = possibleendDate.toUtilDate();
    	        
    	        mDB.addBedOccupancy(id	, idBed, startUtilDate, endUtilDate);
    	    }		            

        } else {
        	ListView2.getItems().add(name+" :");
        	ListView2.getItems().add("No available beds found");
        }
        
        List<Integer> newListID = new ArrayList<>(listID);
        newListID.remove(0);
        searchForOnePeople (newListID,startDate,endDate);
        
	    for (String bedChoice : bedsAllowed) {
	        
	        int idBed = 0;
	        CustomDate possiblestartDate = null;
	        CustomDate possibleendDate = null;
	        
	        String patternString = "\\((\\d+)\\) - .*\\((\\d{2}/\\d{2}/\\d{4}) to (\\d{2}/\\d{2}/\\d{4})\\)";
	        Pattern pattern = Pattern.compile(patternString);
	        Matcher matcher = pattern.matcher(bedChoice);
	        
	        if (matcher.find()) {
	            String idString = matcher.group(1);
	            idBed = Integer.parseInt(idString);
	            String startDateString = matcher.group(2);
	            String endDateString = matcher.group(3);
	            possiblestartDate = new CustomDate(startDateString);
	            possibleendDate = new CustomDate(endDateString);
	        }
	        
	        Date startUtilDate = possiblestartDate.toUtilDate();
	        Date endUtilDate = possibleendDate.toUtilDate();
	        
	        mDB.removeOccupancy(id, idBed, startUtilDate, endUtilDate);
	    }
}
	
    /**
     * Performs a search for available beds based on the selected person and date range.
     * This method is called when the search button is clicked.
     *
     */
	
	public void search(ActionEvent e) {

	    ListView2.getItems().clear();
	    Set<Integer> peopleForASearch = new HashSet<>(peopleForSearch);

	    if (peopleForASearch.isEmpty()) {
	        messageRegister.setText("Choose a person!");
	        return;
	    }
	    
	    if(!(radiobutton1.isSelected()) && !(radiobutton2.isSelected())) {
	    	messageRegister.setText("Indicate room restriction");
	        return;
	    }
	    
	    boolean validDate = false;

	    if (startDateInput.getValue() == null || endDateInput.getValue() == null) {
	        return;
	    }

	    //Check if the date is valid.
	    LocalDate _startDate = startDateInput.getValue();
	    LocalDate _endDate = endDateInput.getValue();

	    Date startDate = Date.from(_startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    Date endDate = Date.from(_endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	    if (_endDate.isAfter(_startDate)) {
	        validDate = true;
	    } else {
	        validDate = false;
	        messageRegister.setText("Choose a valid date");
	        return;
	    }

	    LocalDate currentDate = LocalDate.now(); // Today's date

	    if (!(_startDate.isBefore(currentDate))) {
	        validDate = true;
	    } else {
	        validDate = false;
	        messageRegister.setText("Choose a future date");
	        return;
	    }
	    
	    if (validDate) {
	    	
		    if(radiobutton1.isSelected()) {
		    	InitializeDB db = new InitializeDB();
		    	
		        List<Room> roomList = db.retrieveAllRooms();
		        
		        for (Room room : roomList) {
		        	this.idRoomAllowed.put(room.getId(), room.getTitle());
		        }
		        
		        for(int oneid : peopleForASearch) {
		            List<People> personList = db.retrieveAllPeople();
		            
		            People matchingPerson;
		            int id = oneid;
		            
		            for (People person : personList) {
		                if (person.getId() == id) {
		                    matchingPerson = person;
		                    for (Room room : roomList) {
		    		            if (!(matchingPerson.isAllowed(room))) {
		    		                this.idRoomAllowed.remove(room.getId(), room.getTitle());
		    		            }
		    		        }
		                }
		            }
		            
	
		        }
		        if (this.idRoomAllowed.isEmpty()) {
		        	messageRegister.setText("We can't find sorry");
		        	return;
		        }
		        
		        
		        //find the room where the max of available places (total of places minus total of the bed occupancy during this period) is hit
		        
		        
		        int maxAvailablePlace = 0;
		        int AvailablePlace = 0;
		        int idMaxRoom = 0;
		        for(int id : this.idRoomAllowed.keySet()) {
		        	AvailablePlace = ManageDB.calculateRoomCapacity(id) - ManageDB.calculateRoomOccupancy(id, startDate, endDate);
		        	if(AvailablePlace >= maxAvailablePlace) {
		        		maxAvailablePlace=AvailablePlace;
		        		idMaxRoom = id;
		        	}
		        }
		        
		        

		        // Remove all keys except for the room where the max of available places is hit
		        this.idRoomAllowed.keySet().retainAll(Collections.singleton(idMaxRoom));
		    }
	    	
	    	CustomDate customStartDate = CustomDate.convertFromUtilDate(startDate);
	    	CustomDate customEndDate = CustomDate.convertFromUtilDate(endDate);
	    	
	    	//Check if this person already made a reservation for this period
	    	
	    	List<Integer> listeId = new ArrayList<>(peopleForASearch);
	    	for(int id : listeId) {
	    		boolean isInRange = false;
	    		List<CustomDate[]> dates = InitializeDB.retrieveAllDatesForSomeone(id);
	    		for (CustomDate[] date: dates) {
	    	        if (date[0].isBefore(customStartDate) && date[1].isAfter(customStartDate)) {
	    	            isInRange = true;
	    	        }

	    	        if (date[1].isAfter(customEndDate) && date[0].isBefore(customEndDate)) {
	    	            isInRange = true;
	    	        }

	    	        if (date[1].isBefore(customEndDate) && date[0].isAfter(customStartDate)) {
	    	            isInRange = true;
	    	        }
	    		}
	    		if(isInRange) {
	    			messageRegister.setText("Someone already made a reservation for this period");
	    			return;
	    		}
	    	}
	    	searchForOnePeople(listeId,startDate,endDate);
	    	
	    }
	    


	}

	/**
	 * Finds and returns an available bed based on the provided parameters.
	 * If a bed is not available for all the date range, we cut the date range in half and make a research for each half.
	 * This is done until a date range is equal to a day and cannot be cut in half.
	 *
	 * @param bedList The list of all beds.
	 * @param bedOccupancyList The list of bed occupancies.
	 * @param this.idRoomAllowed The mapping of room IDs to their titles.
	 * @param startDate The start date of the desired bed occupancy.
	 * @param endDate The end date of the desired bed occupancy.
	 * @return A list of available beds as strings, formatted as "(bedID) - roomTitle (startDate to endDate)".
	 */
	
	private List<String> findAvailableBeds(List<Bed> bedList, List<BedOccupancy> bedOccupancyList, CustomDate startDate, CustomDate endDate) {
	    List<String> bedsAllowed = new ArrayList<>();
	    
	    
	    //Check if a bed is available for all the date range.
	    boolean allBedsAvailable = true;
	    for (Map.Entry<Integer, String> entry : idRoomAllowed.entrySet()) {
	    	int bedID = 0;
	        int idRoom = entry.getKey();
	        String roomTitle = entry.getValue();

	        boolean isAvailable = false;

	        for (Bed bed : bedList) {
	            if (bed.getIdRoom() == idRoom) {
	            	int occupants = 0;
	            	int bedSize = Integer.parseInt(bed.getSize());
	                boolean isOccupied = false;
	                for (BedOccupancy occupancy : bedOccupancyList) {
	                    if (occupancy.getBedId() == bed.getId() && occupancy.isOccupied(startDate.toUtilDate(), endDate.toUtilDate())) {
	                    	occupants += 1;
	                    	if (occupants == bedSize) {
	                    		isOccupied = true;
		                        break;
	                    	}
	                    }
	                }
	                if (!isOccupied) {
	                    isAvailable = true;
	                    bedID = bed.getId();
	                    break;
	                }
	            }
	        }

	        if (isAvailable) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	            String value = "(" + bedID + ") - " + roomTitle + " (" + dateFormat.format(startDate.toUtilDate()) + " to " + dateFormat.format(endDate.toUtilDate()) + ")";
	            bedsAllowed.add(value);
	            return bedsAllowed; // The research is stop when we find a solution.
	        } else {
	            allBedsAvailable = false;
	        }
	    }

	    if (allBedsAvailable) {
	        return bedsAllowed;
	    }


	    if (CustomDate.calculateDateDifference(startDate, endDate) > 1) {

	        CustomDate midDate = CustomDate.calculateMiddleDate(startDate, endDate);

	        // Find a bed for each half of the date range
	        List<String> firstHalfBeds = findAvailableBeds(bedList, bedOccupancyList,  startDate, midDate);
	        List<String> secondHalfBeds = findAvailableBeds(bedList, bedOccupancyList,  midDate.addDays(1), endDate);
	        bedsAllowed.addAll(firstHalfBeds);
	        bedsAllowed.addAll(secondHalfBeds);
	        return bedsAllowed;
	        
	    } else {
	        return bedsAllowed; // Return an empty list if the date range is only a single day
	        }
	}
	
	
	/**
	 * Rearranges the format of the bed occupancy strings in the provided list.
	 *
	 * @param bedsAllowed The list of bed occupancy strings to be rearranged.
	 * @return A new list of bed occupancy strings with the rearranged format.
	 */
	
	public static List<String> Rearrange(List<String> bedsAllowed) {

	    if (bedsAllowed.isEmpty()) {
	        return bedsAllowed;
	    }

	    List<String[]> splitBeds = new ArrayList<>();
		for (int i = 0; i < bedsAllowed.size(); i++) {
			splitBeds.add(splitBedString(bedsAllowed.get(i)));
		}
		concatenateDates(splitBeds);
		List<String> newBedsAllowed = new ArrayList<>();
		for(String[] occupation : splitBeds) {
			newBedsAllowed.add("("+occupation[0]+") - "+occupation[1]+" ("+occupation[2]+" to "+occupation[3]+")");
		}
		return newBedsAllowed;
	}

	
	/**
	 * Concatenates overlapping date ranges in the given list of bed occupations.
	 *
	 * @param splitBeds The list of bed occupations to be processed.
	 * @return The updated list of bed occupations after concatenating overlapping date ranges.
	 */
	public static List<String[]> concatenateDates(List<String[]> splitBeds) {
		for(String[] occupation : splitBeds) {
			for(String[] occupationbis : splitBeds) {
				String dayAfter = new CustomDate(occupation[3]).addDays(1).getDate();
				if ((occupation[3].equals(occupationbis[2]) && occupationbis[0].equals(occupation[0])) || (dayAfter.equals(occupationbis[2]) && occupationbis[0].equals(occupation[0]))) {
					occupation[3] = occupationbis[3];
					splitBeds.remove(occupationbis);
					concatenateDates(splitBeds);
					return(splitBeds);
				}				
			}
		}
		return(splitBeds);
	}

	
	/**
	 * Splits a bed occupancy string into its individual components.
	 *
	 * @param bedString The bed occupancy string to be split.
	 * @return An array containing the bed ID, room title, start date, and end date extracted from the bedString.
	 */
	public static String[] splitBedString(String bedString) {
	    String[] parts = bedString.split(" - ");
	    String bedId = parts[0].substring(parts[0].indexOf("(") + 1, parts[0].indexOf(")"));
	    String roomTitle = parts[1].substring(0, parts[1].indexOf("(")).trim();
	    String startDate = parts[1].substring(parts[1].indexOf("(") + 1, parts[1].indexOf(" to "));
	    String endDate = parts[1].substring(parts[1].lastIndexOf(" to ") + 4, parts[1].lastIndexOf(")"));

	    return new String[]{bedId, roomTitle, startDate, endDate};
	}

	/**
	 * Validates and saves the selected bed occupations in the database.
	 *
	 * 
	 */
	public void validateOccupation(ActionEvent e) {
		List<Integer> listeId = new ArrayList<>(this.peopleForSearch);
	    List<String> bedChoicesAndNames = ListView2.getItems(); // Get all of the object in the listview
	    
	    
	    HashMap<String,Integer> idOccupantandBed = new HashMap<>();
	    int IteratorID = -1;
	    int reservation = 0;
	    
	    for (String bedAndNames : bedChoicesAndNames) {
	    	reservation += 1;
	    	if(bedAndNames.charAt(0) == '(') {
	    		idOccupantandBed.put(bedAndNames+reservation,listeId.get(IteratorID));
	    	}
	    	else {
	    		if(!(bedAndNames.equals("No available beds found"))) {
	    			IteratorID++;
	    		}
	    	}
	    }
	    
	    peopleForSearch.clear(); //Set is empty for future search
	    
	    if(idOccupantandBed.isEmpty()) {
	    	messageRegister.setText("No available beds found");
		    ListView2.setVisible(false);
		    buttonValidate.setVisible(false);
		    titleCorrespondingBed.setVisible(false);
	    	return;
	    }
	    
	    
	    ManageDB mDB = new ManageDB();
	    
	    for (String bedChoice : idOccupantandBed.keySet()) {
	        
	        int idBed = 0;
	        CustomDate startDate = null;
	        CustomDate endDate = null;
	        
	        String patternString = "\\((\\d+)\\) - .*\\((\\d{2}/\\d{2}/\\d{4}) to (\\d{2}/\\d{2}/\\d{4})\\)";
	        Pattern pattern = Pattern.compile(patternString);
	        Matcher matcher = pattern.matcher(bedChoice);
	        
	        if (matcher.find()) {
	            String idString = matcher.group(1);
	            idBed = Integer.parseInt(idString);
	            String startDateString = matcher.group(2);
	            String endDateString = matcher.group(3);
	            startDate = new CustomDate(startDateString);
	            endDate = new CustomDate(endDateString);
	        }
	        
	        Date startUtilDate = startDate.toUtilDate();
	        Date endUtilDate = endDate.toUtilDate();
	        
	        mDB.addBedOccupancy(idOccupantandBed.get(bedChoice), idBed, startUtilDate, endUtilDate);
	    }
	    
	    messageRegister.setText("Beds successfully reserved");
	    messageRegister2.setText("");
	    
	    ListView2.setVisible(false);
	    buttonValidate.setVisible(false);
	    titleCorrespondingBed.setVisible(false);
	}




	
	/**
     * Handles the event when the user clicks the "Return to Menu" button.
     * Closes the current window and returns to the main menu.
     *
     *
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