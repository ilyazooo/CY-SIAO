package application;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * The InitializeDB class provides methods for initializing and retrieving data from the database
 * in the accommodation management system.
 */

public class InitializeDB {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL      = "jdbc:mysql://localhost:3306/cysiao";
	private static final String USER        = "root";
	private static final String PASS        = "";
	
	

    /**
     * Constructs a new InitializeDB object.
     */
	
	public InitializeDB(){
		
	}

	
    /**
     * Retrieves all people from the database.
     *
     * @return a list of People objects representing all the people in the database
     */
	
	public List<People> retrieveAllPeople() {
        Connection conn     = null;
        Statement stmt      = null;
        List<People> people = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql   = "SELECT * FROM people";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int personId = rs.getInt("id");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                int genderValue = rs.getInt("gender");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String birthCity = rs.getString("birthCity");
                String socialSecurityNumber = rs.getString("socialSecurityNumber");
                
                
                
                
                Gender gender; 
                if (genderValue == 0) {
                    gender = Gender.MALE;
                } else {
                	if (genderValue == 1) {
                		gender = Gender.FEMALE;
                	} else {
                		gender = Gender.OTHER; 
                	}
                }
                
                People person = new People(personId, lastName, firstName, gender, dateOfBirth, birthCity, socialSecurityNumber);
                people.add(person);

            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        
        return people;
    }
	
