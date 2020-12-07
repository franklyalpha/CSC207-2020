package Controllers;

import globallyAccessible.EventNotFoundException;
import globallyAccessible.NoEventsException;
import useCases.OrganizerManager;
import useCases.TalkManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class TalkRescheduleSpeakerController extends SpeakerReschedulingController {

    private TalkManager talkManager;



    public TalkRescheduleSpeakerController(UserController userController) {
        super(userController);
        talkManager = new TalkManager(eventManager);
    }

    public void updateRescheduledSpeaker(String speaker){
        talkManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        organizerManager.deleteEvent(actInfo[5], actTime);
        organizerManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
    }

    public ArrayList<String[]> getAllActivities() throws NoEventsException {
        ArrayList<String[]> allActivities = talkManager.viewUpcomingActivites();
        if (allActivities.size() == 0){
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }
}
