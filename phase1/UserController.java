import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

//public abstract class UserController
public class UserController {
    private UserManager userma = new UserManager();
    private ChatroomManager chatmana = new ChatroomManager();

    //just for occupying the space;
    public void run(){}

    private void viewPrivateMessage(){
        // should call presenter to display; but will acquire data here;
        HashMap<String, UUID> contacts = userma.contacts();
        HashMap<String, ArrayList<String>> historychat = new HashMap<String, ArrayList<String>>();
        for (String users : contacts.keySet()){
            Chatroom targetedChat = findPrivateChatroom(contacts.get(users));
            ArrayList<String> chatmessage = targetedChat.getHistoricalChats();
            historychat.put(users, chatmessage);
        }
        // will call presenter with final historyChat;

    }

    private void send(){
        Scanner userScanner = new Scanner(System.in);
        System.out.println("please input the username of person " +
                "you wish to contact");
        String userName = userScanner.nextLine();

    }



    private Chatroom findPrivateChatroom(UUID chatID){
        Chatroom returns = null;
        for (Chatroom chatrooms : chatmana.getPrivateChats()){
            if (chatrooms.getId().equals(chatID)){
                returns = chatrooms;
            }
        }
        return returns;
    }

}
