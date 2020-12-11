package UI;

import Controllers.QuitEventController;
import Controllers.UserController;
import functionalityPresenters.EnrolledSchedulePresenter;
import globallyAccessible.EventNotFoundException;
import menuPresenter.AttendeePresenter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * UI for <code>Attendee</code> to cancel event currently enroll.
 */
public class AttCancelEnrollmentUI extends AbstractUI{
    /**
     * @param AttendeePresenter: an instance of <code>AttendeePresenter</code> being instantiated.
     * @param schedulePresenter;: an instance of <code>AvailableSchedulePresenter</code> being instantiated which
     * helps to prints the schedule.
     * @param quit;: an instance of <code>QuitEventController</code> being instantiated.
     */

    private AttendeePresenter attendeePresenter = new AttendeePresenter();
    private EnrolledSchedulePresenter enrolledPresenter;
    private QuitEventController quit;

    /**
     * Instantiates new <code> AttEnrollEventUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public AttCancelEnrollmentUI(UserController userController) {
        super(userController);
        enrolledPresenter = new EnrolledSchedulePresenter(userController);
        quit = new QuitEventController(userController);
    }

    /**
     * Does the all action needed to cancel certain event.Runs the method in this UI.
     */
    @Override
    public void run() {
        ArrayList<String[]> enrolled = enrolledPresenter.viewEnrolledSchedule();
        if (enrolled.size() == 0){
            return;
        }
        inputAndQuitEvent(enrolled);
    }

    /**
     * Cancels the user into their chosen event.
     * @param enrolled ArrayList of strings of information about all events this user currently enrolled.
     * @throws EventNotFoundException when input event IDs is not found in events user can cancel.
     */
    private void inputAndQuitEvent(ArrayList<String[]> enrolled) {
        while (true){
            try{
                String activityID = getEnrolledEventID(enrolled);
                quit.chooseActToCancel(enrolled, activityID);
                break;
            }catch(EventNotFoundException e){
                System.out.println(attendeePresenter.strInvalidEventID());
            }
        }
    }

    /**
     * Get user input for which event they want to cancel.
     * @param availables ArrayList of strings of information about all events this user currently enrolled.
     * @return An IDs showing as String that is <code>Attendee</code> selected to cancel.
     */
    private String getEnrolledEventID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrolledMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strCancelPrompt());
        return scan.nextLine();
    }
}
