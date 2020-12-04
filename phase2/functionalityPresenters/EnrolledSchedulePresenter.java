package functionalityPresenters;

import Controllers.UserController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class EnrolledSchedulePresenter extends AbstractPresenter{

    public EnrolledSchedulePresenter(UserController userController) {
        super(userController);
    }

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
