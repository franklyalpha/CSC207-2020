package Facades;

import Controllers.UserController;
import UI.AttCancelEnrollmentUI;
import UI.AttEnrollEventUI;
import com.mongodb.client.MongoDatabase;
import UI.UserRequestUI;
import menuPresenter.AttendeePresenter;

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
public class AttendeeFacade extends UserFacade {

    final private AttendeePresenter attendeePresenter = new AttendeePresenter();
//    private MongoDatabase database;

//    public AttendeeFacade(UserController userController, MongoDatabase database) {
    public AttendeeFacade(UserController userController) {
        super(userController);
//        this.database = database;
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
//        userController.logout(database);
        userController.logout();
    }

    private void runMethod(int action) {
        //case 10:
        switch (action) {
            case 1: viewEnrolledSchedule(); break;
            case 2: viewAvailableSchedules(); break;
            case 3: viewStatistic(); break;
            case 4: enrollEvent(); break;
            case 5: cancelEnrollment(); break;
            case 6: sendPrivateMessage(); break;
            case 7: viewPrivateMessage(); break;
            case 8: viewGroupMessage(); break;
            case 9: manageRequests(); break;
            case 10: outputAllUpcomingEventsPdf(); break;
        }
    }


    private void addMenu(){
        availableAction.add("- View your events");
        availableAction.add("- View available events");
        availableAction.add("- View top five popular events");
        availableAction.add("- Sign up for an event");
        availableAction.add("- Cancel event registration");
        availableAction.add("- Send private message");
        availableAction.add("- View private messages");
        availableAction.add("- View group messages");
        availableAction.add("- Manage your requests");
        availableAction.add("- Generate a PDF of all upcoming events");

    }
    //TODO move this to presenter

    protected void enrollEvent(){
        new AttEnrollEventUI(userController).run();
    }

    protected void cancelEnrollment(){
        new AttCancelEnrollmentUI(userController).run();
    }

    protected void manageRequests(){ new UserRequestUI(userController).run(); }




    //check whether the room is full, and whether this user is currently enroll.

    //add a new activity to this user, and add this user to the corresponding conference chat.
    /*
      Provides instructions to the user to enroll in conferences available. Available means having space
      and doesn't conflict with schedule of this user.
      Will print out available conferences the user can enroll, and ask user to input the UUID of
      conference the user wish to enroll.
     */


    /**
     * Provides instructions for user to cancel conferences this user enrolled.
     * Will print all conferences the user enrolled, and ask user to input the UUID of conference the user
     * wish to cancel.
     */


    private boolean continuing(){
        boolean enterAction = false;
        System.out.println(attendeePresenter.strContinueServicePrompt());
        Scanner scan2 = new Scanner(System.in);
        String input = scan2.nextLine();
        if(input.equals("YES") || input.equals("yes") || input.equals("y") || input.equals("Yes")){
            enterAction = true;
        }
        return enterAction;
    }

}
