package UI;

import Controllers.EnrollEventController;
import Controllers.UserController;
import functionalityPresenters.AvailableSchedulePresenter;
import globallyAccessible.EventNotFoundException;
import menuPresenter.AttendeePresenter;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * UI for <code>Attendee</code> to enroll selected events.
 */
public class AttEnrollEventUI extends AbstractUI {
    /**
     * @param AttendeePresenter: an instance of <code>AttendeePresenter</code> being instantiated.
     * @param schedulePresenter;: an instance of <code>AvailableSchedulePresenter</code> being instantiated which
     * helps to prints the schedule.
     * @param enroll;: an instance of <code>EnrollEventController </code> being instantiated.
     */
    private AttendeePresenter attendeePresenter = new AttendeePresenter();
    private AvailableSchedulePresenter schedulePresenter;
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
     */
    @Override
    public void run() {
        ArrayList<String[]> availables = schedulePresenter.viewAvailableSchedules();
        if (availables.size() == 0){
            return;
        }
        inputAndEnrollEvent(availables);
    }

    /**
     * Enrolls the user into their chosen event.
     * @param availables ArrayList of strings of information about all events this user can register for.
     * @throws EventNotFoundException when input event IDs is not found in events user can enroll.
     */
    private void inputAndEnrollEvent(ArrayList<String[]> availables) {
        for (int i = 0; i < 3; i++){
            try{
                String actID = getAvailableEventID(availables);
                enroll.enrollEvent(availables, actID);
                break;
            }catch(EventNotFoundException e){
                System.out.println(attendeePresenter.strInvalidEventID());
            }
        }
    }

    /**
     * Get user input for which event they want to enroll.
     * @param availables ArrayList of strings of information about all events this user can register for.
     * @return An IDs showing as String that is <code>Attendee</code> selected to enroll.
     */
    private String getAvailableEventID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrollMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strEnrollPrompt());
        return scan.nextLine();
    }
}
