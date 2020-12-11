package functionalityPresenters;

import Controllers.UserController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * This presenter shows a user's enrolled events.
 */
public class EnrolledSchedulePresenter extends AbstractPresenter{

    /**
     * The constructor for this Presenter.
     * @param userController A <code>UserController</code> instance.
     */
    public EnrolledSchedulePresenter(UserController userController) {
        super(userController);
    }

    /**
     * Show all enrolled events in string representation.
     * @return a <>ArrayList</> containing information of all enrolled events.
     */
    public ArrayList<String[]> viewEnrolledSchedule() {
        HashMap<LocalDateTime[], UUID> schedules = userManager.schedules();
        ArrayList<String[]> allSchedule = new ArrayList<>();
        if(schedules.isEmpty()){
            return allSchedule;
        }
        for (LocalDateTime[] time : schedules.keySet()){
            String[] partialInfo = eventManager.searchEventByUUID(schedules.get(time).toString());
            allSchedule.add(partialInfo);
        }
        return allSchedule;
    }
}
