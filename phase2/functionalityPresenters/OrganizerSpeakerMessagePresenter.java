package functionalityPresenters;

import Controllers.UserController;

import java.util.ArrayList;
import java.util.UUID;

public class OrganizerSpeakerMessagePresenter extends AbstractPresenter {


    public OrganizerSpeakerMessagePresenter(UserController userController) {
        super(userController);
    }

    public ArrayList<String> viewCoopChat() {
        UUID coopChatID = messageRoomManager.getCoopId();
        ArrayList<String> message = messageRoomManager.getHistoricalChats(coopChatID);
        return message;
    }
}
