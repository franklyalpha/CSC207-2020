package UI;

import Controllers.AbstractController;
import Controllers.SendOrganizerSpeakerMessageController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.OrganizerPresenter;

import java.util.Scanner;
/**
 * UI for sending messages.
 */
public class SendManagersMessageUI extends AbstractUI {
    /**
     * An instance of <code>SendOrganizerSpeakerMessageController</code> being instantiated.
     */
    private SendOrganizerSpeakerMessageController orgSpeSendMessage;

    /**
     * Instantiates new <code>SendManagersMessageUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public SendManagersMessageUI(UserController userController) {
        super(userController);
        orgSpeSendMessage = new SendOrganizerSpeakerMessageController(userController);
    }

    /**
     * Gets the message and send it.
     */
    public void run(){
        OrganizerPresenter organizerPresenter = new OrganizerPresenter();
        Scanner messenger = new Scanner(System.in);
        System.out.println(organizerPresenter.strMessagePrompt());
        String message = messenger.nextLine();
        orgSpeSendMessage.sendCoopMessage(message);
    }
}
