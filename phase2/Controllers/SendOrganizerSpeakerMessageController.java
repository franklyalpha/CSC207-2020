package Controllers;

import java.util.UUID;

public class SendOrganizerSpeakerMessageController extends MessagingController {


    public SendOrganizerSpeakerMessageController(UserController userController) {
        super(userController);
    }

    public void sendCoopMessage(String message) {
        UUID coopChatID = messageRoomManager.getCoopId();
        messageRoomManager.sendMessage(message, coopChatID);
    }
}
