package controllers;

import useCases.UserManager;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;
import presenter.*;
import java.lang.reflect.Method;

public class AttendantController extends UserController{
    ArrayList<String> availableAction = new ArrayList<>();
    ArrayList<String> availableMethod = new ArrayList<>();

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
        addMenu();
        addActions();
        int action;
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            /*System.out.println("Services apply\n");
            for(String a: availableAction){
                System.out.println(availableAction.indexOf(a)+1 + " " + a);

            }*/
            Presenter.printAvailableActions(availableAction);
            action = scan.nextInt();
            if (0 < action && action <= availableMethod.size()) {
                runMethod(action);
            }
            else{
                Presenter.printInvalid("input");
            }
            enterAction = continuing();
        }
        logout();
    }

    private void runMethod(int action){
        try {
            Method method = this.getClass().getDeclaredMethod(availableMethod.get(action - 1));
            try {
                method.invoke(this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    private void addMenu(){
        availableAction.add("Available conferences provided");
        availableAction.add("View Signed conferences");
        availableAction.add("Sign up for a conference");
        availableAction.add("Cancel conference");
        availableAction.add("Send messages to a person");
        availableAction.add("View messages from others");
        availableAction.add("View groups' messages");

    }

    private void addActions(){
        availableMethod.add("viewSchedules");
        availableMethod.add("viewEnrolledSchedule");
        availableMethod.add("enrollConference");
        availableMethod.add("cancelEnrollment");
        availableMethod.add("sendPrivateMessage");
        availableMethod.add("viewPrivateMessage");
        availableMethod.add("viewGroupMessage");
    }


    //check whether the room is full, and whether this user is currently enroll.
    protected ArrayList<String[]> availableSchedules(){
        ArrayList<String[]> schedules = activityManager.viewUpcommingActivites();
        ArrayList<String> temp = new ArrayList<>();

        //activity that is full and user is not free.
        for(String[] info: schedules){
            if(checkIsConflict(info)){
                temp.add(activityManager.searchActivityByUUID(info[0])[0]);
            }
        }
        schedules.removeIf(info -> temp.contains(info[0]));
        return schedules;
    }

    private boolean checkIsConflict(String[] info){
        if (!roomManager.CheckRoomFullness(activityManager.numAttendant(UUID.fromString(info[0])),
                UUID.fromString(info[4]))){
            return true;
        }
        LocalDateTime[] time = getTimeHelper(info);
        return !userManager.isFree(time);
    }

    protected void viewSchedules(){
        ArrayList<String[]> result = this.availableSchedules();
        Presenter.printSchedule(result);
    }

    //add a new activity to this user, and add this user to the corresponding conference chat.
    protected void enrollConference(){
        ArrayList<String> userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
        ArrayList<String[]> available = availableSchedules();
        ArrayList<String> actIDs = extractActIDHelper(available);

        String activityID = findActivityID(available, "enroll");
        String[] temp = activityManager.searchActivityByUUID(activityID);
        if (actIDs.contains(activityID)){
            addEnrollment(temp, activityID, userName);
        }
        else{
            Presenter.printInvalid("activity ID");
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
        chatroomManager.addUser(userName, conferenceChat);
        activityManager.addAttendant(UUID.fromString(activityID), userManager.currentUsername());
    }

    protected void cancelEnrollment(){
        ArrayList<String> userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
        ArrayList<String[]> enrolled = viewEnrolledSchedule();
        ArrayList<String> actIDs = extractActIDHelper(enrolled);
        String activityID = findActivityID(enrolled, "cancel");

        if(actIDs.contains(activityID)){
            cancelEnrollment(userName, activityID);
        }
        else{
            Presenter.printInvalid("activity ID");
        }
    }

    private void cancelEnrollment(ArrayList<String> userName, String activityID){
        UUID actID = UUID.fromString(activityID);
        activityManager.removeAttendant(actID, userManager.currentUsername());
        chatroomManager.removeUser(userName,activityManager.getConferenceChat(actID));
        String[] actInfo = activityManager.searchActivityByUUID(activityID);
        LocalDateTime[] time = getTimeHelper(actInfo);
        userManager.deleteActivity(time);
    }

    private boolean continuing(){
        boolean enterAction = true;
        //System.out.println("Continue for other services? Please enter true or false. (false for log out)");
        Presenter.printContinueServicePrompt();
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true")){
            enterAction = false;
        }
        return enterAction;
    }

}
