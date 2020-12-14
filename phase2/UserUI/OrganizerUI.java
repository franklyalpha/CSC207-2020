package UserUI;

import Controllers.UserController;
import UI.*;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.OrganizerPresenter;

import java.util.Scanner;


/**
 * Represents a <code>OrganizerUI</code> extends from <code>UserUI</code>.
 * Is specific for <code>Organizer</code> type usage.
 *
 * Includes:
 * Own presenter
 * Own constructor
 * run: the method for receiving user's inputs for actions and call corresponding method.
 * createRoom(): responsible for creating a new event room.
 * createUser(): responsible for creating a new user account.
 * addSchedule(): responsible for creating a new event.
 * rescheduleSpeaker(): responsible for replacing the speaker with another for a given event.
 * sendPrivateMessage(): responsible for sending private message to a user.
 * viewPrivateMessage(): responsible for gathering all available private messages sent to a user
 *  and print out.
 * sendCoopMessage(): responsible for sending message to all other organizers and speakers in a \
 * particular Organizer-Speaker Chatroom.
 * viewCoopChat(): responsible for gathering all messages from the chatroom with only organizers and
 *  * speakers and print them out.
 * messageAllAttendee(): responsible for sending message to all registered attendees at a given event.
 * modifyEvent(): responsible for modifying information for a given event.
 * removeEvent(): responsible for cancelling a particular event
 * manageRequests(): responsible for managing all requests from a particular user.
 */
public class OrganizerUI extends UserUI {
    final protected OrganizerPresenter organizerPresenter = new OrganizerPresenter();

    public OrganizerUI(UserController userController) {
        super(userController);
    }

    /**
     * This method allows users to do actions corresponding to the organizer's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
     */
    public void run() {
        addMenu();
        int action;
        // replace with try-catch in while loop
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            System.out.println(organizerPresenter.strAvailableActions(availableAction));
            action = scan.nextInt();
            if (0 < action && action <= availableAction.size()) {
                runMethod(action);
            }
            else{
                System.out.println(organizerPresenter.strInvalidInput());
            }
            enterAction = continuing();
        }
        userController.logout();
    }

    /**
     * This method allows users to do actions corresponding to organizer's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
     */

    private void runMethod (int action){
        try{
            switch (action) {
                case 1: createRoom(); break;
                case 2: createUser(); break;
                case 3: addSchedule();break;
                case 4: rescheduleSpeaker();break;
                case 5: sendPrivateMessage(); break;
                case 6: viewPrivateMessage(); break;
                case 7: sendCoopMessage(); break;
                case 8: viewCoopChat(); break;
                case 9: messageAllAttendee(); break;
                case 10: modifyEvent(); break;
                case 11: removeEvent(); break;
                case 12: manageRequests(); break;
            }
        }
        catch (ExceedingMaxAttemptException e){
            e.printStackTrace();
        }
    }

    protected void sendCoopMessage(){
        new SendManagersMessageUI(userController).run();
    }

    protected void viewCoopChat(){
        new ViewManagersMessagesUI(userController).run();
    }

    protected void messageAllAttendee(){
        new OrganizerMessageAllAttendeeUI(userController).run();
    }

    private void modifyEvent() {
        new OrganizerModifyEventUI(userController).run();
    }

    private void removeEvent() throws ExceedingMaxAttemptException {
        new OrganizerRemoveEventUI(userController).run();
    }

    void addMenu(){
        availableAction.add("- Create new event room");
        availableAction.add("- Create new user account");
        availableAction.add("- Create an event");
        availableAction.add("- Reschedule speaker");
        availableAction.add("- Send private message");
        availableAction.add("- View private messages");
        availableAction.add("- Message the Organizer-Speaker MessageRoom");
        availableAction.add("- View messages in the Organizer-Speaker MessageRoom");
        availableAction.add("- Message all attendees");
        availableAction.add("- Modify event information");
        availableAction.add("- Cancel event");
        availableAction.add("- Manage requests");
    }
    //TODO should move to presenter;

    protected void createRoom() throws ExceedingMaxAttemptException {
        new OrganizerCreateRoomUI(userController).run();
    }

    protected void createUser() throws ExceedingMaxAttemptException {
        new OrganizerCreateUserUI(userController).run();
    }

    protected void addSchedule() throws ExceedingMaxAttemptException {
        new OrganizerAddScheduleUI(userController).run();
    }

    protected void rescheduleSpeaker() throws ExceedingMaxAttemptException {
        new OrganizerRescheduleSpeakerUI(userController).run();
    }

    protected void manageRequests() { new OrganizerRequestUI(userController).run(); }
}
