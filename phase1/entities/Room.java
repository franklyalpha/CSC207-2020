package entities;

import java.time.*;
import java.util.*;

public class Room {

    private UUID id;
    private int capacity;
    private HashMap<LocalDateTime[], UUID> schedule;

    public Room(int capacity){
        this.id = UUID.randomUUID(); //assign entities.Room id
        this.capacity = capacity; //assign entities.Room Capacity
        this.schedule = new HashMap<LocalDateTime[], UUID>(); //holds all the schedule times for the entities.Room
    }

    // getter for entities.Room id
    public UUID getId() { return id; }

    // getter for entities.Room Capacity
    public int getCapacity() { return capacity; }

    // setter for entities.Room id
    // public void setId(UUID id) { this.id = id; }

    // setter for entities.Room Capacity
    public void setCapacity(int capacity) { this.capacity = capacity; }

    // getter for entities.Room Schedule
    public HashMap<LocalDateTime[], UUID> getSchedule(){ return schedule; }
}
