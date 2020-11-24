package MessagingControllers;

import globalConstants.UserNotFoundException;
import presenter.Presenter;
import useCases.ActivityManager;
import useCases.MessageRoomManager;
import useCases.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class UserMessagingController implements IMessaging {
    protected UserManager userManager;
    protected ActivityManager activityManager;
    protected MessageRoomManager messageRoomManager;


    public UserMessagingController(Object[] managers){
        userManager = (UserManager)managers[2];
        activityManager = (ActivityManager) managers[1];
        messageRoomManager = (MessageRoomManager) managers[0];

    }


    @Override
    public void sendPrivateMessage() {
        // may consider putting into a private method mainly calling
        // for inputs;
        while(true){
            try{
                Scanner userScanner = new Scanner(System.in);
                Presenter.printUserToContactPrompt();
                String userName = userScanner.nextLine();
                Scanner messageScan = new Scanner(System.in);
                Presenter.printMessagePrompt();
                String message = messageScan.nextLine();
                send(userName, message);
                break;
            }
            catch(UserNotFoundException e){
                Presenter.printInvalid("username");
            }
        }
    }

    /**
     * Sends a private message to the user specified by <code>userName</code>. If there is no pre-existing conversation,
     * a new <code>Chatroom</code> is created. If the specified user does not exist, an invalid input message appears.
     * @param userName String representing the username of the user we wish to send the message to.
     * @param message String representing the message we wish to send.
     */
    protected void send(String userName, String message) throws UserNotFoundException {
        if (userManager.contactable(userName)){
            // may consider putting first two lines in use-case;
            HashMap<String, UUID> contacts = userManager.contacts();
            UUID chatID = contacts.get(userName);
            messageRoomManager.sendPrivateMessage(message, chatID);
        }
        else{
            // may consider putting into another private method;
            if (userManager.isUser(userName) != 0){
                UUID newChatroom = newPrivateChatroomCreator(userName);
                messageRoomManager.sendPrivateMessage(message, newChatroom);
            }
            else {
                throw new UserNotFoundException("User not found");
            }
        }
    }

    private UUID newPrivateChatroomCreator(String userName){
        ArrayList<String> userInvolved = new ArrayList<>();
        userInvolved.add(userManager.currentUsername());
        userInvolved.add(userName);

        UUID newChatroom = messageRoomManager.createChatroom(userInvolved);
        userManager.selfAddChatroom(userName, newChatroom);
        userManager.otherAddChatroom(userName, newChatroom);
        return newChatroom;
    }

    @Override
    public void viewPrivateMessage() {
        HashMap<String, UUID> contact = userManager.contacts();
        if (contact.size() == 0){
            return;
        }
        HashMap<String, ArrayList<String>> historyChat = new HashMap<>();
        for (String users : contact.keySet()){
            ArrayList<String> chatMessage = messageRoomManager.getHistoricalChats(contact.get(users));
            historyChat.put(users, chatMessage);
        }
        privatePrinting(historyChat, contact);
    }

    /**
     * Prints the private messages between the user and the specified contact, but only if they are connected.
     * @param historyChat ArrayList of Strings representing the user's chats.
     * @param contact UUID representing the contact whose messages we want to retrieve.
     */
    private void privatePrinting(HashMap<String, ArrayList<String>> historyChat, HashMap<String, UUID> contact){
        Presenter.printContactPrompt("contact");
        Presenter.printList(contact.keySet().toArray());
        Scanner scan = new Scanner(System.in);
        String contactUser = scan.nextLine();
        if (!contact.containsKey(contactUser)){
            Presenter.printInvalid("user name");
            return;
        }
        // will call presenter with final historyChat
        Presenter.printMessagesInInterval(historyChat.get(contactUser), 1, historyChat.get(contactUser).size());
    }

    @Override
    public void viewGroupMessage() {
        // may add particular user for viewing;
        // should call presenter to display; but will acquire data here;
        HashMap<LocalDateTime[], UUID> act = userManager.getActivities();
        if (act.size() == 0){
            return;
        }
        HashMap<String, ArrayList<String>> historyChat = new HashMap<>();
        for (LocalDateTime[] period : act.keySet()){
            UUID chatID = activityManager.getConferenceChat(act.get(period));
            ArrayList<String> chatMessage = messageRoomManager.getHistoricalChats(chatID);
            String topic = activityManager.searchActivityByUUID(act.get(period).toString())[1];
            historyChat.put(topic, chatMessage);
        }
        groupPrinting(historyChat);
    }

    private void groupPrinting(HashMap<String, ArrayList<String>> historyChat){
        Presenter.printContactPrompt("event");
        Presenter.printList(historyChat.keySet().toArray());
        Scanner scan = new Scanner(System.in);
        String selectedEvent = scan.nextLine();
        if (!historyChat.containsKey(selectedEvent)){
            Presenter.printInvalid("activity name");
            return;
        }
        // will call presenter with final historyChat
        Presenter.printMessagesInInterval(historyChat.get(selectedEvent), 1, historyChat.get(selectedEvent).size());
    }
}
