package Controllers;

import globallyAccessible.EventNotFoundException;
import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The controller responsible for rescheduling speakers of an event.
 */
public class SpeakerReschedulingController extends EventController {
    /**
     * An instance of <>String[]</> containing information of the <>Event</> about to be updated.
     */
    protected String[] actInfo;
    /**
     * An instance of <>LocalDateTime[]</> of the period of <>Event</> about to be updated.
     */
    protected LocalDateTime[] actTime;
    /**
     * An instance of <>OrganizerManager</>.
     */
    protected OrganizerManager organizerManager;

    /**
     * the constructor of this controller;
     * @param userController An instance of <>UserController</>.
     */
    public SpeakerReschedulingController(UserController userController) {
        super(userController);
        actInfo = new String[]{};
        actTime = new LocalDateTime[]{};
        organizerManager = new OrganizerManager(userManager);
    }

    /**
     * returns a list of username of all <>Speaker</> who are free when the <>Event</> is happening.
     * @param actID the ID of <>Event</> which requires modifying <>Speaker</>.
     * @return An instance of <>ArrayList</> containing username of all <>Speakers</> free when <>Event</> is happening.
     */
    public ArrayList<String> availableSpeakers(String actID){
        actInfo = findEventInfo(actID);
        actTime = getTimeHelper(actInfo);
        ArrayList<String> freeSpeakers = organizerManager.availableSpeakers(actTime);
//        freeSpeakers.add(actInfo[5]);
        return freeSpeakers;
    }

    /**
     * returns the information of an event with given <>actID</>.
     * @param actID the ID of an <>Event</>.
     * @return a <>String</> containing relevant info of <>Event</>.
     */
    protected String[] findEventInfo(String actID) {
        return eventManager.searchEventByUUID(actID);
    }

    /**
     * checks whether the ID given corresponds to any <>Event</> stored in the system.
     * @param allActivities An instance of <>ArrayList</> containing <>String[]</> representing relevant info
     *                      of an <>Event</>.
     * @param actID the ID of <>Event</> in which the <>Speaker</> will be modified.
     * @return an instance of <>String</> representing the <>Event</>.
     * @throws EventNotFoundException is thrown when there's no <>Events</> correspond to given <>actID</>.
     */
    public String checkingValidEventID(ArrayList<String[]> allActivities, String actID)
            throws EventNotFoundException {
        if (! extractActIDHelper(allActivities).contains(actID)){
            throw new EventNotFoundException("invalid activity ID");
        }
        return actID;
    }
}
