package UI;

import Controllers.UserController;
import functionalityPresenters.AvailableSchedulePresenter;

public class ViewAvailableScheduleUI extends AbstractUI {
    private final AvailableSchedulePresenter availableSchedulePresenter;

    public ViewAvailableScheduleUI(UserController userController) {
        super(userController);
        availableSchedulePresenter = new AvailableSchedulePresenter(userController);
    }

    @Override
    public void run() {
        if (availableSchedulePresenter.viewAvailableSchedules().isEmpty()){
            System.out.println(userPresenter.strNoEventsAvailable());
        }else {
            System.out.println(userPresenter.strSchedule(availableSchedulePresenter.viewAvailableSchedules()));
        }
    }
}
