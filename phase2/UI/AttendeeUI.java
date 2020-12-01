package UI;

import Controllers.EnrollActivityController;
import Controllers.QuitActivityController;
import Controllers.UserController;
import Presenters.AvailableSchedulePresenter;
import Presenters.EnrolledSchedulePresenter;
import Presenters.Presenter;
import globallyAccessible.ActivityNotFoundException;
import menuPresenter.AttendeePresenter;
import useCases.UserManager;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Represents a <code>AttendeeController</code> extends from <code>UserController</code>.
 * Is specific for <code>Attendee</code> type usage.
 * Includes:
 * Own constructor
 * run: the method for receiving user's inputs for actions and call corresponding method.
 * viewSchedules: responsible for gathering all available conferences the user can enroll and print out.
 * enrollConference: responsible for enrolling the user into available conference after checking constraints.
 * cancelEnrollment: responsible for cancelling any conferences user is enrolled.
 */
public class AttendeeUI extends UserUI{

    final private AttendeePresenter attendeePresenter = new AttendeePresenter();

    public AttendeeUI(UserController userController) {
        super(userController);
    }

    /**
     * This method allows users to do actions corresponding to attendee's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
     */
    public void run(){
        addMenu();
        int action;
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            /*System.out.println("Services apply\n");
            for(String a: availableAction){
                System.out.println(availableAction.indexOf(a)+1 + " " + a);
            }*/
            System.out.println(attendeePresenter.strAvailableActions(availableAction));
            if (scan.hasNextInt()){   //if the input is an integer
                action = scan.nextInt();    //set input to action
                if (0 < action && action <= availableAction.size()) {   // if action is within possible actions
                    runMethod(action);  // do the thing
                }
                else {
                    System.out.println(attendeePresenter.strInvalidInput());
                    continue;
                }
            }
            else{
                System.out.println(attendeePresenter.strInvalidInput());
                scan.next();
                continue;
            }
            enterAction = continuing();
        }
        userController.logout();
    }

    private void runMethod(int action) {
        switch(action){
            case 1: viewAvailableSchedules(); break;
            case 2: viewEnrolledSchedule(); break;
            case 3: enrollConference(); break;
            case 4: cancelEnrollment(); break;
            case 5: sendPrivateMessage(); break;
            case 6: viewPrivateMessage(); break;
            case 7: viewGroupMessage(); break;
            case 8: outputAllUpcomingEventsPdf(); break;
        }
    }


    private void addMenu(){
        availableAction.add("- View available events");
        availableAction.add("- View conferences you have signed up for");
        availableAction.add("- Sign up for an event");
        availableAction.add("- Cancel event registration");
        availableAction.add("- Send a private message");
        availableAction.add("- View private messages");
        availableAction.add("- View group messages");
        availableAction.add("- Generate all upcoming events pdf");
    }
    //TODO move this to presenter

    protected void enrollConference(){
        AvailableSchedulePresenter schedulePresenter = new AvailableSchedulePresenter(userController);
        ArrayList<String[]> availables = schedulePresenter.viewAvailableSchedules();
        if (availables.size() == 0){
            return;
        }
        inputAndEnrollActivity(availables);
    }

    private void inputAndEnrollActivity(ArrayList<String[]> availables) {
        EnrollActivityController enroll = new EnrollActivityController(userController);
        while(true){
            try{
                String actID = getAvailableActivityID(availables);
                enroll.chooseActToEnroll(availables, actID);
                break;
            }catch(ActivityNotFoundException e){
                System.out.println(attendeePresenter.strInvalidActivityID());
            }
        }
    }

    private String getAvailableActivityID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrollMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strEnrollPrompt());
        return scan.nextLine();
    }

    protected void cancelEnrollment(){
        EnrolledSchedulePresenter enrolledPresenter = new EnrolledSchedulePresenter(userController);
        ArrayList<String[]> enrolled = enrolledPresenter.viewEnrolledSchedule();
        if (enrolled.size() == 0){
            return;
        }
        inputAndQuitActivity(enrolled);
    }

    private void inputAndQuitActivity(ArrayList<String[]> enrolled) {
        QuitActivityController quit = new QuitActivityController(userController);
        while (true){
            try{
                String activityID = getEnrolledActivityID(enrolled);
                quit.chooseActToCancel(enrolled, activityID);
                break;
            }catch(ActivityNotFoundException e){
                System.out.println(attendeePresenter.strInvalidActivityID());
            }
        }
    }

    private String getEnrolledActivityID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrolledMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strCancelPrompt());
        return scan.nextLine();
    }


    //check whether the room is full, and whether this user is currently enroll.

    //add a new activity to this user, and add this user to the corresponding conference chat.
    /**
     * Provides instructions to the user to enroll in conferences available. Available means having space
     * and doesn't conflict with schedule of this user.
     * Will print out available conferences the user can enroll, and ask user to input the UUID of
     * conference the user wish to enroll.
     */


    /**
     * Provides instructions for user to cancel conferences this user enrolled.
     * Will print all conferences the user enrolled, and ask user to input the UUID of conference the user
     * wish to cancel.
     */


    private boolean continuing(){
        boolean enterAction = true;
        System.out.println(attendeePresenter.strContinueServicePrompt());
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true")){
            enterAction = false;
        }
        return enterAction;
    }

}
