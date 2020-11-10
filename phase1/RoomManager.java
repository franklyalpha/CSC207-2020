import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

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

    private Room room;


    public RoomManager(){
        this.room = null;
    }

    public UUID addRoom(int capacity){
        Room newRoom = new Room(capacity);
        return newRoom.getId();
    }

    // Check the Fullness for the room
    public boolean CheckRoomFullness(Integer UserNum){
        return room.getCapacity() >= UserNum;
    }

    // Add an activity in the Room schedule
    public void BookRoom(LocalDateTime[] time, UUID activityID){
        if (!room.getSchedule().containsKey(time)){
            room.getSchedule().put(time, activityID);
        }
    }

    // Remove an activity in the Room schedule
    public void CancelRoomActivity(LocalDateTime[] time, UUID actID){
        if (room.getSchedule().containsKey(time)){
            room.getSchedule().remove(time, actID);
        }
    }

    public boolean bookingAvailable(LocalDateTime[] targetPeriod){
        HashMap<LocalDateTime[], UUID> roomBooked = room.getSchedule();
        for(LocalDateTime[] interv: roomBooked.keySet()){
            LocalDateTime start = interv[0];
            LocalDateTime end = interv[1];
            if (start.isBefore(targetPeriod[0]) && end.isAfter(targetPeriod[1])){
                return false;
            }
            if (start.isAfter(targetPeriod[0]) && start.isBefore(targetPeriod[1])){
                return false;
            }
            if (end.isAfter(targetPeriod[0]) && end.isBefore(targetPeriod[1])){
                return false;
            }
        }
        return true;
    }

    public UUID getRoomID(){
        return room.getId();
    }

}
