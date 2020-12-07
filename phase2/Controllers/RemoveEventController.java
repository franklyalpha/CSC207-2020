package Controllers;

import globallyAccessible.NoEventsException;
import useCases.EventManager;

import java.util.ArrayList;
import java.util.UUID;


public class RemoveEventController extends EventController {

    public RemoveEventController(UserController userController) {
        super(userController);
    }

    public ArrayList<String[]> getAllActivities() throws NoEventsException {
        ArrayList<String[]> allActivities = eventManager.viewUpcomingActivities();
        if (allActivities.size() == 0) {
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }

    public void cancelEvent(UUID activityID){
        EventManager eventManager = null;
        UUID activityId = null;
        eventManager.deleteEvent(activityId);
    }
}