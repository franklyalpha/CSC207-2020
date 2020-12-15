package UserUI;

import Controllers.UserController;
import UI.AttCancelEnrollmentUI;
import UI.AttEnrollEventUI;
import UI.UserRequestUI;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.AttendeePresenter;

import java.util.Scanner;


/**
 * Represents a <code>AttendeeUI</code> extends from <code>UserUI</code>.
 * Is specific for <code>Attendee</code> type usage.
 * Includes:
 * Own presenter
 * Own constructor
 * run: the method for receiving user's inputs for actions and call corresponding method.run: the method for receiving user's inputs for actions and call corresponding method.
 * viewEnrolledSchedule: responsible for gathering all events that the user has been enrolled in
 * as an attendee and print out.
 * viewAvailableSchedules(): responsible for gathering all available events that the user can enroll in
 * as an attendee and print out.
 * viewStatistic(): View the top five popular events and print out.
 * enrollEvent(): Sign up the user as an attendee for an event.
 * cancelEnrollment(): Cancel event registration as an attendee for the user.
 * sendPrivateMessage(): responsible for sending private message to a particular user.
 * viewPrivateMessage(): responsible for gathering all available private messages sent to the user
 * and print out.
 * viewGroupMessage(): responsible for gathering all available group messages sent to the user
 * and print out.
 * manageRequests(): responsible for managing requests of the user
 * outputAllUpcomingEventsPdf(): responsible for generating a PDF of all upcoming events.
 * viewSchedules: responsible for gathering all available conferences the user can enroll and print out.
 * enrollConference: responsible for enrolling the user into available conference after checking constraints.
 * cancelEnrollment: responsible for cancelling any conferences user is enrolled.
 */
public class AttendeeUI extends UserUI {

    final private AttendeePresenter attendeePresenter = new AttendeePresenter();
//    private MongoDatabase database;

//    public AttendeeFacade(UserController userController, MongoDatabase database) {
    public AttendeeUI(UserController userController) {
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
        try{
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
        catch (ExceedingMaxAttemptException e){
            System.out.println("Exceeded maximum attempts allowed!!!");
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
        availableAction.add("- View event announcements");
        availableAction.add("- Manage your requests");
        availableAction.add("- Generate a PDF of all upcoming events");
    }
    //TODO move this to presenter

    protected void enrollEvent() throws ExceedingMaxAttemptException {
        new AttEnrollEventUI(userController).run();
    }

    protected void cancelEnrollment() throws ExceedingMaxAttemptException {
        new AttCancelEnrollmentUI(userController).run();
    }

    protected void manageRequests(){ new UserRequestUI(userController).run(); }
}
