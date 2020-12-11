package UI;

import Controllers.UserController;
import functionalityPresenters.EnrolledSchedulePresenter;

import java.util.ArrayList;

/**
 * A UI allows Users to view their enrolled events.
 */
public class ViewEnrolledScheduleUI extends AbstractUI {

    /**
     * An instance of <code>EnrolledSchedulePresenter</code>.
     */
    private EnrolledSchedulePresenter enrolledSchedulePresenter;

    /**
     * the constructor for <code>ViewEnrolledScheduleUI</code>.
     * @param userController A instance of <code>UserController</code>.
     */
    public ViewEnrolledScheduleUI(UserController userController) {
        super(userController);
        enrolledSchedulePresenter = new EnrolledSchedulePresenter(userController);
    }


    @Override
    public void run() {
        ArrayList<String[]> schedules = enrolledSchedulePresenter.viewEnrolledSchedule();
        if (schedules.isEmpty()){
            System.out.println("You have not signed up for any events yet.\n ");
        }else{
            System.out.println(userPresenter.strSchedule(schedules));
        }
    }
}
