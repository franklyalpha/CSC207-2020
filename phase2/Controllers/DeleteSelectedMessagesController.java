package Controllers;

import java.util.ArrayList;
import java.util.UUID;

public class DeleteSelectedMessagesController extends AbstractController {
    private UUID targetedChat;

    public DeleteSelectedMessagesController(UserController userController) {
        super(userController);
    }

    public ArrayList<String> getOrganizerSpeakerMessage(){
        UUID organizerSpeaker = messageRoomManager.getCoopId();
        targetedChat = organizerSpeaker;
        return messageRoomManager.getHistoricalChats(organizerSpeaker);
    }

    public void deleteMessage(ArrayList<Integer> indexesDeletion){
        messageRoomManager.deleteMessage(targetedChat, indexesDeletion);
    }

    public ArrayList<String[]> groupChatIDs(){
        ArrayList<String[]> actInfo = activityManager.viewUpcommingActivites();
        ArrayList<String[]> groupConversationIDs = new ArrayList<>();
        for (String[] info: actInfo){
            groupConversationIDs.add(new String[]{info[1], info[4]});
        }
        return groupConversationIDs;
    }

    public ArrayList<String> getGroupHistoryChat(String chatID){
        targetedChat = UUID.fromString(chatID);
        return messageRoomManager.getHistoricalChats(UUID.fromString(chatID));
    }
}
