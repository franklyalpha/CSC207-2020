package Controllers;

import useCases.PanelManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A subclass of <>SpeakerReschedulingController</> responsible for updating speakers from <>Panel</>.
 */
public class PanelRescheduleSpeakerController extends SpeakerReschedulingController {

    /**
     * An instance of <>PanelManager</>.
     */
    private PanelManager panelManager;

    /**
     * The constructor of this controller.
     * @param userController An instance of <>UserController</>.
     */
    public PanelRescheduleSpeakerController(UserController userController) {
        super(userController);
        panelManager = new PanelManager(eventManager);
    }

    /**
     * responsible for adding another <>Speaker</> towards a <>Panel</>.
     * @param speaker the speaker about to be added to a <>Panel</>.
     */
    public void addNewSpeaker(String speaker){
        panelManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        organizerManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
    }

    /**
     * returns a list of all <>Speaker</>'s username who are assigned to a given <>Panel</>.
     * @param actID the Id of a given <>Panel</>.
     * @return an instance of <>ArrayList<String></> of all <>Speaker</>'s username who are assigned to
     * the given <>Panel</>.
     */
    public ArrayList<String> enrolledSpeakers(String actID){
        actInfo = findEventInfo(actID);
        actTime = getTimeHelper(actInfo);
        return panelManager.getSpeakersList(actID);
    }

    /**
     * deletes a speaker who has been assigned to a given <>Panel</> and updates all relevant information of the system.
     * @param speaker the user name of <>Speaker</> who is about to quit the <>Panel</>.
     */
    public void deleteSpeaker(String speaker){
        panelManager.deleteSpeaker(UUID.fromString(actInfo[0]), speaker);
        organizerManager.deleteEvent(speaker, actTime);
    }


}
