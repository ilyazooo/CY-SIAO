package application;

import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * The ManageDB class provides methods for managing the database operations in the accommodation management system.
 */	
public class ManageDB {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL      = "jdbc:mysql://localhost:3306/cysiao";
	private static final String USER        = "root";
	private static final String PASS        = "";
	
	
    /**
     * Constructs a new ManageDB object.
     */
	
	public ManageDB() {
		
	}
	
	 /**
     * Adds a person to the database.
     *
     * @param firstName            the first name of the person
     * @param lastName             the last name of the person
     * @param cityOfBirth          the city of birth of the person
     * @param dateOfBirth          the date of birth of the person
     * @param gender               the gender of the person
     * @param socialSecurityNumber the social security number of the person
     */
	
	 public void addPerson(String firstName, String lastName, String cityOfBirth,  Date dateOfBirth, Gender gender, String socialSecurityNumber) {
		 	Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "INSERT INTO people (lastName, firstName, gender, dateOfBirth, birthCity, socialSecurityNumber) VALUES (?, ?, ?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(sql);
	            
	            int intGender = 0;
	            
	            switch(gender) {
	            case MALE:
	            	intGender = 0;
	            	break;
	            case FEMALE:
	            	intGender = 1;
	            	break;
	            case OTHER: 
	            	intGender = 2;
	            	break;
	         
	            }
	            
	            java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getTime());
	            
