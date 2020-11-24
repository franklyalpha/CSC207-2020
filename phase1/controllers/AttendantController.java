package controllers;

import globalConstants.ActivityNotFoundException;
import presenter.Presenter;
import useCases.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;


/**
 * Represents a <code>AttendantController</code> extends from <code>UserController</code>.
 * Is specific for <code>Attendant</code> type usage.
 * Includes:
 * Own constructor
 * run: the method for receiving user's inputs for actions and call corresponding method.
 * viewSchedules: responsible for gathering all available conferences the user can enroll and print out.
 * enrollConference: responsible for enrolling the user into available conference after checking constraints.
 * cancelEnrollment: responsible for cancelling any conferences user is enrolled.
 */
public class AttendantController extends UserController{
    /**
     * an Arraylist of <code>availableAction</code>;
     * an Arraylist of <code>availableMethod</code>;
     */
    ArrayList<String> availableAction = new ArrayList<>();
    ArrayList<String> availableMethod = new ArrayList<>();

    /**
     * Creates <code>AttendantController</code> with all use-case classes being initialized.
     */
    public AttendantController(UserManager manager){
        super(manager);
    }
    /*
    require implementation:
    view all available schedules they can enroll
    enroll into one conference, cancel enrollment
     */

    /**
     * This method allows users to do actions corresponding to attendant's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
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
        switch(action){
            case 1: viewSchedules(); break;
            case 2: viewEnrolledSchedule(); break;
            case 3: enrollConference(); break;
            case 4: cancelEnrollment(); break;
            case 5: sendPrivateMessage(); break;
            case 6: viewPrivateMessage(); break;
            case 7: viewGroupMessage(); break;
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
    private ArrayList<String[]> availableSchedules(){
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

    /**
     * Print out the schedule of all activities this attendant can sign in.
     * Information include: topic of activity, start time and end time, ID of assigned room,
     * ID of this activity and name of speaker.
     * Specific format will be determined in Presenter.
     */
    protected void viewSchedules(){
        ArrayList<String[]> result = this.availableSchedules();
        Presenter.printSchedule(result);
    }

    //add a new activity to this user, and add this user to the corresponding conference chat.
    /**
     * Provides instructions to the user to enroll in conferences available. Available means having space
     * and doesn't conflict with schedule of this user.
     * Will print out available conferences the user can enroll, and ask user to input the UUID of
     * conference the user wish to enroll.
     */
    protected void enrollConference(){
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
    /**
     * Provides instructions for user to cancel conferences this user enrolled.
     * Will print all conferences the user enrolled, and ask user to input the UUID of conference the user
     * wish to cancel.
     */
    protected void cancelEnrollment(){
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
