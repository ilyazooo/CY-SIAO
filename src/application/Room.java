package application;

import java.util.ArrayList;
import java.util.HashSet;
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
    
    public Room(int id, String title, int numberOfBeds, boolean isGenderRestricted, boolean isAgeRestricted, List<Bed> beds, int ageMin, int ageMax, Set<Gender> genderRestriction) {
        this.id = id;
        this.title = title;
        this.numberOfBeds = numberOfBeds;
        this.isGenderRestricted = isGenderRestricted;
        this.isAgeRestricted = isAgeRestricted; 
        this.beds = beds != null ? new ArrayList<>(beds) : new ArrayList<>();
        this.ageMin = ageMin; 
        this.ageMax = ageMax;
        this.genderRestriction = genderRestriction != null ? new HashSet<>(genderRestriction) : new HashSet<>();
        
        if(isGenderRestricted || isAgeRestricted) {
            this.isRestricted = true; 
        }
        
        this.occupants = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public List<Bed> getBeds(){
        return beds;
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
        return occupants;
    }

    public boolean isRestricted() {
        return isRestricted;
    }
    
    public boolean isAgeRestricted() {
        return isAgeRestricted;
    }
    
    public int getAgeMin() {
        return ageMin;
    }
    
    public int getAgeMax() {
        return ageMax;
    }
    
    public boolean isGenderRestricted() {
        return isGenderRestricted;
    }
    
    public Set<Gender> getGenderRestriction() {
        return genderRestriction;
    }
    
    public boolean checkOccupants() {
        for (People occupant : occupants) {
            if (!occupant.isAllowed(this)) {
                return false;
            }
        }
        return true;
    }
}