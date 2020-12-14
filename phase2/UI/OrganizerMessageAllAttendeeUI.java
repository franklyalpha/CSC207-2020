package UI;

import Controllers.MessageAllAttendeeController;
import Controllers.UserController;
import menuPresenter.OrganizerPresenter;

import java.util.Scanner;
/**
 * UI for <code>Organizer</code> to message all <code>Attendee</code>.
 */

public class OrganizerMessageAllAttendeeUI extends AbstractUI {
    /**
     * Instantiates new <code>OrganizerMessageAllAttendeeUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OrganizerMessageAllAttendeeUI(UserController userController) {
        super(userController);
    }


    /**
     * Lets <code>Organizer</code> message all <code>Attendee</code> with the message they input.
     */
    @Override
    public void run() {
        OrganizerPresenter organizerPresenter = new OrganizerPresenter();
        Scanner messageScanner = new Scanner(System.in);
        System.out.println(organizerPresenter.strMessagePrompt());
        String message = messageScanner.nextLine();
        MessageAllAttendeeController messageAll = new MessageAllAttendeeController(userController);
        messageAll.messageAllAttendee(message);
    }
}
