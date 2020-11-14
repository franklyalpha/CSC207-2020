package controllers;

import useCases.UserManager;

import java.time.LocalDateTime;
import java.util.*;

public class AttendantController extends UserController{

    public AttendantController(UserManager manager){
        super(manager);
    }
    /*
    require implementation:
    view all available schedules they can enroll
    enroll into one conference, cancel enrollment
     */

    @Override
    public void run() {
        ArrayList<String> availableAction = new ArrayList<>();
        availableAction.add("Available conferences provided");
        availableAction.add("View Signed conferences");
        availableAction.add("Sign up for a conference");
        availableAction.add("Cancel conference");
        availableAction.add("Send messages to a person");
        availableAction.add("view messages from others");
        availableAction.add("View groups' messages");
        availableAction.add("log out");
        int action = 0;
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            System.out.println("Services apply\n");
            for(String a: availableAction){
                System.out.println(availableAction.indexOf(a)+1 + " " + a);

            }
            action = scan.nextInt();
            switch (action){
                case 1 : viewSchedules();
                case 2: viewEnrolledSchedule();
                case 3 : enrollConference();
                case 4 : cancelEnrollment();
                case 5 : sendPrivateMessage();
                case 6 : viewPrivateMessage();
                case 7 : viewGroupMessage();
                case 8 :
                    logout();
                    enterAction = false;
                    break;
                default: System.out.println("invalid action.");
            }
            if (!enterAction){
                boolean whetherContinue = continuing();
                if (!whetherContinue){
                    logout();
                    enterAction = false;
                }

            }
        }
    }


    //check whether the room is full, and whether this user is currently enroll.
    private ArrayList<String[]> availableSchedules(){
        ArrayList<String[]> schedules = activityManager.viewUpcommingActivites();
        ArrayList<String> temp = new ArrayList<>();

        //activity that is full and user is not free.
        for(String[] d: schedules){
            if (!roomManager.CheckRoomFullness(activityManager.numAttendant(UUID.fromString(d[0])), UUID.fromString(d[4]))){
                temp.add(activityManager.searchActivityByUUID(d[0])[0]);
            }
            LocalDateTime[] time = getTimeHelper(d);
            if(!userManager.isFree(time)){
                temp.add(activityManager.searchActivityByUUID(d[0])[0]);
            }
        }

        schedules.removeIf(info -> temp.contains(info[0]));
        return schedules;
    }

    public void viewSchedules(){
        ArrayList<String[]> result = this.availableSchedules();
        System.out.println(result);
        // presenter
    }

    //add a new activity to this user, and add this user to the corresponding conference chat.
    public void enrollConference(){
        ArrayList<String> userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
        Scanner scan = new Scanner(System.in);

        ArrayList<String[]> available = availableSchedules();
        ArrayList<String> actIDs = extractActIDHelper(available);
        System.out.println("here are available activities you can enroll: " + actIDs);
        System.out.println("please input the activity's ID " +
                "you wish to enroll");
        String activityID = scan.nextLine();
        String[] temp = activityManager.searchActivityByUUID(activityID);
        if (actIDs.contains(activityID)){
            LocalDateTime[] time = getTimeHelper(temp);
            userManager.selfAddSchedule(time, UUID.fromString(activityID));
            UUID conferenceChat = activityManager.getConferenceChat(UUID.fromString(temp[0]));
            chatroomManager.addUser(userName, conferenceChat);
            activityManager.addAttendant(UUID.fromString(activityID), userManager.currentUsername());
        }
        else{
            System.out.println("Invalid activity ID.");
        }
    }

    public void cancelEnrollment(){
        ArrayList<String> userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
        Scanner scan = new Scanner(System.in);

        ArrayList<String[]> enrolled = viewEnrolledSchedule();
        ArrayList<String> actIDs = extractActIDHelper(enrolled);
        System.out.println("here are activities you've enrolled: "+actIDs);
        System.out.println("please input the activity's ID " +
                "you wish to cancel");
        String activityID = scan.nextLine();
        HashMap<LocalDateTime[], UUID> temp = userManager.getActivities();
        //check whether this activity user has enrolled.
        UUID actID = UUID.fromString(activityID);
        if(temp.containsValue(actID)){
            activityManager.removeAttendant(actID, userManager.currentUsername());
            chatroomManager.removeUser(userName,activityManager.getConferenceChat(actID));
            String[] actInfo = activityManager.searchActivityByUUID(activityID);
            // update user's enrollment in User-manager
            LocalDateTime[] time = getTimeHelper(actInfo);
            userManager.deleteActivity(time);
        }
        else{
            System.out.println("Invalid activity ID.");
        }
    }

    private boolean continuing(){
        boolean enteraction = true;
        System.out.println("Continue for other services? Please enter true or false. (false for log out)");
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true")){
            enteraction = false;
        }
        return enteraction;
    }

}
