package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a room in the application.
 */
public class Room {
    private int id;
    private String title;
    private int numberOfBeds;
    private List<Bed> beds;
    private Set<People> occupants;
    private Set<Gender> genderRestriction;
    private int ageMin;
    private int ageMax;
    private boolean isGenderRestricted;
    private boolean isAgeRestricted;
    private boolean isRestricted;

    /**
     * Constructs a new Room object with the specified parameters.
     *
     * @param id                 the ID of the room
     * @param title              the title or name of the room
     * @param numberOfBeds       the number of beds in the room
     * @param isGenderRestricted whether the room has gender restrictions
     * @param isAgeRestricted    whether the room has age restrictions
     * @param beds               the list of beds in the room
     * @param ageMin             the minimum age allowed in the room
     * @param ageMax             the maximum age allowed in the room
     * @param genderRestriction  the set of genders allowed in the room
     */
    
    public Room(int _id, String _title, int _numberOfBeds, boolean _isGenderRestricted, boolean _isAgeRestricted, List<Bed> _beds, int _ageMin, int _ageMax, Set<Gender> _genderRestriction) {
        this.id = _id;
        this.title = _title;
        this.isGenderRestricted = _isGenderRestricted;
        this.isAgeRestricted = _isAgeRestricted; 
        this.numberOfBeds = _numberOfBeds;
        this.beds = new ArrayList<>();
        this.beds = _beds;
        this.ageMin = _ageMin; 
        this.ageMax = _ageMax;
        this.genderRestriction = _genderRestriction; 
        
        if(isGenderRestricted || isAgeRestricted) {
        	this.isRestricted = true; 
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public List<Bed> getBeds(){
    	return this.beds;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
    
    public Set<People> getOccupants(){
    	return this.occupants;
    }

    public boolean isRestricted() {
        return isRestricted;
    }
    
    public boolean isAgeRestricted() {
        return isAgeRestricted;
    }
    
    public int getAgeMin() {
    	return this.ageMin;
    }
    
    public int getAgeMax() {
    	return this.ageMax;
    }
    
    public boolean isGenderRestricted() {
        return isGenderRestricted;
    }
    
    public Set<Gender> getGenderRestriction() {
    	return this.genderRestriction;
    }
    
    
    
    
    public boolean checkOccupants() {
        for (People occupant : this.occupants) {
            if (!occupant.isAllowed(this)) {
                return false;
            }
        }
        return true;
    }

    
    /*
    public void setAgeRestriction(int ageMin, int ageMax) {
    
    	/*
    	boolean previousisRestricted = this.isRestricted;
    	boolean previousageRestricted = this.ageRestricted;
    	int previousAgeMin = this.ageMin;
    	int previousAgeMax = this.ageMax;
    	this.isRestricted = true;
    	this.ageRestricted = true;
    	this.ageMin = ageMin;
    	this.ageMax = ageMax;
    	if (!checkOccupants()) {
    		System.out.println("Exception");
    		this.isRestricted= previousisRestricted;
    		this.ageRestricted=previousageRestricted;
    		this.ageMin=previousAgeMin;
    		this.ageMax=previousAgeMax;
    	}4
    	
    	
    }
    
    public void setNoAgeRestriction() {
    	this.isAgeRestricted = false;
    	if (!this.isGenderRestricted){
    		this.isRestricted = false;
    	}
    }
    
    public void setGenderRestriction(Set<Gender> genderRestriction) {
    	boolean previousisRestricted = this.isRestricted;
    	boolean previousgenderRestricted = this.genderRestricted;
    	Set<Gender> previousgenderRestriction = this.genderRestriction;
    	this.isRestricted = true;
    	this.genderRestricted = true;
    	this.genderRestriction = genderRestriction;
    	if (!checkOccupants()) {
    		System.out.println("Exception");
    		this.isRestricted= previousisRestricted;
    		this.genderRestricted=previousgenderRestricted;
    		this.genderRestriction=previousgenderRestriction;
    	}
    }
    
    public void setNoGenderRestriction() {
    	this.genderRestricted = false;
    	if (!this.ageRestricted){
    		this.isRestricted = false;
    	}
    }
    
    */
    
   
    
   
}