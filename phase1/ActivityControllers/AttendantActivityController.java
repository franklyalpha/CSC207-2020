package ActivityControllers;

import globalConstants.ActivityNotFoundException;
import presenter.Presenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class AttendantActivityController extends UserActivityController implements IAttendantActivity{
    public AttendantActivityController(Object[] managers) {
        super(managers);
    }

    @Override
    public void enrollConference() {
        ArrayList<String> userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
        ArrayList<String[]> available = availableSchedules();
        ArrayList<String> actIDs = extractActIDHelper(available);

        chooseActToEnroll(userName, available, actIDs);
    }

    private void chooseActToEnroll(ArrayList<String> userName, ArrayList<String[]> available,
                                   ArrayList<String> actIDs) {
        while(true){
            try{
                String activityID = findActivityID(available, "enroll");
                String[] temp = activityManager.searchActivityByUUID(activityID);
                if (actIDs.contains(activityID)){
                    addEnrollment(temp, activityID, userName);
                    break;
                }
                else{
                    throw new ActivityNotFoundException("The ID of activity isn't right");
                }
            }catch(ActivityNotFoundException e){
                Presenter.printInvalid("activity ID");
            }
        }
    }

    private String findActivityID(ArrayList<String[]> available, String action){
        Scanner scan = new Scanner(System.in);
        if (action.equals("enroll")){
            Presenter.printDescription("available activities you can enroll");
            Presenter.printSchedule(available);
            Presenter.printActivityIDPrompt("enroll");
        }
        else{
            Presenter.printDescription("available activities you can cancel");
            Presenter.printSchedule(available);
            Presenter.printActivityIDPrompt("cancel");
        }
        return scan.nextLine();
    }

    private void addEnrollment(String[] temp, String activityID, ArrayList<String> userName){
        LocalDateTime[] time = getTimeHelper(temp);
        userManager.selfAddSchedule(time, UUID.fromString(activityID));
        UUID conferenceChat = activityManager.getConferenceChat(UUID.fromString(temp[0]));
        messageRoomManager.addUser(userName, conferenceChat);
        activityManager.addAttendant(UUID.fromString(activityID), userManager.currentUsername());
    }

    @Override
    public void cancelEnrollment() {
        ArrayList<String> userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
        ArrayList<String[]> enrolled = viewEnrolledSchedule();
        ArrayList<String> actIDs = extractActIDHelper(enrolled);

        chooseActToCancel(userName, enrolled, actIDs);
    }

    private void chooseActToCancel(ArrayList<String> userName, ArrayList<String[]> enrolled,
                                   ArrayList<String> actIDs) {
        while (true){
            try{
                String activityID = findActivityID(enrolled, "cancel");
                if(actIDs.contains(activityID)){
                    cancelEnrollmentUpdate(userName, activityID);
                    break;
                }
                else{
                    throw new ActivityNotFoundException("You aren't enrolled in this activity");
                }
            }catch(ActivityNotFoundException e){
                Presenter.printInvalid("activity ID");
            }
        }
    }

    private void cancelEnrollmentUpdate(ArrayList<String> userName, String activityID){
        UUID actID = UUID.fromString(activityID);
        activityManager.removeAttendant(actID, userManager.currentUsername());
        messageRoomManager.removeUser(userName,activityManager.getConferenceChat(actID));
        String[] actInfo = activityManager.searchActivityByUUID(activityID);
        LocalDateTime[] time = getTimeHelper(actInfo);
        userManager.deleteActivity(time);
    }
}
