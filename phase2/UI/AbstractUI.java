package UI;

import Controllers.SendPrivateMessageController;
import Controllers.UserController;
import functionalityPresenters.*;
import Controllers.OutputConferenceInfoController;
import globallyAccessible.UserNotFoundException;
import menuPresenter.UserPresenter;

import java.io.IOException;
import java.util.*;

//public abstract class controllers.UserController

/**
 * Controller for <code>User</code>-related functions, calling the appropriate methods.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class AbstractUI {

    protected ArrayList<String> availableAction = new ArrayList<>();
    protected ArrayList<String> availableMethod = new ArrayList<>();
    protected UserController userController;
    final private UserPresenter userPresenter = new UserPresenter();


    public AbstractUI(UserController userController){
        this.userController = userController;
    }

    /**
     * Abstract run method to be overridden.
     */
    public void run() {}

    protected void viewPrivateMessage(){
        PrivateMessagePresenter messagePresenter = new PrivateMessagePresenter(userController);
        HashMap<String, ArrayList<String>> historyChat = messagePresenter.viewPrivateMessage();

        Presenter.printContactPrompt("contact"); //TODO Really should change this to an index system
        System.out.println(userPresenter.strList(historyChat.keySet().toArray()));
        Scanner scan = new Scanner(System.in);
        String contactUser = scan.nextLine();
        if (!historyChat.containsKey(contactUser)){
            System.out.println(userPresenter.strInvalidUsername());
        }
        System.out.println(userPresenter.strMessagesInInterval(historyChat.get(contactUser), 1, historyChat.get(contactUser).size()));
    }

    protected void sendPrivateMessage(){
        SendPrivateMessageController sendPrivateController = new SendPrivateMessageController(userController);
        while(true){
            try{
                Scanner userScanner = new Scanner(System.in);
                System.out.println(userPresenter.strUserToContactPrompt());
                String userName = userScanner.nextLine();
                Scanner messageScan = new Scanner(System.in);
                System.out.println(userPresenter.strMessagePrompt());
                String message = messageScan.nextLine();
                sendPrivateController.send(userName, message);
                break;
            }
            catch(UserNotFoundException e){
                System.out.println(userPresenter.strInvalidInput());
            }
        }
    }

    protected void viewGroupMessage(){
        GroupMessagePresenter groupMessagePresenter = new GroupMessagePresenter(userController);
        HashMap<String, ArrayList<String>> historyChat = groupMessagePresenter.viewGroupMessage();

        Presenter.printContactPrompt("event"); //TODO Really should change this to select an index
        System.out.println(userPresenter.strList(historyChat.keySet().toArray()));
        Scanner scan = new Scanner(System.in);
        String selectedEvent = scan.nextLine();
        if (!historyChat.containsKey(selectedEvent)){
            System.out.println(userPresenter.strInvalidActivityID());
            return;
        }
        System.out.println(userPresenter.strMessagesInInterval(historyChat.get(selectedEvent), 1,
                historyChat.get(selectedEvent).size()));
    }

    protected void viewEnrolledSchedule(){
        EnrolledSchedulePresenter enrolledSchedulePresenter = new EnrolledSchedulePresenter(userController);
        ArrayList<String[]> schedules = enrolledSchedulePresenter.viewEnrolledSchedule();
        System.out.println(userPresenter.strSchedule(schedules));
    }

    protected void viewAvailableSchedules(){
        AvailableSchedulePresenter availableSchedulePresenter = new AvailableSchedulePresenter(userController);
        System.out.println(userPresenter.strSchedule(availableSchedulePresenter.viewAvailableSchedules()));
    }

    protected void outputAllUpcomingEventsPdf() {
        OutputConferenceInfoController pdf = new OutputConferenceInfoController(userController);
        try{
            pdf.outputAllUpcomingEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
