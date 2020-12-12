package UI;

import Controllers.EnrollEventController;
import Controllers.UserController;
import functionalityPresenters.AvailableSchedulePresenter;
import globallyAccessible.EventNotFoundException;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.AttendeePresenter;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * UI for <code>Attendee</code> to enroll selected events.
 */
public class AttEnrollEventUI extends AbstractUI {
    /**
     * An instance of <code>AttendeePresenter</code> being instantiated.
     */
    private AttendeePresenter attendeePresenter = new AttendeePresenter();
    /**
     * An instance of <code>AvailableSchedulePresenter</code> being instantiated which
     * helps to prints the schedule.
     */

    private AvailableSchedulePresenter schedulePresenter;
    /**
     * An instance of <code>EnrollEventController </code> being instantiated.
     */
    private EnrollEventController enroll;

    /**
     * Instantiates new <code> AttEnrollEventUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public AttEnrollEventUI(UserController userController) {
        super(userController);
        schedulePresenter = new AvailableSchedulePresenter(userController);
        enroll = new EnrollEventController(userController);
    }

    /**
     * Does the all action needed to enroll in certain event. Runs the method in this UI.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        ArrayList<String[]> availables = schedulePresenter.viewAvailableSchedules();
        if (availables.size() == 0){
            return;
        }
        inputAndEnrollEvent(availables);
    }


    private void inputAndEnrollEvent(ArrayList<String[]> availables) throws ExceedingMaxAttemptException {
        for (int i = 0; i < 3; i++){
            try{
                String actID = getAvailableEventID(availables);
                enroll.enrollEvent(availables, actID);
                return;
            }catch(EventNotFoundException e){
                System.out.println(attendeePresenter.strInvalidEventID());
            }
        }
        throw new ExceedingMaxAttemptException("Maximum number of attempts exceeded");
    }


    private String getAvailableEventID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrollMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strEnrollPrompt());
        return scan.nextLine();
    }
}
