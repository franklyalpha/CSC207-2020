package UI;

import Controllers.DeleteSelectedMessagesController;
import Controllers.UserController;
import menuPresenter.AdminDeleteMessagePresenter;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * UI for <code>Administrator</code> to delete message.
 */

public class AdminDeleteMessageUI extends AdminDeleteConversationMessageUI{
    /**
     * @param adminDeleteMessagePresenter : an instance of <code>AdminDeleteMessagePresenter</code> being instantiated.
     */

    final private AdminDeleteMessagePresenter adminDeleteMessagePresenter = new AdminDeleteMessagePresenter();

    /**
     * Instantiates new <code> AdminDeleteMessageUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public AdminDeleteMessageUI(UserController userController) {
        super(userController);
    }

    /**
     * Does the action delete message. Run the method in this UI.
     *Gives instructions for invalid inputs.
     */
    @Override
    public void run() {
        System.out.println(adminDeleteMessagePresenter.strGroupActionMenu());
        while(true){
            Scanner choice = new Scanner(System.in);
            if(choice.hasNextInt()) {
                deleteChoice(choice);
                break;
            }
            System.out.println(adminDeleteMessagePresenter.strInvalidInput());
        }
    }

    /**
     * Lets <code>Administrator</code> choose whether they want to delete organizer speaker message or event message.
     */
    private void deleteChoice(Scanner choice) {
        switch(choice.nextInt()){
            case 0: deleteOrganizerSpeakerMessage(); break;
            case 1: deleteEventMessage(); break;
        }
    }

    /**
     * Deletes organizer speaker message which <code>Administrator</code> selected.
     */
    private void deleteOrganizerSpeakerMessage(){
        DeleteSelectedMessagesController deleteMessage = new DeleteSelectedMessagesController(userController);
        ArrayList<String> history = deleteMessage.getOrganizerSpeakerMessage();
        if(history.size() != 0){
            chooseLinesToDelete(deleteMessage, history);
        }
    }

    /**
     * Choose the line of message<code>Administrator</code> wants to selected.
     *  @param deleteMessage An controller that response for delete message.
     *  @param history A list of String that showing the history messages.
     */
    private void chooseLinesToDelete(DeleteSelectedMessagesController deleteMessage, ArrayList<String> history) {
        System.out.println(adminDeleteMessagePresenter.strDisplayMessageHistory(history));
        Scanner lines = new Scanner(System.in);
        System.out.println(adminDeleteMessagePresenter.strIndexToDeletePrompt());
        ArrayList<Integer> targetedDeletion = new ArrayList<>();
        while(lines.hasNextInt()){
            targetedDeletion.add(lines.nextInt());
        }
        deleteMessage.deleteMessage(targetedDeletion);
    }
    /**
     * Deletes event message which <code>Administrator</code> selected.
     */
    private void deleteEventMessage(){
        DeleteSelectedMessagesController deleteMessage = new DeleteSelectedMessagesController(userController);
        ArrayList<String> ids = viewAllGroupConversation(deleteMessage);
        if(ids.size() != 0){
            ArrayList<String> history = selectGroupConversation(deleteMessage, ids);
            chooseLinesToDelete(deleteMessage, history);
        }
    }

    /**
     * Lets <code>Administrator</code>  select the group conversation wants to return the history message of this
     * conversation.
     *  @param deleteMessage An controller that response for delete message.
     *  @param ids A list of String that contains the IDS of all groups conversation.
     *  @return A list of string that is the history message of this conversation.
     */
    private ArrayList<String> selectGroupConversation(DeleteSelectedMessagesController deleteMessage, ArrayList<String> ids){
        System.out.println(adminDeleteMessagePresenter.strChatToDeletePrompt());
        while(true){
            Scanner conversationID = new Scanner(System.in);
            String ID = conversationID.nextLine();
            if (ids.contains(ID)){
                return deleteMessage.getGroupHistoryChat(ID);
            }
            System.out.println(adminDeleteMessagePresenter.strInvalidInput());
        }
    }


    /**
     * Shows the IDs of all group conversation.
     * @param deleteMessage An controller that response for delete message.
     * @return A list of String that are the IDs of all group conversation.
     */
    private ArrayList<String> viewAllGroupConversation(DeleteSelectedMessagesController deleteMessage) {
        ArrayList<String[]> groupConversationId = deleteMessage.groupChatIDs();
        System.out.println(adminDeleteMessagePresenter.strChatDisplayHeader());
        ArrayList<String> ids = new ArrayList<>();
        System.out.println(adminDeleteMessagePresenter.strDisplayChatGroups(groupConversationId));
        for(String[] info: groupConversationId){
            ids.add(info[1]);
        }
        return ids;
    }
}
