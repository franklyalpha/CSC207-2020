package Controllers;

import globallyAccessible.NoEventsException;
import useCases.OrganizerManager;
import useCases.PanelManager;
import useCases.TalkManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class PanelRescheduleSpeakerController extends SpeakerReschedulingController {
    private PanelManager panelManager;

    public PanelRescheduleSpeakerController(UserController userController) {
        super(userController);
        panelManager = new PanelManager(eventManager);
    }

    public ArrayList<String[]> getAllActivities() throws NoEventsException {
        ArrayList<String[]> allActivities = panelManager.viewUpcomingActivites();
        if (allActivities.size() == 0){
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }

    public void addNewSpeaker(String speaker){
        panelManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        organizerManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
    }

    public ArrayList<String> enrolledSpeakers(String actID){
        actInfo = tmp(actID);
        actTime = getTimeHelper(actInfo);
        return panelManager.getSpeakersList(actID);
    }

    public void deleteSpeaker(String speaker){
        panelManager.deleteSpeaker(UUID.fromString(actInfo[0]), speaker);
        organizerManager.deleteEvent(speaker, actTime);
    }


}
