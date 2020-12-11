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
 * sendPrivateMessage(): responsible for sending private messages to a particular user.
 * viewPrivateMessage(): responsible for gathering all private messages sent to a particular user
 * and print them out.
 * sendCoopMessage(): responsible for sending message to all organizers and speakers in a
 *   particular Organizer-Speaker Chatroom.
 * viewCoopChat(): responsible for viewing messages from a chatroom with only organizers and speakers.
 * deleteMessage(): responsible for deleting messages for a particular user.
 * deleteEvent():
 * createRoom(): responsible for creating a new event room.
 * createUser(): responsible for creating a new user account.
 * addSchedule(): responsible for creating a new event.
 * rescheduleSpeaker(): responsible for replacing the speaker with another for a given event.
 * endPrivateMessage(): send private message to a user.
 * viewPrivateMessage(): responsible for gathering all available private messages sent to a user
 *  and print out.
 * sendCoopMessage(): responsible for sending message to all other organizers and speakers in a \
 * particular Organizer-Speaker Chatroom.
 * viewCoopChat(): responsible for viewing messages from the chatroom with only organizers and speakers;
 * messageAllAttendee(): responsible for sending message to all registered attendees at a given event.
 * modifyEvent(): responsible for modifying information for a given event.
 * removeEvent(): responsible for cancelling a particular event
 * manageRequests(): responsible for managing all requests from a particular user.
 */
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
