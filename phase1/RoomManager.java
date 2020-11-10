import java.time.LocalDateTime;

public class RoomManager {
    /**
     * Contains instance of Room: room, a room for some activities;
     *
     * Own constructor
     * CheckRoomFullness: Check the Fullness for the room;
     * BookRoom: Add an activity in the room schedule;
     * CancelRoomActivity: Remove an activity in the room schedule;
     *
     */

    private final Room room;


    public RoomManager(Room room){
        this.room = room;
    }

    // Check the Fullness for the room
    public boolean CheckRoomFullness(Integer UserNum){
        return room.getCapacity() == UserNum;
    }

    // Add an activity in the Room schedule
    public void BookRoom(LocalDateTime time, Activity activity){
        if (!room.getSchedule().containsKey(time)){
            room.schedule.put(time,activity);
        }
    }

    // Remove an activity in the Room schedule
    public void CancelRoomActivity(LocalDateTime time, Activity activity){
        if (!room.getSchedule().containsKey(time)){
            room.schedule.remove(time,activity);
        }
    }

}
