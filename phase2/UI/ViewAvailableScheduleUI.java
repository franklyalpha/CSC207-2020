package UI;

import Controllers.UserController;
import functionalityPresenters.AvailableSchedulePresenter;

public class ViewAvailableScheduleUI extends AbstractUI {
    private AvailableSchedulePresenter availableSchedulePresenter;

    public ViewAvailableScheduleUI(UserController userController) {
        super(userController);
        availableSchedulePresenter = new AvailableSchedulePresenter(userController);
    }

    @Override
    public void run() {
        System.out.println(userPresenter.strSchedule(availableSchedulePresenter.viewAvailableSchedules()));
    }
}
