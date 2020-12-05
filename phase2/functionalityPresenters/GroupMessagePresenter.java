package functionalityPresenters;

import Controllers.UserController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GroupMessagePresenter extends AbstractPresenter {


    public GroupMessagePresenter(UserController userController) {
        super(userController);
    }

    public HashMap<String, ArrayList<String>> viewGroupMessage() {
        // may add particular user for viewing;
        // should call presenter to display; but will acquire data here;
        HashMap<LocalDateTime[], UUID> act = userManager.getActivities();
        if (act.size() == 0){
            return null;
        }
        HashMap<String, ArrayList<String>> historyChat = new HashMap<>();
        for (LocalDateTime[] period : act.keySet()){
            UUID chatID = eventManager.getConferenceChat(act.get(period));
            ArrayList<String> chatMessage = messageRoomManager.getHistoricalChats(chatID);
            String topic = eventManager.searchEventByUUID(act.get(period).toString())[1];
            historyChat.put(topic, chatMessage);
        }
        return historyChat;
    }

    private void groupPrinting(HashMap<String, ArrayList<String>> historyChat){

    }
}