	            pstmt.setString(1, lastName);
	            pstmt.setString(2, firstName);	            
	            pstmt.setInt(3, intGender);
	            pstmt.setDate(4, sqlDateOfBirth);
	            pstmt.setString(5, cityOfBirth);
	            pstmt.setString(6, socialSecurityNumber);
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	    }
	 
	
	 /**
	     * Adds a bed to the database.
	     *
	     * @param bedSize the size of the bed
	     * @param idRoom  the ID of the room the bed belongs to
	     */
	 
	 
	 public void addBed(String bedSize, int idRoom) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "INSERT INTO bed (bed_size, id_room, id_occupant) VALUES (?, ?, ?)";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, bedSize);
	            pstmt.setInt(2, idRoom);
	            pstmt.setInt(3, 0);
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	}
	 
	 /**
	     * Adds a bed occupancy record to the database.
	     *
	     * @param idPerson   the ID of the person occupying the bed
	     * @param idBed      the ID of the bed being occupied
	     * @param startDate  the start date of the occupancy
	     * @param endDate    the end date of the occupancy
	     */
	 
	 public void addBedOccupancy(int idPerson, int idBed, Date startDate, Date endDate) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "INSERT INTO bedoccupancy (personId, bedId, startDate, endDate) VALUES (?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(sql);
	            
	            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	            
	            pstmt.setInt(1, idPerson);
	            pstmt.setInt(2, idBed);
	            pstmt.setDate(3, sqlStartDate);
	            pstmt.setDate(4, sqlEndDate);
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	}
	 
	 
	 /**
	     * Adds a room to the database.
	     *
	     * @param numBeds           the number of beds in the room
	     * @param title             the title or name of the room
	     * @param isAgeRestricted   indicates if the room has age restrictions
	     * @param isGenderRestricted indicates if the room has gender restrictions
	     * @param ageMin            the minimum age allowed in the room (if age-restricted)
	     * @param ageMax            the maximum age allowed in the room (if age-restricted)
	     * @param genderRestriction the gender restriction of the room (if gender-restricted)
	     */
	 
	 public void addRoom(int numBeds, String title, boolean isAgeRestricted, boolean isGenderRestricted, int ageMin, int ageMax, String genderRestriction) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "INSERT INTO room (numberOfBeds, bedIds, isAgeRestricted, isGenderRestricted, title, ageMin, ageMax, genderRestriction) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, numBeds);
	            pstmt.setInt(2, 0);
	            pstmt.setBoolean(3, isAgeRestricted);
	            pstmt.setBoolean(4, isGenderRestricted);
	            pstmt.setString(5, title);
	            pstmt.setInt(6, ageMin);
	            pstmt.setInt(7, ageMax);
	            pstmt.setString(8, genderRestriction);
	           
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	   }
	 
	 
	 /**
	     * Deletes a bed from the database.
	     *
	     * @param idBed the ID of the bed to delete
	     */
	 
	 public void deleteBed(int idBed) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "DELETE FROM bed WHERE id = ?";
	            pstmt = conn.prepareStatement(sql);
	            
	            
	            pstmt.setInt(1, idBed);
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	}
	 

	    /**
	     * Deletes a room from the database.
	     *
	     * @param idRoom the ID of the room to delete
	     */
	 
	 public void deleteRoom(int idRoom) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "DELETE FROM room WHERE id = ?";
	            pstmt = conn.prepareStatement(sql);
	            
	            
	            pstmt.setInt(1, idRoom);
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	}
	 
	 /**
	     * Updates the details of a room in the database.
	     *
	     * @param roomId             the ID of the room to update
	     * @param numBeds            the updated number of beds in the room
	     * @param title              the updated title or name of the room
	     * @param isAgeRestricted    indicates if the room has age restrictions
	     * @param isGenderRestricted indicates if the room has gender restrictions
	     * @param ageMin             the updated minimum age allowed in the room (if age-restricted)
	     * @param ageMax             the updated maximum age allowed in the room (if age-restricted)
	     * @param genderRestriction  the updated gender restriction of the room (if gender-restricted)
	     */
	 public void updateRoom(int roomId, int numBeds, String title, boolean isAgeRestricted, boolean isGenderRestricted, int ageMin, int ageMax, String genderRestriction) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "UPDATE room SET numberOfBeds = ?, bedIds = ?, isAgeRestricted = ?, isGenderRestricted = ?, title = ?, ageMin = ?, ageMax = ?, genderRestriction = ? WHERE id = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, numBeds);
	            pstmt.setInt(2, 0);
	            pstmt.setBoolean(3, isAgeRestricted);
	            pstmt.setBoolean(4, isGenderRestricted);
	            pstmt.setString(5, title);
	            pstmt.setInt(6, ageMin);
	            pstmt.setInt(7, ageMax);
	            pstmt.setString(8, genderRestriction);
	            pstmt.setInt(9, roomId);
	            
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	   }
	 
	 
	 /**
	     * Updates the details of a bed in the database.
	     *
	     * @param bedId      the ID of the bed to update
	     * @param size       the updated size of the bed
	     * @param idOccupant the updated ID of the occupant
	     * @param idRoom     the updated ID of the room the bed belongs to
	     */
	 public void updateBed(int bedId, String size, int idOccupant, int idRoom) {
		 	Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "UPDATE bed SET bed_size = ?, id_occupant = ?, id_room = ? WHERE id = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, size);
	            pstmt.setInt(2, 0);
	            pstmt.setInt(3, idRoom);
	            pstmt.setInt(4, bedId);
	        
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	   }
	   

	    /**
	     * The main method used for testing the ManageDB class.
	     *
	     * @param args the command line arguments
	     */
	 
	 public void removeOccupancy(int idPersonChoice,int idBed,Date startUtilDate,Date endUtilDate) {
		 	Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String sql = "DELETE FROM bedoccupancy WHERE personId = ? AND bedId = ? AND startDate = ? AND endDate = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, idPersonChoice);
	            pstmt.setInt(2, idBed);
	            java.sql.Date sqlStartDate = new java.sql.Date(startUtilDate.getTime());
	            java.sql.Date sqlEndDate = new java.sql.Date(endUtilDate.getTime());
	            pstmt.setDate(3, sqlStartDate);
	            pstmt.setDate(4, sqlEndDate);
	        
	            pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	   }
	 



	    public static int calculateRoomOccupancy(int roomId, Date startDate, Date endDate) {
	        int totalOccupancy = 0;
	        Connection conn = null;

	        try {
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String query = "SELECT COUNT(bo.id) AS total_occupancy " +
	                    "FROM bedoccupancy bo " +
	                    "JOIN bed b ON bo.bedId = b.id " +
	                    "WHERE b.id_room = ? " +
	                    "AND ((bo.startDate >= ? AND bo.startDate <= ?) OR " +
	                    "(bo.endDate >= ? AND bo.endDate <= ?) OR " +
	                    "(bo.startDate <= ? AND bo.endDate >= ?))";
	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setInt(1, roomId);

	            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

	            statement.setDate(2, sqlStartDate);
	            statement.setDate(3, sqlEndDate);
	            statement.setDate(4, sqlStartDate);
	            statement.setDate(5, sqlEndDate);
	            statement.setDate(6, sqlStartDate);
	            statement.setDate(7, sqlEndDate);

	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                totalOccupancy = resultSet.getInt("total_occupancy");
	            }

	            resultSet.close();
	            statement.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return totalOccupancy;
	    }
	    
	    public static int calculateRoomCapacity(int roomId) {
	        int totalCapacity = 0;
	        Connection conn = null;

	        try {
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            String query = "SELECT SUM(bed_size) AS total_capacity " +
	                    "FROM bed " +
	                    "WHERE id_room = ?";
	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setInt(1, roomId);

	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                totalCapacity = resultSet.getInt("total_capacity");
	            }

	            resultSet.close();
	            statement.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return totalCapacity;
	    }
	    
 


 
	 

}

