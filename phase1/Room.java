import java.time.*;
import java.util.*;

public class Room {

    private UUID id;
    private int capacity;
    private HashMap<LocalDateTime[], UUID> schedule;

    public Room(int capacity){
        this.id = UUID.randomUUID(); //assign Room id
        this.capacity = capacity; //assign Room Capacity
        this.schedule = new HashMap<LocalDateTime[], UUID>(); //holds all the schedule times for the Room
    }

    // getter for Room id
    public UUID getId() { return id; }

    // getter for Room Capacity
    public int getCapacity() { return capacity; }

    // setter for Room id
    // public void setId(UUID id) { this.id = id; }

    // setter for Room Capacity
    public void setCapacity(int capacity) { this.capacity = capacity; }

    // getter for Room Schedule
    public HashMap<LocalDateTime[], UUID> getSchedule(){ return schedule; }
}
