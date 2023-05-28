package application;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.time.Period;
import java.time.ZoneId;
import java.time.Period;
import java.util.Calendar;


/**
 * The People class represents a person with various attributes.
 */
public class People {
    private String lastName;
    private String firstName; 
    private int id;
    private Gender gender;
    private Date dateOfBirth;
    private String birthCity;
    private String socialSecurityNumber;
    private Set<String> knownAddresses;

    
    /**
     * Constructs a People object with the specified attributes.
     *
     * @param _id                    the ID of the person
     * @param _lastName              the last name of the person
     * @param _firstName             the first name of the person
     * @param _gender                the gender of the person
     * @param _dateOfBirth           the date of birth of the person
     * @param _birthCity             the birth city of the person
     * @param _socialSecurityNumber  the social security number of the person
     */
    public People(int _id, String _lastName, String _firstName, Gender _gender, Date _dateOfBirth, String _birthCity, String _socialSecurityNumber) {
    	this.id = _id;
        this.lastName = _lastName;
        this.firstName = _firstName; 
        this.gender = _gender;
        this.dateOfBirth = _dateOfBirth;
        this.birthCity = _birthCity;
        this.socialSecurityNumber = _socialSecurityNumber;
        this.knownAddresses = new HashSet<>();
    }
    
    public void setId(int _id) {
    	this.id = _id;
    }
    
    public int getId() {
    	return id;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String _firstName) {
        this.firstName = _firstName;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public void addKnownAddress(String address) {
        knownAddresses.add(address);
    }
    

    /**
     * Checks if the person is allowed in the specified room.
     *
     * @param room the room to check
     * @return true if the person is allowed, false otherwise
     */
    
    public boolean isAllowed(Room room) {
    	
        if (room.isRestricted()) {
            if (room.isAgeRestricted()) {
                CustomDate birthDate = CustomDate.convertFromUtilDate(this.dateOfBirth);

                CustomDate currentDate = CustomDate.convertFromUtilDate(new java.util.Date());

                int age = currentDate.getYear() - birthDate.getYear();

                if (currentDate.getMonth() < birthDate.getMonth() ||
                        (currentDate.getMonth() == birthDate.getMonth() &&
                                currentDate.getDay() < birthDate.getDay())) {
                    age--;
                }

                if (age < room.getAgeMin() || age > room.getAgeMax()) {
                    return false;
                }
            }

            if (room.isGenderRestricted()) {
                if (!room.getGenderRestriction().contains(this.gender)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
     * Calculates and returns the age of the person.
     *
     * @return the age of the person
     */
    
    public int getAge(){
    	CustomDate birthDate = CustomDate.convertFromUtilDate(this.dateOfBirth);

        CustomDate currentDate = CustomDate.convertFromUtilDate(new java.util.Date());

        int age = currentDate.getYear() - birthDate.getYear();

        if (currentDate.getMonth() < birthDate.getMonth() ||
                (currentDate.getMonth() == birthDate.getMonth() &&
                        currentDate.getDay() < birthDate.getDay())) {
            age--;
        }
        return age;
    }
    
    /**
     * Checks if the person is a minor (under 18 years old).
     *
     * @return true if the person is a minor, false otherwise
     */
    public boolean isMinor() {
    	LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Period age = Period.between(birthDate, currentDate);

        return age.getYears() >= 18;
    }
    
    /**
     * Checks if the person is an adult (18 years old or older).
     *
     * @return true if the person is an adult, false otherwise
     */
    public boolean isAdult() {
        return !isMinor();
    }
    

    
}