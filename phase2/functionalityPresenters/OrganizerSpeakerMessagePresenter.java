package functionalityPresenters;

import Controllers.UserController;

import java.util.ArrayList;
import java.util.UUID;

/**
 * OrganizerSpeakerMessagePresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class OrganizerSpeakerMessagePresenter extends AbstractPresenter {

    /**
     * Creates a OrganizerSpeakerMessagePresenter
     * @param userController contains all the managers
     */
    public OrganizerSpeakerMessagePresenter(UserController userController) {
        super(userController);
    }

    /**
     * return the messages in the coop chat
     * @return an ArrayList of message Strings
     */
    public ArrayList<String> viewCoopChat() {
        UUID coopChatID = messageRoomManager.getCoopId();
        return messageRoomManager.getHistoricalChats(coopChatID);
    }
}
