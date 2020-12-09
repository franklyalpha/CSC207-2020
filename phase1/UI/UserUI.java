package UI;

import Controllers.SendPrivateMessageController;
import Controllers.UserController;
import Presenters.*;
import gateways.GatewayActivity;
import globallyAccessible.UserNotFoundException;
import useCases.ActivityManager;
import useCases.MessageRoomManager;
import useCases.RoomManager;
import useCases.UserManager;

import java.util.*;

//public abstract class controllers.UserController

/**
 * Controller for <code>User</code>-related functions, calling the appropriate methods.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class UserUI {
    protected ArrayList<String> availableAction = new ArrayList<>();
    protected ArrayList<String> availableMethod = new ArrayList<>();
    protected UserController userController;


    public UserUI(UserController userController){
        this.userController = userController;
    }

    /**
     * Abstract run method to be overridden.
     */
    public void run(){}

    protected void viewPrivateMessage(){
        PrivateMessagePresenter messagePresenter = new PrivateMessagePresenter(userController);
        HashMap<String, ArrayList<String>> historyChat = messagePresenter.viewPrivateMessage();

        Presenter.printContactPrompt("contact");
        Presenter.printList(historyChat.keySet().toArray());
        Scanner scan = new Scanner(System.in);
        String contactUser = scan.nextLine();
        if (!historyChat.containsKey(contactUser)){
            Presenter.printInvalid("user name");
            return;
        }
        Presenter.printMessagesInInterval(historyChat.get(contactUser), 1, historyChat.get(contactUser).size());
        // better put this method in message presenter;
    }

    protected void sendPrivateMessage(){
        SendPrivateMessageController sendPrivateController = new SendPrivateMessageController(userController);
        while(true){
            try{
                Scanner userScanner = new Scanner(System.in);
                Presenter.printUserToContactPrompt();
                String userName = userScanner.nextLine();
                Scanner messageScan = new Scanner(System.in);
                Presenter.printMessagePrompt();
                String message = messageScan.nextLine();
                sendPrivateController.send(userName, message);
                break;
            }
            catch(UserNotFoundException e){
                Presenter.printInvalid("input. That user does not exist!");
            }
        }
    }

    protected void viewGroupMessage(){
        GroupMessagePresenter groupMessagePresenter = new GroupMessagePresenter(userController);
        HashMap<String, ArrayList<String>> historyChat = groupMessagePresenter.viewGroupMessage();

        Presenter.printContactPrompt("event");
        Presenter.printList(historyChat.keySet().toArray());
        Scanner scan = new Scanner(System.in);
        String selectedEvent = scan.nextLine();
        if (!historyChat.containsKey(selectedEvent)){
            Presenter.printInvalid("activity name");
            return;
        }
        Presenter.printMessagesInInterval(historyChat.get(selectedEvent), 1,
                historyChat.get(selectedEvent).size());
    }

    protected void viewEnrolledSchedule(){
        EnrolledSchedulePresenter enrolledSchedulePresenter = new EnrolledSchedulePresenter(userController);
        ArrayList<String[]> schedules = enrolledSchedulePresenter.viewEnrolledSchedule();
        Presenter.printSchedule(schedules);
    }

    protected void viewAvailableSchedules(){
        AvailableSchedulePresenter availableSchedulePresenter = new AvailableSchedulePresenter(userController);
        Presenter.printSchedule(availableSchedulePresenter.viewAvailableSchedules());
    }


}
