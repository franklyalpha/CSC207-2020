package UI;

import Controllers.UserController;
import functionalityPresenters.EnrolledSchedulePresenter;

import java.util.ArrayList;

public class ViewEnrolledScheduleUI extends AbstractUI {
    private EnrolledSchedulePresenter enrolledSchedulePresenter;

    public ViewEnrolledScheduleUI(UserController userController) {
        super(userController);
        enrolledSchedulePresenter = new EnrolledSchedulePresenter(userController);
    }

    @Override
    public void run() {
        ArrayList<String[]> schedules = enrolledSchedulePresenter.viewEnrolledSchedule();
        System.out.println(userPresenter.strSchedule(schedules));
    }
}
