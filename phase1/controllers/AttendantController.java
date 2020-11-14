package controllers;

import useCases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            ArrayList<String> avaiableAction = new ArrayList<>();
            avaiableAction.add("Avaiable conferences provided");
            avaiableAction.add("View Signed conferences");
            avaiableAction.add("Sign up for a conference");
            avaiableAction.add("Cancel conference");
            avaiableAction.add("Send messages to a person");
            avaiableAction.add("view messages from others");
            avaiableAction.add("View groups' messages");
            String action = "";
            boolean enteraction = true;
            while(enteraction){
                Scanner scan = new Scanner(System.in);
                System.out.println("Service apply\n");
                for(String a: avaiableAction){
                    System.out.println(a);
                }
                action = scan.nextLine();
                if (avaiableAction.contains(action)){
                    if(action.equals(avaiableAction.get(0))){
                        this.viewSchedules();
                        enteraction = this.continuing();
                    }
                    if(action.equals(avaiableAction.get(1))){
                        this.viewEnrolledSchedule();
                        enteraction = this.continuing();
                    }
                    if(action.equals(avaiableAction.get(2))){
                        this.enrollConference();
                        enteraction = this.continuing();
                    }
                    if(action.equals(avaiableAction.get(3))){
                        this.cancelEnrollment();
                        enteraction = this.continuing();
                    }
                    if(action.equals(avaiableAction.get(4))){
                        this.sendPrivateMessage();
                        enteraction = this.continuing();
                    }
                    if(action.equals(avaiableAction.get(5))){
                        this.viewPrivateMessage();
                        enteraction = this.continuing();
                    }
                    if(action.equals(avaiableAction.get(6))){
                        this.viewGroupMessage();
                        enteraction = this.continuing();
                    }
                }
                else{
                    System.out.println("Invalid service, please enter again.");
                }
            }
        }

    //check whether the room is full, and whether this user is currently enroll.
    private ArrayList<String[]> availableSchedules(){
        ArrayList<String[]> schedules = actmanag.viewUpcommingActivites();
        ArrayList<String> temp = new ArrayList<String>();

        //activity that is full and user is not free.
        for(String[] d: schedules){
            if (!roomma.CheckRoomFullness(actmanag.numAttendant(UUID.fromString(d[0])), UUID.fromString(d[4]))){
                temp.add(actmanag.searchActivityByUUID(d[0])[0]);
            }
            LocalDateTime[] time = getTimeHelper(d);
            if(!userma.isFree(time)){
                temp.add(actmanag.searchActivityByUUID(d[0])[0]);
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
        ArrayList<String> userName = new ArrayList<String>();
        userName.add(userma.currentUsername());
        Scanner scan = new Scanner(System.in);

        ArrayList<String[]> available = availableSchedules();
        ArrayList<String> actIDs = extractActIDHelper(available);
        System.out.println("here are available activities you can enroll: " + actIDs);
        System.out.println("please input the activity's ID " +
                "you wish to enroll");
        String activityID = scan.nextLine();
        String[] temp = actmanag.searchActivityByUUID(activityID);
        if (actIDs.contains(activityID)){
            LocalDateTime[] time = getTimeHelper(temp);
            userma.selfAddSchedule(time, UUID.fromString(activityID));
            UUID conferenceChat = actmanag.getConferenceChat(UUID.fromString(temp[0]));
            chatmana.addUser(userName, conferenceChat);
            actmanag.addAttendant(UUID.fromString(activityID), userma.currentUsername());
        }
        else{
            System.out.println("Invalid activity ID.");
        }
    }

    public void cancelEnrollment(){
        ArrayList<String> userName = new ArrayList<String>();
        userName.add(userma.currentUsername());
        Scanner scan = new Scanner(System.in);

        ArrayList<String[]> enrolled = viewEnrolledSchedule();
        ArrayList<String> actIDs = extractActIDHelper(enrolled);
        System.out.println("here are activities you've enrolled: "+actIDs);
        System.out.println("please input the activity's ID " +
                "you wish to cancel");
        String activityID = scan.nextLine();
        HashMap<LocalDateTime[], UUID> temp = userma.getActivities();
        //check whether this activity user has enrolled.
        UUID actID = UUID.fromString(activityID);
        if(temp.containsValue(actID)){
            actmanag.removeAttendant(actID, userma.currentUsername());
            chatmana.removeUser(userName,actmanag.getConferenceChat(actID));
            String[] actInfo = actmanag.searchActivityByUUID(activityID);
            // update user's enrollment in User-manager
            LocalDateTime[] time = getTimeHelper(actInfo);
            userma.deleteActivity(time);
        }
        else{
            System.out.println("Invalid activity ID.");
        }
    }

    private boolean continuing(){
        boolean enteraction = true;
        System.out.println("Continue for other services? Please enter yes or no");
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("yes")){
            enteraction = false;
        }
        return enteraction;
    }
}
