package UserControllers;

import ActivityControllers.AttendantActivityController;
import MessagingControllers.AttendantMessagingController;
import presenter.Presenter;
import useCases.UserManager;

import java.util.ArrayList;
import java.util.Scanner;


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
    AttendantMessagingController messagingController;
    AttendantActivityController activityController;

    /**
     * Creates <code>AttendantController</code> with all use-case classes being initialized.
     */
    public AttendantController(UserManager manager){
        super(manager);
        Object[] managers = new Object[]{messageRoomManager, activityManager, userManager};
        messagingController = new AttendantMessagingController(managers);
        activityController = new AttendantActivityController(managers);
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
            case 1: activityController.viewAvailableSchedules(); break;
            case 2: activityController.viewEnrolledSchedule(); break;
            case 3: activityController.enrollConference(); break;
            case 4: activityController.cancelEnrollment(); break;
            case 5: messagingController.sendPrivateMessage(); break;
            case 6: messagingController.viewPrivateMessage(); break;
            case 7: messagingController.viewGroupMessage(); break;
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






    //add a new activity to this user, and add this user to the corresponding conference chat.
    /**
     * Provides instructions to the user to enroll in conferences available. Available means having space
     * and doesn't conflict with schedule of this user.
     * Will print out available conferences the user can enroll, and ask user to input the UUID of
     * conference the user wish to enroll.
     */
    protected void enrollConference(){

    }






    /**
     * Provides instructions for user to cancel conferences this user enrolled.
     * Will print all conferences the user enrolled, and ask user to input the UUID of conference the user
     * wish to cancel.
     */
    protected void cancelEnrollment(){

    }





    private boolean continuing(){
        boolean enterAction = true;
        Presenter.printContinueServicePrompt();
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true")){
            enterAction = false;
        }
        return enterAction;
    }

}
