import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private ArrayList<Room> rooms;


    public RoomManager(){
        this.rooms = new ArrayList<Room>();
    }

    public UUID addRoom(int capacity){
        Room newRoom = new Room(capacity);
        rooms.add(newRoom);
        return newRoom.getId();
    }

    // Check the Fullness for the room
    public boolean CheckRoomFullness(Integer UserNum, UUID roomID){
        Room room = findRoom(roomID);
        assert room != null;
        return room.getCapacity() >= UserNum;
    }

    private Room findRoom(UUID roomID){
        for (Room room: rooms){
            if (room.getId().equals(roomID)){
                return room;
            }
        }
        return null;
    }

    // Add an activity in the Room schedule
    public void BookRoom(LocalDateTime[] time, UUID activityID, UUID roomID){
        Room room = findRoom(roomID);
        assert room != null;
        if (!room.getSchedule().containsKey(time)){
            room.getSchedule().put(time, activityID);
        }
    }

    // Remove an activity in the Room schedule
    public void CancelRoomActivity(LocalDateTime[] time, UUID actID, UUID roomID){
        Room room = findRoom(roomID);
        assert room != null;
        if (room.getSchedule().containsKey(time)){
            room.getSchedule().remove(time, actID);
        }
    }

    public ArrayList<UUID> bookingAvailable(LocalDateTime[] targetPeriod){
        ArrayList<UUID> possibleRooms = new ArrayList<UUID>();
        for (Room room: rooms){
            if (checkSingleRoomOK(targetPeriod, room)){
                possibleRooms.add(room.getId());
            }
        }
        return possibleRooms;
    }

    private boolean checkSingleRoomOK(LocalDateTime[] targetPeriod, Room room){
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

}
