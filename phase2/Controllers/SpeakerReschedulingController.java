package Controllers;

import globallyAccessible.EventNotFoundException;
import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SpeakerReschedulingController extends EventController {
    protected String[] actInfo;
    protected LocalDateTime[] actTime;
    protected OrganizerManager organizerManager;

    public SpeakerReschedulingController(UserController userController) {
        super(userController);
        actInfo = new String[]{};
        actTime = new LocalDateTime[]{};
        organizerManager = new OrganizerManager(userManager);
    }

    public ArrayList<String> availableSpeakers(String actID){
        actInfo = tmp(actID);
        actTime = getTimeHelper(actInfo);
        ArrayList<String> freeSpeakers = organizerManager.availableSpeakers(actTime);
//        freeSpeakers.add(actInfo[5]);
        return freeSpeakers;
    }

    protected String[] tmp(String actID) {
        return eventManager.searchEventByUUID(actID);
    }

    public String checkingValidEventID(ArrayList<String[]> allActivities, String actID)
            throws EventNotFoundException {
        if (! extractActIDHelper(allActivities).contains(actID)){
            throw new EventNotFoundException("invalid activity ID");
        }
        return actID;
    }
}
