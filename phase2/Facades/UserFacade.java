package Facades;

import Controllers.UserController;
import UI.*;
import menuPresenter.UserPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class UserFacade {
    protected ArrayList<String> availableAction = new ArrayList<>();
    protected ArrayList<String> availableMethod = new ArrayList<>();
    protected UserController userController;
    final private UserPresenter userPresenter = new UserPresenter();


    public UserFacade(UserController userController){
        this.userController = userController;
    }

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
        boolean enterAction = true;
        System.out.println(userPresenter.strContinueServicePrompt());
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equalsIgnoreCase("true")){
            enterAction = false;
        }
        return enterAction;
    }
}
