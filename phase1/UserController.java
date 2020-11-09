import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

//public abstract class UserController
public class UserController {
    private UserManager userma = new UserManager();
    private ChatroomManager chatmana = new ChatroomManager();
    private ActivityManager actmanag = new ActivityManager();

    //just for occupying the space;
    public void run(){}

    private void viewPrivateMessage(){
        // may add particular user for viewing;
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

    private void sendPrivateMessage(){
        Scanner userScanner = new Scanner(System.in);
        System.out.println("please input the username of person " +
                "you wish to contact");
        String userName = userScanner.nextLine();

        Scanner messageScan = new Scanner(System.in);
        System.out.println("please input the emssage you wanta send:");
        String message = messageScan.nextLine();
        if (userma.contactable(userName)){
            // may consider putting first two lines in use-case;
            HashMap<String, UUID> contacts = userma.contacts();
            UUID chatID = contacts.get(userName);
            chatmana.sendPrivateMessage(message, chatID);
        }
        else{
            // may consider putting into another private method;
            ArrayList<String> userInvolved = new ArrayList<String>();
            userInvolved.add(userma.currentUsername());
            userInvolved.add(userName);
            UUID newChatroom = chatmana.createChatroom(userInvolved);
            userma.selfAddChatroom(userName, newChatroom);
            userma.otherAddChatroom(userName, newChatroom);
            chatmana.sendPrivateMessage(message, newChatroom);
        }
    }


    private void viewAllSchedule(){
        HashMap<LocalDateTime[], UUID> schedules = userma.schedules();
        ArrayList<String> actIDs = UUIDlist(schedules);
        ArrayList<Activity> allSchedule = actmanag.searchActivityByUUID(actIDs);


        // will call presenter below
    }

    private ArrayList<String> UUIDlist(HashMap<LocalDateTime[], UUID> schedule){
        ArrayList<UUID> IDs = (ArrayList<UUID>) schedule.values();
        ArrayList<String> stringIDs = new ArrayList<String>();
        for (UUID id: IDs){
            stringIDs.add(id.toString());
        }
        return stringIDs;
    }


    private Chatroom findPrivateChatroom(UUID chatID){

        //should move this method to use-case class;
        // (since this is a functionality only allowed in use-case)
        Chatroom returns = null;
        for (Chatroom chatrooms : chatmana.getPrivateChats()){
            if (chatrooms.getId().equals(chatID)){
                returns = chatrooms;
            }
        }
        return returns;
    }

}
