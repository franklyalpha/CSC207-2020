package UI;

import Controllers.QuitEventController;
import Controllers.UserController;
import functionalityPresenters.EnrolledSchedulePresenter;
import globallyAccessible.EventNotFoundException;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.AttendeePresenter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * UI for <code>Attendee</code> to cancel event currently enroll.
 */
public class AttCancelEnrollmentUI extends AbstractUI{
    /**
     * An instance of <code>AttendeePresenter</code> being instantiated.
     */

    private AttendeePresenter attendeePresenter = new AttendeePresenter();
    /**
     * An instance of <code>AvailableSchedulePresenter</code> being instantiated which
     * helps to prints the schedule.
     */
    private EnrolledSchedulePresenter enrolledPresenter;

    /**
     * An instance of <code>QuitEventController</code> being instantiated.
     */
    private QuitEventController quit;

    /**
     * Instantiates new <code>AttCancelEnrollmentUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public AttCancelEnrollmentUI(UserController userController) {
        super(userController);
        enrolledPresenter = new EnrolledSchedulePresenter(userController);
        quit = new QuitEventController(userController);
    }

    /**
     * Does the all action needed to cancel certain event.Runs the method in this UI.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        ArrayList<String[]> enrolled = enrolledPresenter.viewEnrolledSchedule();
        if (enrolled.size() == 0){
            return;
        }
        inputAndQuitEvent(enrolled);
    }


    private void inputAndQuitEvent(ArrayList<String[]> enrolled) throws ExceedingMaxAttemptException {
        for(int i=0; i<3; i++){
            try{
                String activityID = getEnrolledEventID(enrolled);
                quit.chooseActToCancel(enrolled, activityID);
                return;
            }catch(EventNotFoundException e){
                System.out.println(attendeePresenter.strInvalidEventID());
            }
        }
        throw new ExceedingMaxAttemptException("Maximum number of attempts exceeded");
    }


    private String getEnrolledEventID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrolledMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strCancelPrompt());
        return scan.nextLine();
    }
}
