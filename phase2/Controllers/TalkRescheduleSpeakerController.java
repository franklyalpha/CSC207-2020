package Controllers;

import globallyAccessible.NoEventsException;
import useCases.TalkManager;

import java.util.ArrayList;
import java.util.UUID;

public class TalkRescheduleSpeakerController extends SpeakerReschedulingController {

    private TalkManager talkManager;



    public TalkRescheduleSpeakerController(UserController userController) {
        super(userController);
        talkManager = new TalkManager(eventManager);
    }

    public void updateRescheduledSpeaker(String speaker){
        String originalSpeaker = talkManager.getSpeaker(UUID.fromString(actInfo[0]));
        talkManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        organizerManager.deleteEvent(originalSpeaker, actTime);
        organizerManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
    }

    public ArrayList<String[]> getAllActivities() throws NoEventsException {
        ArrayList<String[]> allActivities = talkManager.viewUpcomingActivities();
        if (allActivities.size() == 0){
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }
}
