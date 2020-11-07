import java.time.*;
import java.util.*;

public class Room {

    private int id;
    private int capacity;
    private HashMap<LocalDateTime, String> schedule;

    public Room(int id, int capacity){
        this.id = id;
        this.capacity = capacity;
        this.schedule = new HashMap<LocalDateTime, String>();
    }

    public int getId() { return id; }

    public int getCapacity() { return capacity; }

    public void setId(int id) { this.id = id; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
}
