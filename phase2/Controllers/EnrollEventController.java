package Controllers;

import globallyAccessible.EventNotFoundException;
import useCases.AttendeeManager;
import useCases.EventManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class EnrollEventController extends EventController {
    private ArrayList<String> userName = new ArrayList<>();
    private AttendeeManager attendeeManager;

    public EnrollEventController(UserController userController) {
        super(userController);
        userName.add(userManager.currentUsername());
        attendeeManager = new AttendeeManager(userManager);
    }


    public void chooseActToEnroll(ArrayList<String[]> available, String activityID
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
        UUID conferenceChat = eventManager.getConferenceChat(UUID.fromString(temp[0]));
        messageRoomManager.addUser(userName, conferenceChat);
        eventManager.addAttendee(UUID.fromString(activityID), userManager.currentUsername());
    }
}
