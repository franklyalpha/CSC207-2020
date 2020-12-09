package Controllers;

import globallyAccessible.MaxNumberBeyondRoomCapacityException;
import globallyAccessible.NoEventsException;

import java.util.ArrayList;
import java.util.UUID;


public class ModifyEventController extends EventController {

    public ModifyEventController(UserController userController) {
        super(userController);
    }

    public ArrayList<String[]> getAllActivities() throws NoEventsException {
        ArrayList<String[]> allActivities = eventManager.viewUpcomingActivities();
        if (allActivities.size() == 0) {
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }

    public void changeEventMaxNumPeople(UUID activityId, Integer newMaxNum) throws MaxNumberBeyondRoomCapacityException {
        UUID roomID = UUID.fromString(eventManager.searchEventByUUID(activityId.toString())[4]);
        if(roomManager.getRoomCapacity(roomID) < newMaxNum){
            throw new MaxNumberBeyondRoomCapacityException("invalid maximum enrollment");
        }
        eventManager.changeEventMaxParticipant(activityId, newMaxNum);
    }
}//TODO part of event retrofit
