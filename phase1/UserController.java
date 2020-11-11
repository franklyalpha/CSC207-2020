import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

//public abstract class UserController
public class UserController {
    protected UserManager userma = new UserManager();
    protected ChatroomManager chatmana = new ChatroomManager();
    protected ActivityManager actmanag = new ActivityManager();
    protected RoomManager roomma = new RoomManager();

    //just for occupying the space;
    public void run(){}

    protected void viewPrivateMessage(){
        // may add particular user for viewing;
        // should call presenter to display; but will acquire data here;
        HashMap<String, UUID> contact = userma.contacts();
        HashMap<String, ArrayList<String>> historychat = new HashMap<String, ArrayList<String>>();
        for (String users : contact.keySet()){
            ArrayList<String> chatmessage = chatmana.getHistoricalChats(contact.get(users));
            historychat.put(users, chatmessage);
        }
        // will call presenter with final historyChat;

    }

    protected void viewGroupMessage(){
        // may add particular user for viewing;
        // should call presenter to display; but will acquire data here;
        HashMap<LocalDateTime[], UUID> act = userma.getActivities();
        HashMap<String, ArrayList<String>> historychat = new HashMap<String, ArrayList<String>>();
        for (LocalDateTime[] period : act.keySet()){
            UUID chatID = actmanag.getConferenceChat(act.get(period));
            ArrayList<String> chatmessage = chatmana.getHistoricalChats(chatID);
            String topic = actmanag.searchActivityByUUID(act.get(period).toString())[1];
            historychat.put(topic, chatmessage);
        }
        // will call presenter with final historyChat;

    }

    protected void sendPrivateMessage(){
        // may consider putting into a private method mainly calling
        // for inputs;
        Scanner userScanner = new Scanner(System.in);
        System.out.println("please input the username of person " +
                "you wish to contact");
        String userName = userScanner.nextLine();

        Scanner typeScan = new Scanner(System.in);
        System.out.println("please input the type of this user:");
        String typeName = userScanner.nextLine();

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
            if (userma.isUser(userName, typeName) != 0){
                ArrayList<String> userInvolved = new ArrayList<String>();
                userInvolved.add(userma.currentUsername());
                userInvolved.add(userName);

                UUID newChatroom = chatmana.createChatroom(userInvolved);
                userma.selfAddChatroom(userName, newChatroom);
                userma.otherAddChatroom(userName, newChatroom);

                chatmana.sendPrivateMessage(message, newChatroom);
            }
            else {
                System.out.println("Invalid username or usertype! Try again later!");
                //return to main menu;
            }
        }
    }


    protected void viewEnrolledSchedule(){
        HashMap<LocalDateTime[], UUID> schedules = userma.schedules();
        ArrayList<String[]> allSchedule = new ArrayList<String[]>();
        for (LocalDateTime[] time : schedules.keySet()){
            String[] partialInfo = actmanag.searchActivityByUUID(schedules.get(time).toString());
            allSchedule.add(partialInfo);
        }
        // will call presenter below
    }


}
