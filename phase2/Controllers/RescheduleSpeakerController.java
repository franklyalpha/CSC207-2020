package Controllers;

import globallyAccessible.EventNotFoundException;
import globallyAccessible.NoEventsException;
import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class RescheduleSpeakerController extends EventController {
    private String[] actInfo;
    private LocalDateTime[] actTime;
    private OrganizerManager organizerManager;


    public RescheduleSpeakerController(UserController userController) {
        super(userController);
        actInfo = new String[]{};
        actTime = new LocalDateTime[]{};
        organizerManager = new OrganizerManager(userManager);
    }

    private String[] tmp(String actID) {
        return eventManager.searchEventByUUID(actID);
    }

    public ArrayList<String> availableSpeakers(String actID){
        actInfo = tmp(actID);
        actTime = getTimeHelper(actInfo);
        ArrayList<String> freeSpeakers = organizerManager.availableSpeakers(actTime);
        freeSpeakers.add(actInfo[5]);
        return freeSpeakers;
    }

    public void updateRescheduledSpeaker(String speaker){
        eventManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        organizerManager.deleteEvent(actInfo[5], actTime);
        organizerManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
    }





    public ArrayList<String[]> getAllActivities() throws NoEventsException {
        ArrayList<String[]> allActivities = eventManager.viewUpcomingActivites();
        if (allActivities.size() == 0){
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }

    public String checkingValidEventID(ArrayList<String[]> allActivities, String actID)
            throws EventNotFoundException {
        if (! extractActIDHelper(allActivities).contains(actID)){
            throw new EventNotFoundException("invalid activity ID");
        }
        return actID;
    }
}
