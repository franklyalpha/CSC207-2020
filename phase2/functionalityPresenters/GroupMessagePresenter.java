package functionalityPresenters;

import Controllers.UserController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Processes the Group Chat Messages for presentation
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class GroupMessagePresenter extends AbstractPresenter {

    /**
     * Creates a GroupMessagePresenter
     * @param userController the UserController containing the managers
     */
    public GroupMessagePresenter(UserController userController) {
        super(userController);
    }

    /**
     * Returns the chat history of the group chat
     * @return the chat history in a hashmap
     */
    public HashMap<String, ArrayList<String>> viewGroupMessage() {
        // may add particular user for viewing;
        // should call presenter to display; but will acquire data here;
        HashMap<LocalDateTime[], UUID> act = userManager.getEvents();
        if (act.size() == 0){
            return null;
        }
        HashMap<String, ArrayList<String>> historyChat = new HashMap<>();
        for (LocalDateTime[] period : act.keySet()){
            UUID chatID = eventManager.getEventChat(act.get(period));
            ArrayList<String> chatMessage = messageRoomManager.getHistoricalChats(chatID);
            String topic = eventManager.searchEventByUUID(act.get(period).toString())[1];
            historyChat.put(topic, chatMessage);
        }
        return historyChat;
    }
}
