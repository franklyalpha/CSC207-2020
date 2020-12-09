package Facades;

import Controllers.UserController;
import UI.AdminCancelEventUI;
import UI.AdminDeleteMessageUI;
import com.mongodb.client.MongoDatabase;
import menuPresenter.AdminPresenter;

import java.util.Scanner;

public class AdministratorFacade extends OrganizerFacade {

    private final AdminPresenter presenter = new AdminPresenter();
    public MongoDatabase database;

    public AdministratorFacade(UserController userController, MongoDatabase database) {
        super(userController, database);
        this.database = database;
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
        userController.logout(database);
    }

    private void runMethod (int action){
        switch (action) {
            case 1 : sendPrivateMessage(); break;
            case 2 : viewPrivateMessage(); break;
            case 3 : sendCoopMessage(); break;
            case 4 : viewCoopChat(); break;
            case 5 : deleteMessage(); break;
            case 6 : deleteEvent(); break;
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
