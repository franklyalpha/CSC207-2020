package UI;

import Controllers.TalkRescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.EventNotFoundException;
import globallyAccessible.NoEventsException;

import java.util.ArrayList;

public class OrganizerTalkSpeakerUI extends OrganizerRescheduleSpeakerUI {
    private TalkRescheduleSpeakerController talkRescheduleSpeaker;

    public OrganizerTalkSpeakerUI(UserController userController) {
        super(userController);
        talkRescheduleSpeaker = new TalkRescheduleSpeakerController(userController);
    }

    @Override
    public void run() {
        try{
            String actID = eventSelect();
            String speaker = determineSpeakerValidity(actID);
            talkRescheduleSpeaker.updateRescheduledSpeaker(speaker);
        } catch(NoEventsException e) {
            e.printStackTrace();
        }
    }

    private String determineSpeakerValidity(String actID) throws NoEventsException {
        ArrayList<String> availableSpeakers = talkRescheduleSpeaker.availableSpeakers(actID);
        if(availableSpeakers.size() == 0) {
            throw new NoEventsException("No available speakers for this period!!!");
        }
        return chooseSpeaker(availableSpeakers, "assign");
    }

    private String eventSelect() throws NoEventsException {
        ArrayList<String[]> allActivities = talkRescheduleSpeaker.getAllActivities();
        System.out.println(organizerRescheduleSpeakerPresenter.strAllEventMenuDes());
        System.out.println(organizerRescheduleSpeakerPresenter.strSchedule(allActivities));
        return inputSelection(allActivities, talkRescheduleSpeaker);
    }
}