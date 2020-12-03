package UI;

import Controllers.QuitActivityController;
import Controllers.UserController;
import functionalityPresenters.EnrolledSchedulePresenter;
import globallyAccessible.ActivityNotFoundException;
import menuPresenter.AttendeePresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class AttCancelEnrollmentUI extends AbstractUI{
    private AttendeePresenter attendeePresenter = new AttendeePresenter();
    private EnrolledSchedulePresenter enrolledPresenter;
    private QuitActivityController quit;

    public AttCancelEnrollmentUI(UserController userController) {
        super(userController);
        enrolledPresenter = new EnrolledSchedulePresenter(userController);
        quit = new QuitActivityController(userController);
    }

    @Override
    public void run() {
        ArrayList<String[]> enrolled = enrolledPresenter.viewEnrolledSchedule();
        if (enrolled.size() == 0){
            return;
        }
        inputAndQuitActivity(enrolled);
    }

    private void inputAndQuitActivity(ArrayList<String[]> enrolled) {
        while (true){
            try{
                String activityID = getEnrolledActivityID(enrolled);
                quit.chooseActToCancel(enrolled, activityID);
                break;
            }catch(ActivityNotFoundException e){
                System.out.println(attendeePresenter.strInvalidActivityID());
            }
        }
    }

    private String getEnrolledActivityID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrolledMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strCancelPrompt());
        return scan.nextLine();
    }
}
