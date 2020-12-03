package UI;

import Controllers.MessageAllAttendeeController;
import Controllers.UserController;
import menuPresenter.OrganizerPresenter;

import java.util.Scanner;

public class MessageAllAttendeeUI extends AbstractUI {
    public MessageAllAttendeeUI(UserController userController) {
        super(userController);
    }

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
