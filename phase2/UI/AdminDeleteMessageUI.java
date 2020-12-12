package UI;

import Controllers.DeleteSelectedMessagesController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
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
     * Does the action delete message. Run the method in this UI. And Gives instructions for invalid inputs.
     */
    @Override
    public void run(){
        System.out.println(adminDeleteMessagePresenter.strGroupActionMenu());
        for(int i=0; i<3; i++){
            Scanner choice = new Scanner(System.in);
            if(choice.hasNextInt()) {
                deleteChoice(choice);
                break;
            }
            System.out.println(adminDeleteMessagePresenter.strInvalidInput());
        }
    }


    private void deleteChoice(Scanner choice) {
        switch(choice.nextInt()){
            case 0: deleteOrganizerSpeakerMessage(); break;
            case 1: deleteEventMessage(); break;
        }
    }


    private void deleteOrganizerSpeakerMessage(){
        DeleteSelectedMessagesController deleteMessage = new DeleteSelectedMessagesController(userController);
        ArrayList<String> history = deleteMessage.getOrganizerSpeakerMessage();
        if(history.size() != 0){
            chooseLinesToDelete(deleteMessage, history);
        }
    }


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

    private void deleteEventMessage(){
        DeleteSelectedMessagesController deleteMessage = new DeleteSelectedMessagesController(userController);
        ArrayList<String> ids = viewAllGroupConversation(deleteMessage);
        if(ids.size() != 0){
            ArrayList<String> history = selectGroupConversation(deleteMessage, ids);
            chooseLinesToDelete(deleteMessage, history);
        }
    }


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
