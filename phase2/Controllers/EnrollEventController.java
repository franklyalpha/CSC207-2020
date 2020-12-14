package Controllers;

import globallyAccessible.EventNotFoundException;
import useCases.AttendeeManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The controller responsible for enrolling an attendee into an event and update all information associated with this action.
 *
 */
public class EnrollEventController extends EventController {
    /**
     * An instance of <>ArrayList</> containing username of a <>User</>.
     */
    private ArrayList<String> userName = new ArrayList<>();

    /**
     * An instance of <>AttendeeManager</>.
     */
    private AttendeeManager attendeeManager;

    /**
     * The constructor of this controller.
     * @param userController An instance of UserController.
     */
    public EnrollEventController(UserController userController) {
        super(userController);
        userName.add(userManager.currentUsername());
        attendeeManager = new AttendeeManager(userManager);
    }

    /**
     * Adds user to participant list of an <>Event</> given the expected info of event.
     * @param available An instance of <>ArrayList<String></> containing info of all events the user can enroll
     * @param activityID a string representing the ID of event the user wish to enroll.
     * @throws EventNotFoundException is thrown when the inputted <>activityID</> is not the one the user can enroll.
     */
    public void enrollEvent(ArrayList<String[]> available, String activityID
                                   ) throws EventNotFoundException {
        ArrayList<String> actIDs = extractActIDHelper(available);
        String[] temp = eventManager.searchEventByUUID(activityID);
        if (actIDs.contains(activityID)){
            addEnrollment(temp, activityID, userName);
        }
        else{
            throw new EventNotFoundException("The ID of activity isn't right");
        }
    }


    private void addEnrollment(String[] temp, String activityID, ArrayList<String> userName){
        LocalDateTime[] time = getTimeHelper(temp);
        attendeeManager.selfAddSchedule(time, UUID.fromString(activityID));
        UUID conferenceChat = eventManager.getEventChat(UUID.fromString(temp[0]));
        messageRoomManager.addUser(userName, conferenceChat);
        eventManager.addAttendee(UUID.fromString(activityID), userManager.currentUsername());
    }
}
