package Controllers;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The class responsible for deleting selected messages from a given conversation.
 */
public class DeleteSelectedMessagesController extends AbstractController {

    /**
     * the ID of <>MessageRoom</> in which messages will be deleted.
     */
    private UUID targetedChat;

    /**
     * the constructor of this class.
     * @param userController: an instance of UserController.
     */
    public DeleteSelectedMessagesController(UserController userController) {
        super(userController);
    }

    /**
     * returning history of chat from group with organizers and speakers.
     * @return an <>ArrayList</> representing history chats of given <>MessageRoom</> with ID <>targetedChat</>.
     */
    public ArrayList<String> getOrganizerSpeakerMessage(){
        UUID organizerSpeaker = messageRoomManager.getCoopId();
        targetedChat = organizerSpeaker;
        return messageRoomManager.getHistoricalChats(organizerSpeaker);
    }

    /**
     * Deleting selected lines of messages from <>MessageRoom</> with ID <>targetedChat</>.
     * @param indexesDeletion: an arraylist containing the position of messages required to be deleted.
     */
    public void deleteMessage(ArrayList<Integer> indexesDeletion){
        messageRoomManager.deleteMessage(targetedChat, indexesDeletion);
    }

    /**
     * returning information of all group <>MessageRooms</>.
     * @return an <>ArrayList</> where each element is an array containing
     * topic and corresponding group chat of all events.
     */
    public ArrayList<String[]> groupChatIDs(){
        ArrayList<String[]> actInfo = eventManager.viewUpcomingActivities();
        ArrayList<String[]> groupConversationIDs = new ArrayList<>();
        for (String[] info: actInfo){
            UUID eventID = UUID.fromString(info[0]);
            UUID chatID = eventManager.getEventChat(eventID);
            groupConversationIDs.add(new String[]{info[1], chatID.toString()});
        }
        return groupConversationIDs;
    }

    /**
     * returning the history of given group <>MessageRoom</> with ID <>chatID</>.
     * @param chatID: the ID of <>MessageRoom</> the user wish to view history.
     * @return An <>ArrayList</> containing lines of history chats.
     */
    public ArrayList<String> getGroupHistoryChat(String chatID){
        targetedChat = UUID.fromString(chatID);
        return messageRoomManager.getHistoricalChats(UUID.fromString(chatID));
    }
}
