package entities;

import roomRequirementBuilder.RoomItems;

import java.time.*;
import java.util.*;

/**
 * Represents a Room where <code>Event</code> can take place.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
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

    /**
     * <code>RoomItems</code> in this room.
     */
    private RoomItems items;


    /**
     * Creates <code>Room</code> with a randomly generated id, blank schedule, and a capacity we give it.
     * @param capacity is maximum number of <code>User</code> this room can fit in.
     */
    public EventRoom(int capacity, RoomItems roomItems){
        this.id = UUID.randomUUID(); //assign entities.Room id
        this.capacity = capacity; //assign entities.Room Capacity
        this.schedule = new HashMap<>(); //holds all the schedule times for the entities.Room
        this.items = roomItems;
    }

    /**
     * Creates <code>Room</code> with a randomly generated id, blank schedule, no room items and a capacity we give it.
     * @param capacity is maximum number of <code>User</code> this room can fit in.
     */
    public EventRoom(int capacity){
        this.id = UUID.randomUUID();
        this.capacity = capacity;
        this.schedule = new HashMap<>();
        this.items = null;
    }

    /**
     * Gets the unique identifier for this <code>Room</code>.
     * @return The UUID corresponding to this <code>Room</code>.
     */
    public UUID getId() { return id; }


    /**
     * Gets the capacity for this <code>Room</code>
     * @return a integer corresponding to the maximum number of <code>User</code> this room can fit in.
     */
    public int getCapacity() { return capacity; }


    /**
     * Changes the id of this room with the given new id.
     * @param  id the new name of this room.
     */
    public void setId(UUID id) { this.id = id; }

    /**
     * Changes the capacity of this room with the given new capacity.
     * @param  capacity the new capacity of this room.
     */
    public void setCapacity(int capacity) { this.capacity = capacity; }

    /**
     * Gets the schedule for this <code>Room</code>
     * @return a Hashmap corresponding to the activities take place in this room indexed by their scheduled time.
     */
    public HashMap<LocalDateTime[], UUID> getSchedule(){ return schedule; }


    /**
     * Gets room items for this <code>Room</code>
     * @return room items in this room.
     */
    public RoomItems getRoomItems(){
        return this.items;
    }

    /**
     * Changes the room items of this room with given <code>RoomItems</code>.
     * @param  itemsTotal all new items in this room.
     */
    public void setRoomItems(RoomItems itemsTotal){
        items = itemsTotal;
    }


    /**
     * Show the information of event room in string.
     * @return the string representation of class <code>EventRoom</code>.
     */
    @Override
    public String toString(){
        return "EventRoom{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", \nitems=" + items.showItems() +
                '}';
    }

    /**
     * Check if the event room meets the given technology constraints.
     * @param constraints: a array containing 3 boolean values which represents whether requires projector, microphone,
     *                   and party audio.
     * @return true if the requirement is met, i.e. has the required room items.
     */
    public Boolean checkItemRequirement(Boolean[] constraints){
        Boolean[] availability = new Boolean[]{items.hasProjector(), items.hasMicrophone(), items.hasPartyAudio()};
        for(int i = 0; i < 3; i ++){
            if(constraints[i] != availability[i] & constraints[i]){
                return false;
            }
        }
        return true;
    }
}
