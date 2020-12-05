package UI;

import Controllers.DeleteSelectedMessagesController;
import Controllers.UserController;
import menuPresenter.AdminDeleteMessagePresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminDeleteMessageUI extends AdminDeleteConversationMessageUI{
    final private AdminDeleteMessagePresenter adminDeleteMessagePresenter = new AdminDeleteMessagePresenter();

    public AdminDeleteMessageUI(UserController userController) {
        super(userController);
    }

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

    private void deleteChoice(Scanner choice) {
        switch(choice.nextInt()){
            case 0: deleteOrganizerSpeakerMessage();
            case 1: deleteEventMessage();
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
