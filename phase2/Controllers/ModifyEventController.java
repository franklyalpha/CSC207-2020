package Controllers;

import globallyAccessible.MaxNumberBeyondRoomCapacityException;
import globallyAccessible.NoEventsException;

import java.util.ArrayList;
import java.util.UUID;


/**
 * The controller responsible for updating information (in particularm the maximum number of
 * participants) of existing events.
 */
public class ModifyEventController extends EventController {

    /**
     * The constructor of this controller class.
     * @param userController An instance of <>UserController</>.
     */
    public ModifyEventController(UserController userController) {
        super(userController);
    }

    /**
     * updates maximum number of participannt of a given event.
     * @param activityId the ID of event in which the changes will be applied.
     * @param newMaxNum the new maximum number of participant of an event.
     * @throws MaxNumberBeyondRoomCapacityException is thrown when given maximum participants is greater than room capacity.
     */
    public void changeEventMaxNumPeople(UUID activityId, Integer newMaxNum) throws MaxNumberBeyondRoomCapacityException {
        UUID roomID = UUID.fromString(eventManager.searchEventByUUID(activityId.toString())[4]);
        if(roomManager.getRoomCapacity(roomID) < newMaxNum){
            throw new MaxNumberBeyondRoomCapacityException("invalid maximum enrollment");
        }
        eventManager.changeEventMaxParticipant(activityId, newMaxNum);
    }
}
