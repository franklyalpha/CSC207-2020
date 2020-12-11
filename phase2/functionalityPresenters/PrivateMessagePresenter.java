package functionalityPresenters;

import Controllers.UserController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * PrivateMessagePresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class PrivateMessagePresenter extends AbstractPresenter {

    /**
     * Creates a PrivateMessagePresenter
     * @param managers contains the manager classes
     */
    public PrivateMessagePresenter(UserController managers) {
        super(managers);
    }

    /**
     * Gets all of the private messages
     * @return the historical chat from all contacts
     */
    public HashMap<String, ArrayList<String>> viewPrivateMessage() {
        HashMap<String, UUID> contact = userManager.contacts();
        if (contact.size() == 0){
            return null;
        }
        HashMap<String, ArrayList<String>> historyChat = new HashMap<>();
        for (String users : contact.keySet()){
            ArrayList<String> chatMessage = messageRoomManager.getHistoricalChats(contact.get(users));
            historyChat.put(users, chatMessage);
        }
        return historyChat;
    }
}
