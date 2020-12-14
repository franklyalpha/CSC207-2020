package Controllers;


import useCases.TalkManager;
import java.util.UUID;

/**
 * The controller responsible for rescheduling a speaker from talk
 */
public class TalkRescheduleSpeakerController extends SpeakerReschedulingController {

    /**
     * An instance of <>TalkManager</>.
     */
    private TalkManager talkManager;

    /**
     * the constructor of this controller.
     * @param userController an instance of <>UserController</>.
     */
    public TalkRescheduleSpeakerController(UserController userController) {
        super(userController);
        talkManager = new TalkManager(eventManager);
    }

    /**
     * assigns new <>Speaker</> to given <>Talk</> and update all info of this system.
     * @param speaker the username of <>Speaker</> about to be assigned to given <>Talk</>.
     */
    public void updateRescheduledSpeaker(String speaker){
        String originalSpeaker = talkManager.getSpeaker(UUID.fromString(actInfo[0]));
        talkManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        organizerManager.deleteEvent(originalSpeaker, actTime);
        organizerManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
    }
}
