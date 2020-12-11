package UserUI;

import Controllers.UserController;
import UI.AdminCancelEventUI;
import UI.AdminDeleteConversationMessageUI;
import UI.AdminDeleteMessageUI;
import menuPresenter.AdminPresenter;

import java.util.Scanner;

/**
 * Represents a <code>AdministratorUI</code> extends from <code>OrganizerUI</code>.
 * Is specific for <code>Administrator</code> type usage.
 *
 * Includes:
 * Own presenter
 * Own constructor
 * run: the method for receiving user's inputs for actions and call corresponding method.
 * sendPrivateMessage(): responsible for sending private messages to a particular user.
 * viewPrivateMessage(): responsible for gathering all private messages sent to a particular user
 * and print them out.
 * sendCoopMessage(): responsible for sending message to all organizers and speakers in a
 *   particular Organizer-Speaker Chatroom.
 * viewCoopChat(): responsible for viewing messages from a chatroom with only organizers and speakers.
 * deleteMessage(): responsible for deleting messages for a particular user.
 * deleteEvent(): responosible for cancellinga particular event.
 */
public class AdministratorUI extends OrganizerUI {

    private final AdminPresenter presenter = new AdminPresenter();

    public AdministratorUI(UserController userController) {
        super(userController);
    }

    @Override
    /*
      This method allows users to do actions corresponding to administrator's allowed actions.
      Will print out a list of actions the user can implement, ask for choice of action the user
      want to do and call corresponding method.
     */

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