    /**
     * Retrieves all beds from the database.
     *
     * @return a list of Bed objects representing all the beds in the database
     */
	
	
	public List<Bed> retrieveAllBeds() {
		
        Connection conn = null;
        Statement stmt  = null;
        List<Bed> beds  = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            conn       = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt       = conn.createStatement();

            String sql   = "SELECT * FROM bed";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int bedId      = rs.getInt("id");
                String bedSize = rs.getString("bed_size");
                int roomId     = rs.getInt("id_room");
                int occupantId = rs.getInt("id_occupant");

                Bed bed = new Bed(bedId, bedSize, roomId, occupantId);
                beds.add(bed);
            }

            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return beds;
    }
	
	   /**
     * Retrieves all bed occupancies from the database.
     *
     * @return a list of BedOccupancy objects representing all the bed occupancies in the database
     */
	
	public List<BedOccupancy> retrieveAllBedOccupancy() {
		
        Connection conn = null;
        Statement stmt  = null;
        List<BedOccupancy> bedOccupancyList  = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            conn       = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt       = conn.createStatement();

            String sql   = "SELECT * FROM bedoccupancy";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int idOccupation = rs.getInt("id");
                int personId = rs.getInt("personId");
                int bedId     = rs.getInt("bedId");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");

                BedOccupancy bedOccupancy = new BedOccupancy(idOccupation, personId, bedId, startDate, endDate);
                bedOccupancyList.add(bedOccupancy);
            }

            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return bedOccupancyList;
    }
	
	public static List<CustomDate[]> retrieveAllDatesForSomeone(int personId) {

	    Connection conn = null;
	    Statement stmt  = null;
	    List<Date> dateList = new ArrayList<>();

	    try {
	        Class.forName(JDBC_DRIVER);
	        conn = DriverManager.getConnection(DB_URL, USER, PASS);
	        stmt = conn.createStatement();

	        String sql = "SELECT startDate, endDate FROM bedoccupancy WHERE personId =" + personId;
	        ResultSet rs = stmt.executeQuery(sql);

	        while (rs.next()) {
	            Date startDate = rs.getDate("startDate");
	            Date endDate = rs.getDate("endDate");

	            dateList.add(startDate);
	            dateList.add(endDate);
	        }

	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    Date[] dateArray = dateList.toArray(new Date[0]);
	    
	    List<CustomDate[]> arrangeCustomDate = new ArrayList<>();
	        
	    
	    	    
	    
	    for (int i = 0; i < dateArray.length; i += 2) {
	    	CustomDate[] startEndDate = {CustomDate.convertFromUtilDate(dateArray[i]),CustomDate.convertFromUtilDate(dateArray[i + 1])};
	    	arrangeCustomDate.add(startEndDate);
	    }
	    
	    
	    arrangeCustomDate = Concatenate(arrangeCustomDate);
	    
	    return arrangeCustomDate;
	}
	
	public static List<CustomDate[]> Concatenate(List<CustomDate[]> arrangeCustomDate) {
	    for(CustomDate[] period1 : arrangeCustomDate) {
	    	for(CustomDate[] period2 : arrangeCustomDate) {
	    		if(period2[1].addDays(1).getDate().equals(period1[0].getDate())) {
	    			period1[0] = period2[0];
	    			arrangeCustomDate.remove(period2);
	    			Concatenate(arrangeCustomDate);
	    			return(arrangeCustomDate);
	    		}
	    	}
	    }
	    return(arrangeCustomDate);
	}

	
	public Date retrieveMaxDateForSomeone(int personId) {

	    Connection conn = null;
	    Statement stmt  = null;
	    Date endDateMax = new Date();

	    try {
	        Class.forName(JDBC_DRIVER);
	        conn = DriverManager.getConnection(DB_URL, USER, PASS);
	        stmt = conn.createStatement();

	        String sql = "SELECT MAX(endDate) AS endDateMax FROM bedoccupancy WHERE personId =" + personId;
	        ResultSet rs = stmt.executeQuery(sql);

	        if (rs.next()) {
	            endDateMax = rs.getDate("endDateMax");
	        }

	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return endDateMax;
	}
	

    /**
     * Retrieves all rooms from the database.
     *
     * @return a list of Room objects representing all the rooms in the database
     */
	
	public List<Room> retrieveAllRooms() {
        Connection conn = null;
        Statement stmt = null;
        List<Room> rooms = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM room";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int roomId = rs.getInt("id");
                String titleRoom = rs.getString("title");
                int numberOfBeds = rs.getInt("numberOfBeds");
                boolean isGenderRestricted = rs.getBoolean("isGenderRestricted");
                boolean isAgeRestricted = rs.getBoolean("isAgeRestricted");
                String bedIds = rs.getString("bedIds");
                int ageMin = rs.getInt("ageMin");
                int ageMax = rs.getInt("ageMax");
                String genderRestriction = rs.getString("genderRestriction");
                
                Set<Gender> genders = parseGenderRestrictionFromString(genderRestriction);
                /// Now we will match every bedIds with a bed
                /// By checking their Ids 
                
                List<Integer> bedIdsList = parseBedIdsFromString(bedIds);
                List<Bed> bedsRoom = new ArrayList<>();
                List<Bed> beds = retrieveAllBeds();
                
                
                
                for (Integer bedId : bedIdsList) {
                	for (Bed bed : beds) {
                		if (bed.getId() == bedId) {
                			bedsRoom.add(bed);
                		}
                	}
                }

                Room room = new Room(roomId, titleRoom, numberOfBeds, isGenderRestricted, isAgeRestricted, bedsRoom, ageMin, ageMax, genders);
                rooms.add(room);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        
        return rooms;
    }
	

    /**
     * Parses a gender restriction string and returns a set of Gender objects.
     *
     * @param genderRestriction the gender restriction string to parse
     * @return a set of Gender objects representing the parsed gender restrictions
     */
	
	private Set<Gender> parseGenderRestrictionFromString(String _genderRestriction) {
	    Set<Gender> genderRestriction = new HashSet<>();

	    if (_genderRestriction != null && !_genderRestriction.isEmpty()) {
	        String[] genders = _genderRestriction.split(",");
	        for (String gender : genders) {
	        	Gender g; 
	        	switch(gender) {
	        	case "0": 
	        		g = Gender.MALE;
	        		genderRestriction.add(g);
	        		break;
	        	case "1":
	        		g = Gender.FEMALE;
	        		genderRestriction.add(g);
	        		break;
	        	case "2" : 
	        		g = Gender.OTHER;
	        		genderRestriction.add(g);
	        		break;
	        	}
	        }
	    }
	    
	    return genderRestriction;
	}
	
	

    /**
     * Parses a bed IDs string and returns a list of bed IDs.
     *
     * @param bedIdsString the bed IDs string to parse
     * @return a list of bed IDs
     */
	private List<Integer> parseBedIdsFromString(String bedIdsString) {
	    List<Integer> bedIds = new ArrayList<>();

	    if (bedIdsString != null && !bedIdsString.isEmpty()) {
	        String[] idsArray = bedIdsString.split(",");
	        for (String id : idsArray) {
	            bedIds.add(Integer.parseInt(id.trim()));
	        }
	    }
	    return bedIds;
	}
	
	

    /**
     * The main method used for testing the InitializeDB class.
     *
     * @param args the command line arguments
     */
	public static void main(String[] args) {
		
				
	}
	
	
	
}
