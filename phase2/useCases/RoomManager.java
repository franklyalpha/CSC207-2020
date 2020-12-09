package useCases;

import entities.EventRoom;
import roomRequirementBuilder.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

//still in progress.

/**
 * Represents a <code>RoomManager</code> that is responsible for any interactions with <code>Room</code> entities.
 * Contains instance of entities.Room: room, a room for some activities;
 *
 * Own constructor
 * CheckRoomFullness: Check the Fullness for the room;
 * BookRoom: Add an activity in the room schedule given a time period;
 * bookingAvailable: Check if there are activities using this room at a given time;
 * CancelRoomActivity: Remove an activity in the room schedule;
 *
 */

public class RoomManager implements java.io.Serializable {


    /**
     * a Arraylist of <code>Room</code>
     */
    private ArrayList<EventRoom> eventRooms;

    private ItemFactory itemFactory;

    /**
     * Creates <code>RoomManager</code> with a blank list of Rooms.
     */
    public RoomManager(){
        eventRooms = new ArrayList<EventRoom>();
    }

    /**
     * Creates a <code>Room</code> and add it to the list of rooms of the <code>RoomManager</code>
     * @param capacity is the capacity for the newly constructed <code>Room</code>.
     * @return the id of the newly constructed <code>Room</code>.
     */
    public UUID addRoom(int capacity, List<Integer> roomItems){
        RoomBuilder builder = new RoomBuilder(capacity);
        builder.buildMicrophone(roomItems.get(0));
        builder.buildProjector(roomItems.get(1));
        builder.buildPartyAudio(roomItems.get(2));
        EventRoom newEventRoom = builder.getNewRoom();
        eventRooms.add(newEventRoom);
        return newEventRoom.getId();
    }

    // Check the Fullness for the room

    /**
     * Check whether a <code>Room</code> have the capacity to hold a given <code>Activity</code>
     * @param UserNum is the number of <code>User</code> attending the activity.
     * @param roomID is the id of the room we want to chsck.
     * @return True if the room have the ability to hold this activity.
     */
    public boolean CheckRoomFullness(Integer UserNum, UUID roomID){
        EventRoom eventRoom = findRoom(roomID);
        assert eventRoom != null;
        return eventRoom.getCapacity() >= UserNum;
    }

    /**
     * find the <code>Room</code> by given rome id.
     * @param roomID is the id of the room we are searching for.
     * @return a room or nothing if it is not in the list of rooms of our <code>RoomManager</code>
     */
    public EventRoom findRoom(UUID roomID){
        for (EventRoom eventRoom : eventRooms){
            if (eventRoom.getId().equals(roomID)){
                return eventRoom;
            }
        }
        return null;
    }


    /**
     * book a <code>Room</code> by given time, room, and <code>Activity</code>.
     * @param time is the time we want to book.
     * @param activityID is the id of the activity.
     * @param roomID is the id of the room.
     */
    public void BookRoom(LocalDateTime[] time, UUID activityID, UUID roomID){
        EventRoom eventRoom = findRoom(roomID);
        assert eventRoom != null;
        if (!eventRoom.getSchedule().containsKey(time)){
            eventRoom.getSchedule().put(time, activityID);
        }
    }


    public int getRoomCapacity(UUID roomId){
        return findRoom(roomId).getCapacity();
    }

    /**
     * Remove an activity in the entities.Room schedule
     * @param time of the <code>Activity</code>
     * @param actID id of the <code>Activity</code>
     * @param roomID of the <code>Room</code>
     */
    public void CancelRoomEvent(LocalDateTime[] time, UUID actID, UUID roomID){
        EventRoom eventRoom = findRoom(roomID);
        assert eventRoom != null;
        if (eventRoom.getSchedule().containsKey(time)){
            eventRoom.getSchedule().remove(time, actID);
        }
    }

    /**
     * get list of available <code>Room</code> in given time period.
     * @param targetPeriod is the time period we want to check.
     * @return a list of available room ids.
     */
    public ArrayList<String[]> bookingAvailable(LocalDateTime[] targetPeriod){
        ArrayList<String[]> possibleRooms = new ArrayList<String[]>();
        for (EventRoom eventRoom : eventRooms){
            if (checkSingleRoomOK(targetPeriod, eventRoom)){
                possibleRooms.add(new String[]{eventRoom.getId().toString(), eventRoom.toString()});
            }
        }
        return possibleRooms;
    }

    /**
     * check if a specific <code>Room</code> is available in a time period.
     * @param targetPeriod is the target time period.
     * @param eventRoom is the target room
     * @return True if the room is not used by any activity in this time period.
     */
    private boolean checkSingleRoomOK(LocalDateTime[] targetPeriod, EventRoom eventRoom){
        HashMap<LocalDateTime[], UUID> roomBooked = eventRoom.getSchedule();
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
            if(start.isEqual(targetPeriod[0]) && end.isEqual(targetPeriod[1])){
                return false;
            }
        }
        return true;
    }

    public RoomItems getRoomItems(UUID id){
        return findRoom(id).getRoomItems();
    }

    public boolean checkRoomItems(UUID eventID, Boolean[] itemChoice){
        return findRoom(eventID).checkItemRequirement(itemChoice);
    }

//    public showItems(){
//
//    }
}
