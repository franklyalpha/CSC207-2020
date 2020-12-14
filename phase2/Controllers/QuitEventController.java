package Controllers;

import globallyAccessible.EventNotFoundException;
import useCases.AttendeeManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Responsible for deleting a participant of a given event.
 */
public class QuitEventController extends EventController {
    /**
     * The <>ArrayList</> containing username of <>User</>.
     */
    private ArrayList<String> userName;
    /**
     * An instance of <>AttendeeManager</>.
     */
    private AttendeeManager attendeeManager;

    /**
     * The constructor of this controller class.
     * @param userController: an instance of <>UserController</>.
     */
    public QuitEventController(UserController userController) {
        super(userController);
        userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
        attendeeManager = new AttendeeManager(userManager);
    }

    /**
     * finds and cancels enrollment of a given <>User</> with given ID of an <>Event</>.
     * @param enrolled an <>ArrayList</> containing information of activities enrolled by the <>User</>
     *                 currently using the program.
     * @param activityID: the ID of the <>Event</> the <>User</> potentially wants to quit.
     * @throws EventNotFoundException is thrown when the <>User</> is not enrolled in the <>Event</>
     * with given <>activityID</>.
     */
    public void chooseActToCancel(ArrayList<String[]> enrolled, String activityID)
            throws EventNotFoundException {
        ArrayList<String> actIDs = extractActIDHelper(enrolled);
        if(actIDs.contains(activityID)){
            cancelEnrollmentUpdate(userName, activityID);
        }
        else{
            throw new EventNotFoundException("You aren't enrolled in this activity");
        }
    }

    private void cancelEnrollmentUpdate(ArrayList<String> userName, String activityID){
        UUID actID = UUID.fromString(activityID);
        eventManager.removeAttendee(actID, userManager.currentUsername());
        messageRoomManager.removeUser(userName, eventManager.getEventChat(actID));
        String[] actInfo = eventManager.searchEventByUUID(activityID);
        LocalDateTime[] time = getTimeHelper(actInfo);
        attendeeManager.deleteEvent(time);
    }
}
