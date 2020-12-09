package Facades;

import Controllers.UserController;
import UI.AdminCancelEventUI;
import UI.AdminDeleteMessageUI;
import menuPresenter.AdminPresenter;

import java.util.Scanner;

public class AdministratorFacade extends OrganizerFacade {

    private final AdminPresenter presenter = new AdminPresenter();

    public AdministratorFacade(UserController userController) {
        super(userController);
    }

    @Override
    public void run() {
        availableAction = presenter.optionMenu();
        int action;
        // replace with try-catch in while loop
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            System.out.println(presenter.strAvailableActions(availableAction));
            action = scan.nextInt();
            if (0 < action && action <= availableAction.size()) {
                runMethod(action);
            }
            else{
                System.out.println(presenter.strInvalidInput());
            }
            enterAction = continuing();
        }
        userController.logout();
    }

    private void runMethod (int action){
        switch (action) {
            case 1 : sendPrivateMessage();
            case 2 : viewPrivateMessage();
            case 3 : sendCoopMessage();
            case 4 : viewCoopChat();
            case 5 : deleteMessage();
            case 6 : deleteEvent();
        }
    }

    private void deleteMessage(){
        new AdminDeleteMessageUI(userController).run();
    }

    private void deleteEvent(){
        new AdminCancelEventUI(userController).run();
    }

    boolean continuing(){
        boolean enterAction = true;
        System.out.println(presenter.strContinueServicePrompt());
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true") && !scan2.nextLine().equals("True") && !scan2.nextLine().equals("t") &&
                !scan2.nextLine().equals("T")){
            enterAction = false;
        }
        return enterAction;
    }
}
