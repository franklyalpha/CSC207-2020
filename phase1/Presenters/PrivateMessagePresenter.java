package Presenters;

import Controllers.UserController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PrivateMessagePresenter extends AbstractPresenter {
    public PrivateMessagePresenter(UserController managers) {
        super(managers);
    }

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
