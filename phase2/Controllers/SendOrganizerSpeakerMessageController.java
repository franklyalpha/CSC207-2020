package Controllers;

import java.util.UUID;

/**
 * responsible for sending messages between organizers and speakers.
 */
public class SendOrganizerSpeakerMessageController extends MessagingController {


    /**
     * the constructor of this controller.
     * @param userController An instance of <>UserController</>.
     */
    public SendOrganizerSpeakerMessageController(UserController userController) {
        super(userController);
    }

    /**
     * responsible for sending a message towards <>MessageRoom</> involving only <>Organizers</> and <>Speakers</>.
     * @param message a <>String</> representing the message about to be sent.
     */
    public void sendCoopMessage(String message) {
        UUID coopChatID = messageRoomManager.getCoopId();
        messageRoomManager.sendMessage(message, coopChatID);
    }
}
