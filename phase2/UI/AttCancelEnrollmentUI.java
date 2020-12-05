package UI;

import Controllers.QuitEventController;
import Controllers.UserController;
import functionalityPresenters.EnrolledSchedulePresenter;
import globallyAccessible.EventNotFoundException;
import menuPresenter.AttendeePresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class AttCancelEnrollmentUI extends AbstractUI{
    private AttendeePresenter attendeePresenter = new AttendeePresenter();
    private EnrolledSchedulePresenter enrolledPresenter;
    private QuitEventController quit;

    public AttCancelEnrollmentUI(UserController userController) {
        super(userController);
        enrolledPresenter = new EnrolledSchedulePresenter(userController);
        quit = new QuitEventController(userController);
    }

    @Override
    public void run() {
        ArrayList<String[]> enrolled = enrolledPresenter.viewEnrolledSchedule();
        if (enrolled.size() == 0){
            return;
        }
        inputAndQuitEvent(enrolled);
    }

    private void inputAndQuitEvent(ArrayList<String[]> enrolled) {
        while (true){
            try{
                String activityID = getEnrolledEventID(enrolled);
                quit.chooseActToCancel(enrolled, activityID);
                break;
            }catch(EventNotFoundException e){
                System.out.println(attendeePresenter.strInvalidEventID());
            }
        }
    }

    private String getEnrolledEventID(ArrayList<String[]> availables) {
        Scanner scan = new Scanner(System.in);
        System.out.println(attendeePresenter.strEnrolledMenuDes());
        System.out.println(attendeePresenter.strSchedule(availables));
        System.out.println(attendeePresenter.strCancelPrompt());
        return scan.nextLine();
    }
}
