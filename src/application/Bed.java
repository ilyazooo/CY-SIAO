package application;

/**
 * The Bed class represents a bed in the accommodation management system.
 * Each bed has an ID, size, associated occupant, room, and related information.
 */

public class Bed {
    private int id;
    private int idOccupant;
    private int idRoom;
    private People occupant;
    private String size;
    private Room room;

    /**
     * Constructs a Bed object with the specified ID, size, room ID, and occupant ID.
     *
     * @param _id         the ID of the bed
     * @param size        the size of the bed
     * @param _id_room    the ID of the associated room
     * @param _id_occupant the ID of the associated occupant
     */
    public Bed(int _id, String size, int _id_room, int _id_occupant) {
        this.id = _id;
        this.size = size;
        this.idRoom = _id_room;
        this.idOccupant = _id_occupant;
    }

    /**
     * Returns the occupant of the bed.
     *
     * @return the occupant of the bed
     */
    public People getOccupant() {
        return occupant;
    }

    /**
     * Sets the occupant of the bed.
     *
     * @param occupant the occupant to be set
     */
    public void setOccupant(People occupant) {
        this.occupant = occupant;
    }

    /**
     * Returns the size of the bed.
     *
     * @return the size of the bed
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the size of the bed.
     *
     * @param size the size to be set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Returns the ID of the bed.
     *
     * @return the ID of the bed
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the bed.
     *
     * @param _id the ID to be set
     */
    public void setId(int _id) {
        this.id = _id;
    }

    /**
     * Returns the ID of the associated room.
     *
     * @return the ID of the associated room
     */
    public int getIdRoom() {
        return this.idRoom;
    }

    /**
     * Sets the ID of the associated room.
     *
     * @param _id_room the ID of the associated room to be set
     */
    public void setIdRoom(int _id_room) {
        this.idRoom = _id_room;
    }

    /**
     * Returns the ID of the associated occupant.
     *
     * @return the ID of the associated occupant
     */
    public int getIdOccupant() {
        return this.idOccupant;
    }

    /**
     * Sets the ID of the associated occupant.
     *
     * @param _id_occupant the ID of the associated occupant to be set
     */
    public void setIdOccupant(int _id_occupant) {
        this.idOccupant = _id_occupant;
    }

    /**
     * Returns the room associated with the bed.
     *
     * @return the room associated with the bed
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room associated with the bed.
     *
     * @param room the room to be set
     */
    public void setRoom(Room room) {
        this.room = room;
    }
}
