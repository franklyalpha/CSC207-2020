package controllers;

import gateways.GatewayActivity;
import gateways.GatewayChat;
import gateways.GatewayRoom;
import gateways.GatewayUser;
import presenter.Presenter;
import useCases.ActivityManager;
import useCases.ChatroomManager;
import useCases.RoomManager;
import useCases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

//public abstract class controllers.UserController
public class UserController {
    protected UserManager userManager;
    protected ChatroomManager chatroomManager;
    protected ActivityManager activityManager;
    protected RoomManager roomManager;

    //just for occupying the space;
    public UserController(UserManager manager) {
        userManager = manager;
        chatroomManager = new GatewayChat().deserialize();
        activityManager = new GatewayActivity().deserialize();
        roomManager = new GatewayRoom().deserialize();
    }

    public void run(){}

    protected void viewPrivateMessage(){
        // may add particular user for viewing;
        // should call presenter to display; but will acquire data here;
        HashMap<String, UUID> contact = userManager.contacts();
        HashMap<String, ArrayList<String>> historyChat = new HashMap<>();
        for (String users : contact.keySet()){
            ArrayList<String> chatMessage = chatroomManager.getHistoricalChats(contact.get(users));
            historyChat.put(users, chatMessage);
        }
        Presenter.printContactPrompt("contact");
        Presenter.printList(contact.keySet().toArray());
        Scanner scan = new Scanner(System.in);
        String contactUser = scan.nextLine();
        // will call presenter with final historyChat
        Presenter.printMessagesInInterval(historyChat.get(contactUser), 1, historyChat.get(contactUser).size());

    }

    protected void viewGroupMessage(){
        // may add particular user for viewing;
        // should call presenter to display; but will acquire data here;
        HashMap<LocalDateTime[], UUID> act = userManager.getActivities();
        HashMap<String, ArrayList<String>> historyChat = new HashMap<>();
        for (LocalDateTime[] period : act.keySet()){
            UUID chatID = activityManager.getConferenceChat(act.get(period));
            ArrayList<String> chatMessage = chatroomManager.getHistoricalChats(chatID);
            String topic = activityManager.searchActivityByUUID(act.get(period).toString())[1];
            historyChat.put(topic, chatMessage);
        }
        Presenter.printContactPrompt("event");
        Presenter.printList(historyChat.keySet().toArray());
        Scanner scan = new Scanner(System.in);
        String selectedEvent = scan.nextLine();
        // will call presenter with final historyChat
        Presenter.printMessagesInInterval(historyChat.get(selectedEvent), 1, historyChat.get(selectedEvent).size());

    }

    protected void sendPrivateMessage(){
        // may consider putting into a private method mainly calling
        // for inputs;
        Scanner userScanner = new Scanner(System.in);
        //System.out.println("please input the username of person " + "you wish to contact");
        Presenter.printUserToContactPrompt();
        String userName = userScanner.nextLine();

        Scanner typeScan = new Scanner(System.in);
        Presenter.printTypeToContactPrompt();
        String typeName = typeScan.nextLine();

        Scanner messageScan = new Scanner(System.in);
        //System.out.println("please input the message you wanna send:");
        Presenter.printMessagePrompt();
        String message = messageScan.nextLine();
        send(userName, message, typeName);

    }

    protected void send(String userName, String message, String typeName){
        if (userManager.contactable(userName)){
            // may consider putting first two lines in use-case;
            HashMap<String, UUID> contacts = userManager.contacts();
            UUID chatID = contacts.get(userName);
            chatroomManager.sendPrivateMessage(message, chatID);
        }
        else{
            // may consider putting into another private method;
            if (userManager.isUser(userName, typeName) != 0){
                UUID newChatroom = newPrivateChatroomCreator(userName);
                chatroomManager.sendPrivateMessage(message, newChatroom);
            }
            else {
                //System.out.println("Invalid username or usertype! Try again later!");
                Presenter.printInvalid("username or usertype");
                //return to main menu;
            }
        }
    }

    private UUID newPrivateChatroomCreator(String userName){
        ArrayList<String> userInvolved = new ArrayList<>();
        userInvolved.add(userManager.currentUsername());
        userInvolved.add(userName);

        UUID newChatroom = chatroomManager.createChatroom(userInvolved);
        userManager.selfAddChatroom(userName, newChatroom);
        userManager.otherAddChatroom(userName, newChatroom);
        return newChatroom;
    }


    protected ArrayList<String[]> viewEnrolledSchedule(){
        HashMap<LocalDateTime[], UUID> schedules = userManager.schedules();
        ArrayList<String[]> allSchedule = new ArrayList<>();
        for (LocalDateTime[] time : schedules.keySet()){
            String[] partialInfo = activityManager.searchActivityByUUID(schedules.get(time).toString());
            allSchedule.add(partialInfo);
        }
        // will call presenter below: printSchedule
        return allSchedule;
    }

    protected ArrayList<String> extractActIDHelper (ArrayList<String[]> available){
        ArrayList<String> actIDs = new ArrayList<>();
        for (String[] schedule: available){
            actIDs.add(schedule[0]);
        }
        return actIDs;
    }

    protected LocalDateTime[] getTimeHelper(String[] scheduleInfo){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime[] time = {LocalDateTime.parse(scheduleInfo[2], df),
                LocalDateTime.parse(scheduleInfo[3], df)};
        return time;
    }

    protected void logout(){
        userManager.logout();
        new GatewayUser().ser(userManager);
        new GatewayRoom().ser(roomManager);
        new GatewayChat().ser(chatroomManager);
        new GatewayActivity().ser(activityManager);
    }




}
