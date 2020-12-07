package entities;

import roomRequirementBuilder.roomItem;
import roomRequirementBuilder.roomItems;

import java.time.*;
import java.util.*;

/**
 * Represents a Room where <code>Event</code> can take place.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class EventRoom implements java.io.Serializable {


    /**
     * Unique identifier for each <code>Chatroom</code>.
     */
    private UUID id;

    /**
     * maximum number of <code>User</code> this room can fit in.
     */
    private int capacity;

    /**
     * Holds all <code>Activity</code> this room will take place and its scheduled time.
     */
    private final HashMap<LocalDateTime[], UUID> schedule;

    private roomItems items;


    /**
     * Creates <code>Room</code> with a randomly generated id, blank schedule, and a capacity we give it.
     * @param capacity is maximum number of <code>User</code> this room can fit in.
     */
    public EventRoom(int capacity, roomItems roomItems){
        this.id = UUID.randomUUID(); //assign entities.Room id
        this.capacity = capacity; //assign entities.Room Capacity
        this.schedule = new HashMap<>(); //holds all the schedule times for the entities.Room
        this.items = roomItems;
    }

    // getter for entities.Room id
    /**
     * Gets the unique identifier for this <code>Room</code>.
     * @return The UUID corresponding to this <code>Room</code>.
     */
    public UUID getId() { return id; }

    // getter for entities.Room Capacity

    /**
     * Gets the capacity for this <code>Room</code>
     * @return a integer corresponding to the maximum number of <code>User</code> this room can fit in.
     */
    public int getCapacity() { return capacity; }


    // setter for entities.Room id
    /**
     * Changes the id of this room with the given new id.
     * @param  id the new name of this room.
     */
    public void setId(UUID id) { this.id = id; }

    // setter for entities.Room Capacity
    /**
     * Changes the capacity of this room with the given new capacity.
     * @param  capacity the new capacity of this room.
     */
    public void setCapacity(int capacity) { this.capacity = capacity; }

    // getter for entities.Room Schedule
    /**
     * Gets the schedule for this <code>Room</code>
     * @return a Hashmap corresponding to the activities take place in this room indexed by their scheduled time.
     */
    public HashMap<LocalDateTime[], UUID> getSchedule(){ return schedule; }

    public List<roomItem> getRoomItems(){
        return this.items.getItems();
    }
}
