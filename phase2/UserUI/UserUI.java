package UserUI;

import Controllers.UserController;
import UI.*;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.UserPresenter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a <code>UserUI</code>.
 * Is specific for <code>Organizer</code> type usage.
 *
 * Includes:
 * Own presenter
 * Own constructor
 * run: the method for receiving user's inputs for actions and call corresponding method.
 * viewPrivateMessage(): responsible for gathering all available private messages sent to the user
 *  and print out.
 * sendPrivateMessage(): responsible for sending private message to a particular user.
 * viewGroupMessage(): responsible for gathering all message within a chatroom and print them out.
 * viewEnrolledSchedule: responsible for gathering all events that the user has been enrolled in
 *  and print out.
 * viewAvailableSchedules(): responsible for gathering all available events that the user can enroll in
 *   and print them out.
 *outputAllUpcomingEventsPdf(): responsible for generating a PDF of all upcoming events
 *
 */
public class UserUI {
    protected ArrayList<String> availableAction = new ArrayList<>();
    protected ArrayList<String> availableMethod = new ArrayList<>();
    protected UserController userController;
    final private UserPresenter userPresenter = new UserPresenter();


    public UserUI(UserController userController){
        this.userController = userController;
    }

    /**
     * This method allows users to do actions corresponding to the user's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
     */
    public void run(){}

    protected void viewPrivateMessage(){
        new ViewPrivateMessageUI(userController).run();
    }

    protected void sendPrivateMessage(){
        new SendPrivateMessageUI(userController).run();
    }

    protected void viewGroupMessage(){
        new ViewGroupMessageUI(userController).run();
    }

    protected void viewEnrolledSchedule(){
        new ViewEnrolledScheduleUI(userController).run();
    }

    protected void viewAvailableSchedules(){
        new ViewAvailableScheduleUI(userController).run();
    }

    protected void outputAllUpcomingEventsPdf() {
        new OutputUpcomingEventsPDFUI(userController).run();
    }

    protected void viewStatistic(){new StatisticUI(userController).run();}

    protected boolean continuing(){
        boolean enterAction = false;
        System.out.println(userPresenter.strContinueServicePrompt());
        Scanner scan2 = new Scanner(System.in);
        String choice = scan2.nextLine();
        if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")){
            enterAction = true;
        }
        return enterAction;
    }
}
