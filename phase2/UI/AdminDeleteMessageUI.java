package UI;

import Controllers.DeletePrivateConversationController;
import Controllers.DeleteSelectedMessagesController;
import Controllers.UserController;
import globallyAccessible.UserNotFoundException;
import menuPresenter.AdminDeleteMessagePresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminDeleteMessageUI extends UserUI {

    final private AdminDeleteMessagePresenter adminDeleteMessagePresenter = new AdminDeleteMessagePresenter();

    public AdminDeleteMessageUI(UserController userController) {
        super(userController);
    }

    @Override
    public void run() {
        super.run();
    }

    private void chooseChatOrMessage(){
        System.out.println(adminDeleteMessagePresenter.strMessageActionMenu());
        while(true){
            Scanner choice = new Scanner(System.in);
            if(!choice.hasNextInt()){
                System.out.println(adminDeleteMessagePresenter.strInvalidInput());
            }
            else{
                switch(choice.nextInt()){
                    case 1: deletePrivateConversation();
                    case 2: deleteSelectedMessages();
                }
            }
        }
    }

    private void deletePrivateConversation(){
        while(true){
            try{
                DeletePrivateConversationController deletePrivate = new DeletePrivateConversationController(userController);
                System.out.println(adminDeleteMessagePresenter.strUsersToAddPrompt());
                Scanner users = new Scanner(System.in);
                String username1 = users.nextLine();
                String username2 = users.nextLine();
                deletePrivate.checkIsUser(new String[]{username1, username2});
                deletePrivate.deletePrivateConversation();
                break;
            }catch(UserNotFoundException e){
                e.printStackTrace();
            }
        }
    }


    // followings can be placed in another UI
    private void deleteSelectedMessages(){
        System.out.println(adminDeleteMessagePresenter.strGroupActionMenu());
        while(true){
            Scanner choice = new Scanner(System.in);
            if(!choice.hasNextInt()){
                System.out.println(adminDeleteMessagePresenter.strInvalidInput());
            }
            else{
                switch(choice.nextInt()){
                    case 0: deleteOrganizerSpeakerMessage();
                    case 1: deleteActivityMessage();
                }
                break;
            }
        }
    }

    private void deleteOrganizerSpeakerMessage(){
        DeleteSelectedMessagesController deleteMessage = new DeleteSelectedMessagesController(userController);
        ArrayList<String> history = deleteMessage.getOrganizerSpeakerMessage();
        chooseLinesToDelete(deleteMessage, history);
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

    private void deleteActivityMessage(){
        DeleteSelectedMessagesController deleteMessage = new DeleteSelectedMessagesController(userController);
        ArrayList<String> history = selectGroupConversation(deleteMessage);
        chooseLinesToDelete(deleteMessage, history);
    }

    private ArrayList<String> selectGroupConversation(DeleteSelectedMessagesController deleteMessage){
        ArrayList<String> ids = viewAllGroupConversation(deleteMessage);
        System.out.println(adminDeleteMessagePresenter.strChatToDeletePrompt());
        while(true){
            Scanner conversationID = new Scanner(System.in);
            String ID = conversationID.nextLine();
            if (ids.contains(ID)){
                return deleteMessage.getGroupHistoryChat(ID);
            }
            else{
                System.out.println(adminDeleteMessagePresenter.strInvalidInput());
            }
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
