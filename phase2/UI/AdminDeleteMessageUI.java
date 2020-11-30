package UI;

import Controllers.DeletePrivateConversationController;
import Controllers.DeleteSelectedMessagesController;
import Controllers.UserController;
import Presenters.Presenter;
import globallyAccessible.UserNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminDeleteMessageUI extends UserUI {

    public AdminDeleteMessageUI(UserController userController) {
        super(userController);
    }

    @Override
    public void run() {
        super.run();
    }

    private void chooseChatOrMessage(){
        System.out.println("Please enter '1' if you want to delete whole conversation, or '2' if for messages: ");
        while(true){
            Scanner choice = new Scanner(System.in);
            if(!choice.hasNextInt()){
                Presenter.printInvalid("input");
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
                System.out.println("Please enter username of users involved in this PRIVATE chat, one user for each line");
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
        System.out.println("Please enter '0' for deleting message in organizer-speaker conversation, or '1' for activity-group messages");
        while(true){
            Scanner choice = new Scanner(System.in);
            if(!choice.hasNextInt()){
                Presenter.printInvalid("invalid");
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
        for(int i = 0; i < history.size(); i++){
            System.out.println(i + ": " + history.get(i) + "\n");
        }
        Scanner lines = new Scanner(System.in);
        System.out.println("please enter one or more integers, which are indexes of messages you want to delete (separate by space): \n" +
                "(Any integers greater than max index or less than zero will be filtered)");
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
        System.out.println("Please enter the ID of chat you wish to delete message");
        while(true){
            Scanner conversationID = new Scanner(System.in);
            String ID = conversationID.nextLine();
            if (ids.contains(ID)){
                return deleteMessage.getGroupHistoryChat(ID);
            }
            else{
                Presenter.printInvalid("input");
            }
        }
    }

    private ArrayList<String> viewAllGroupConversation(DeleteSelectedMessagesController deleteMessage) {
        ArrayList<String[]> groupConversationId = deleteMessage.groupChatIDs();
        System.out.println("below are conversation IDs paired with corresponding chats");
        ArrayList<String> ids = new ArrayList<>();
        for(String[] info: groupConversationId){
            System.out.println(info[0] + ": " + info[1] + "\n");
            ids.add(info[1]);
        }
        return ids;
    }
}
