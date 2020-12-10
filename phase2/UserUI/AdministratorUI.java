package UserUI;

import Controllers.UserController;
import UI.AdminCancelEventUI;
import UI.AdminDeleteConversationMessageUI;
import UI.AdminDeleteMessageUI;
import menuPresenter.AdminPresenter;

import java.util.Scanner;

public class AdministratorUI extends OrganizerUI {

    private final AdminPresenter presenter = new AdminPresenter();

    public AdministratorUI(UserController userController) {
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
            case 1 : sendPrivateMessage(); break;
            case 2 : viewPrivateMessage(); break;
            case 3 : sendCoopMessage(); break;
            case 4 : viewCoopChat(); break;
            case 5 : deleteMessage(); break;
            case 6 : deleteEvent(); break;
        }
    }

    private void deleteMessage(){
        new AdminDeleteConversationMessageUI(userController).run();
    }

    private void deleteEvent(){
        new AdminCancelEventUI(userController).run();
    }


}
