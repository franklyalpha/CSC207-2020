package UI;

import Controllers.EnrollActivityController;
import Controllers.UserController;
import functionalityPresenters.AvailableSchedulePresenter;
import globallyAccessible.ActivityNotFoundException;
import menuPresenter.AttendeePresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class AttEnrollEventUI extends AbstractUI {
    private AttendeePresenter attendeePresenter = new AttendeePresenter();
    private AvailableSchedulePresenter schedulePresenter;
    private EnrollActivityController enroll;

    public AttEnrollEventUI(UserController userController) {
        super(userController);
        schedulePresenter = new AvailableSchedulePresenter(userController);
        enroll = new EnrollActivityController(userController);
    }

    @Override
    public void run() {
        ArrayList<String[]> availables = schedulePresenter.viewAvailableSchedules();
        if (availables.size() == 0){
            return;
        }
        inputAndEnrollActivity(availables);
    }

    private void inputAndEnrollActivity(ArrayList<String[]> availables) {
        while(true){
            try{
                String actID = getAvailableActivityID(availables);
                enroll.chooseActToEnroll(availables, actID);
                break;
            }catch(ActivityNotFoundException e){
                System.out.println(attendeePresenter.strInvalidActivityID());
            }
        }
    }

    private String getAvailableActivityID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrollMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strEnrollPrompt());
        return scan.nextLine();
    }
}
