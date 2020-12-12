package UI;

import Controllers.UserController;
import functionalityPresenters.AvailableSchedulePresenter;

/**
 * UI for presenting available schedule.
 */
public class ViewAvailableScheduleUI extends AbstractUI {
    /**
     * An instance of <code>AvailableSchedulePresenter</code> being instantiated.
     */
    private final AvailableSchedulePresenter availableSchedulePresenter;

    /**
     * Instantiates new <code>ViewAvailableScheduleUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public ViewAvailableScheduleUI(UserController userController) {
        super(userController);
        availableSchedulePresenter = new AvailableSchedulePresenter(userController);
    }

    /**
     * Shows the available schedules on the screen.
     */
    @Override
    public void run() {
        if (availableSchedulePresenter.viewAvailableSchedules().isEmpty()){
            System.out.println(userPresenter.strNoEventsAvailable());
        }else {
            System.out.println(userPresenter.strSchedule(availableSchedulePresenter.viewAvailableSchedules()));
        }
    }
}
