import java.time.*;
import java.util.*;

public class Room {

    public int id;
    public int capacity;
    public HashMap<LocalDateTime, Activity> schedule;

    public Room(int id, int capacity){
        this.id = id; //assign Room id
        this.capacity = capacity; //assign Room Capacity
        this.schedule = new HashMap<LocalDateTime, Activity>(); //holds all the schedule times for the Room
    }

    // getter for Room id
    public int getId() { return id; }

    // getter for Room Capacity
    public int getCapacity() { return capacity; }

    // setter for Room id
    public void setId(int id) { this.id = id; }

    // setter for Room Capacity
    public void setCapacity(int capacity) { this.capacity = capacity; }

    // getter for Room Schedule
    public HashMap<LocalDateTime, Activity> getSchedule(){ return schedule; }
}
