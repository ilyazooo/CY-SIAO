package application;

import java.util.Date;

/**
 * The BedOccupancy class represents the occupancy of a bed in the accommodation management system.
 * Each occupancy record contains the person ID, bed ID, occupancy ID, start date, and end date.
 */

public class BedOccupancy {
    private int personId;
    private int bedId;
    private int idOccupancy;
    private Date startDate;
    private Date endDate;

    /**
     * Constructs a BedOccupancy object with the specified occupancy ID, person ID, bed ID, start date, and end date.
     *
     * @param _idOccupancy the ID of the occupancy
     * @param _personId    the ID of the associated person
     * @param _bedId       the ID of the associated bed
     * @param _startDate   the start date of the occupancy
     * @param _endDate     the end date of the occupancy
     */
    public BedOccupancy(int _idOccupancy, int _personId, int _bedId, Date _startDate, Date _endDate) {
        this.idOccupancy = _idOccupancy;
        this.personId = _personId;
        this.bedId = _bedId;
        this.startDate = _startDate;
        this.endDate = _endDate;
    }

    /**
     * Returns the ID of the occupancy.
     *
     * @return the ID of the occupancy
     */
    public int getIdOccupancy() {
        return this.idOccupancy;
    }

    /**
     * Sets the ID of the occupancy.
     *
     * @param _idOccupancy the ID of the occupancy to be set
     */
    public void setIdOccupancy(int _idOccupancy) {
        this.idOccupancy = _idOccupancy;
    }

    /**
     * Returns the ID of the associated person.
     *
     * @return the ID of the associated person
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Sets the ID of the associated person.
     *
     * @param personId the ID of the associated person to be set
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * Returns the ID of the associated bed.
     *
     * @return the ID of the associated bed
     */
    public int getBedId() {
        return bedId;
    }

    /**
     * Sets the ID of the associated bed.
     *
     * @param bedId the ID of the associated bed to be set
     */
    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    /**
     * Returns the start date of the occupancy.
     *
     * @return the start date of the occupancy
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the occupancy.
     *
     * @param startDate the start date of the occupancy to be set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the end date of the occupancy.
     *
     * @return the end date of the occupancy
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the occupancy.
     *
     * @param endDate the end date of the occupancy to be set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Checks if the occupancy has finished based on the current date.
     *
     * @return true if the occupancy has finished, false otherwise
     */
    public boolean isFinished() {
        Date currentDate = new Date();
        return currentDate.after(endDate);
    }

    /**
     * Checks if the bed is occupied within the specified start and end dates.
     *
     * @param startDate2 the start date to check
     * @param endDate2   the end date to check
     * @return true if the bed is occupied within the specified dates, false otherwise
     */
    public boolean isOccupied(Date startDate2, Date endDate2) {
        boolean isInRange = false;

        CustomDate sDate1 = CustomDate.convertFromUtilDate(this.startDate);
        CustomDate eDate1 = CustomDate.convertFromUtilDate(this.endDate);

        CustomDate sDate2 = CustomDate.convertFromUtilDate(startDate2);
        CustomDate eDate2 = CustomDate.convertFromUtilDate(endDate2);

        eDate1 = eDate1.addDays(1); // A bed cannot be reserved the day a person freed it

        if (sDate2.isBefore(sDate1) && eDate2.isAfter(sDate1)) {
            isInRange = true;
        }

        if (eDate2.isAfter(eDate1) && sDate2.isBefore(eDate1)) {
            isInRange = true;
        }

        if (eDate2.isBefore(eDate1) && sDate2.isAfter(sDate1)) {
            isInRange = true;
        }

        return isInRange;
    }
}
