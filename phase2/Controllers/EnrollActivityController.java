package Controllers;

import globallyAccessible.ActivityNotFoundException;
import Presenters.Presenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class EnrollActivityController extends ActivityController {
    private ArrayList<String> userName = new ArrayList<>();

    public EnrollActivityController(UserController userController) {
        super(userController);
        userName.add(userManager.currentUsername());
    }


    public void chooseActToEnroll(ArrayList<String[]> available, String activityID
                                   ) throws ActivityNotFoundException {
        ArrayList<String> actIDs = extractActIDHelper(available);
        String[] temp = activityManager.searchActivityByUUID(activityID);
        if (actIDs.contains(activityID)){
            addEnrollment(temp, activityID, userName);
        }
        else{
            throw new ActivityNotFoundException("The ID of activity isn't right");
        }
    }


    private void addEnrollment(String[] temp, String activityID, ArrayList<String> userName){
        LocalDateTime[] time = getTimeHelper(temp);
        userManager.selfAddSchedule(time, UUID.fromString(activityID));
        UUID conferenceChat = activityManager.getConferenceChat(UUID.fromString(temp[0]));
        messageRoomManager.addUser(userName, conferenceChat);
        activityManager.addAttendee(UUID.fromString(activityID), userManager.currentUsername());
    }
}
