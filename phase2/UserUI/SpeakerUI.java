package UserUI;

import Controllers.UserController;
import UI.SpeSendEventMessagesUI;
import menuPresenter.SpeakerPresenter;

import java.util.Scanner;

/**
 * Represents a <code>SpeakerUI</code> extends from <code>OrganizerUI </code>.
 * Is specific for <code>Speaker</code> type usage.
 *
 * Includes:
 * Own presenter
 * Own constructor
 * run: the method for receiving user's inputs for actions and call corresponding method.
 * sendActivityMessage: a method responsible for guiding the user to choose a conference
 * and send message to all attendees enrolled.
 * sendPrivateMessage(): responsible for sending private messages to a user.
 * viewPrivateMessage(): responsible for gathering all the private messages sent to user
 * and print the out.
 * viewGroupMessage(): responsible for gathering message within users in a particular chatroom.
 * sendEventMessage(): responsible for sending message to registered users for a particular event.
 * sendCoopMessage(): responsible for sending message to all other organizers and speakers in the
 *   particular Organizer-Speaker Chatroom.
 * viewCoopChat(): responsible for gathering all messages from the chatroom with only organizers and
 *  speakers and print them out.
 * viewEnrolledSchedule(): responsible for gathering all events that the user has been enrolled in
 *  as a speaker and print out.
 */


public class SpeakerUI extends OrganizerUI {

    final private SpeakerPresenter speakerPresenter = new SpeakerPresenter();

    public SpeakerUI(UserController userController) {
        super(userController);
    }

    /**
     * This method allows users to do actions corresponding to speaker's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
     */

    public void run() {
        addMenu();
        int action;
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            System.out.println(speakerPresenter.strAvailableActions(availableAction));
            action = scan.nextInt();
            if (0 < action && action <= availableAction.size()) {
                runMethod(action);
            }
            else{
                System.out.println(speakerPresenter.strInvalidInput());
            }
            enterAction = continuing();
        }
        userController.logout();
    }

    /**
     * This method takes user's intent(action) to let user choose from given actions, those
     * actions are self-explained.
     * @param action that user can choose from.
     */

    private void runMethod (int action){
        switch (action) {
            case 1: sendPrivateMessage(); break;
            case 2: viewPrivateMessage(); break;
            case 3: viewGroupMessage(); break;
            case 4: sendEventMessage(); break;
            case 5: sendCoopMessage(); break;
            case 6: viewCoopChat(); break;
            case 7: viewEnrolledSchedule(); break;
        }
    }

    /**
     * This method add actions to the class attribute availableAction.
     */

    protected void addMenu(){
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("view group messages");
        availableAction.add("send group messages");
        availableAction.add("send messages in coopChatroom");
        availableAction.add("view messages from coopChatroom");
        availableAction.add("view signed conferences");
    }

    protected void sendEventMessage(){
        new SpeSendEventMessagesUI(userController).run();
    }
}


