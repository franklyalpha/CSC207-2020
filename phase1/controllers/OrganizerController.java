package controllers;


import useCases.UserManager;

import java.time.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class OrganizerController extends UserController {
    public OrganizerController(UserManager manager) {
        super(manager);
    }

    @Override
    public void run() {
        ArrayList<String> availableAction = new ArrayList<String>();
        availableAction.add("create conference room");
        availableAction.add("create speaker account");
        availableAction.add("schedule conference");
        availableAction.add("reschedule speaker");
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("view group messages");
        availableAction.add("send messages in coopchatroom");
        availableAction.add("view messages from coopchatroom");
        availableAction.add("message all attendees");
        availableAction.add("view singed conferences");
        availableAction.add("log out");
        int action = 0;
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            System.out.println("Services apply\n");
            for(String a: availableAction){
                System.out.println(availableAction.indexOf(a)+1 + ": " + a);

            }
            action = scan.nextInt();
            switch (action){
                    case 1 : createRoom();
                    case 2 : createSpeaker();
                    case 3 : addSchedule();
                    case 4 : rescheduleSpeaker();
                    case 5 : sendPrivateMessage();
                    case 6 : viewPrivateMessage();
                    case 7 : viewGroupMessage();
                    case 8 : sendCoopMessage();
                    case 9 : viewCoopChat();
                    case 10: messageAllAttendee();
                    case 11: viewEnrolledSchedule();
                    case 12:
                        logout();
                        enterAction = false;
                        break;
                    default: System.out.println("invalid action.");
            }
            if (!enterAction){
                boolean whetherContinue = continuing();
                if (!whetherContinue){
                    logout();
                    enterAction = false;
                }
            }
        }
    }

    /*
    require implementation:

    create room, create speaker account, modify speaker,
     */

    private boolean addSchedule(){
        Scanner start = new Scanner(System.in);
        System.out.println("Please input year, month, day, hour, minute of start time IN ORDER: ");
        LocalDateTime startDateTime = LocalDateTime.of(start.nextInt(),
                start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt());

        Scanner end = new Scanner(System.in);
        System.out.println("Please input year, month, day, hour, minute of end time IN ORDER: ");
        LocalDateTime endDateTime = LocalDateTime.of(end.nextInt(),
                end.nextInt(), end.nextInt(), end.nextInt(), end.nextInt());
        LocalDateTime[] targetPeriod = {startDateTime, endDateTime};
        // input time;
        ArrayList<String> freeSpeaker = userma.availableSpeakers(targetPeriod);
        ArrayList<UUID> freeRooms = roomma.bookingAvailable(targetPeriod);

        if (freeRooms.size() != 0 &&
                freeSpeaker.size() != 0){
            // check whether there are rooms available during that time; (use UUID, and int for capacity)
            // if that is, allow organizer to input info of conference (code below);
            Scanner moreInfo = new Scanner(System.in);
            System.out.println("Here are available speaker names: " + freeSpeaker + "\n Here are available rooms ID: " +
                    "" + freeRooms);
            System.out.println("Please input topic, speaker and ith room (e.g, 1st room: input 1) for this activity" +
                    " IN ORDER and in different lines: (if there are invalid inputs, will use the first one as default)");
            String topic = moreInfo.nextLine();
            String speaker = moreInfo.nextLine();
            int roomIndex = moreInfo.nextInt() - 1;
            if (!freeSpeaker.contains(speaker)){
                speaker = freeSpeaker.get(0);
            }
            if (roomIndex < 0 || roomIndex >= freeRooms.size()){
                roomIndex = 0;
            }
            UUID assignedChat = chatmana.createChatroom(new ArrayList<>());
            // above arraylist has size zero, which will be assigned to conference chat automatically;
            UUID assignedroom = freeRooms.get(roomIndex);
            UUID actID = actmanag.addNewActivity(targetPeriod[0], targetPeriod[1], assignedChat, assignedroom, topic);
            actmanag.addSpeaker(actID, speaker);
            roomma.BookRoom(targetPeriod, actID, assignedroom);
            userma.otherAddSchedule(speaker, targetPeriod, actID);
            chatmana.addUser(speaker, assignedChat);
            // then choose a room;
            // then program creates schedule automatically, and update both activity, speaker and room.
            // saving file???
            return true;
        }
        else{
            System.out.println("Invalid time period! Please reconsider another time!!!");
        }
        return false;
    }
    //check speaker, positive number.

    private boolean createRoom() {
        int a;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the capacity of this room");
        try {
                a = input.nextInt();
                if (a > 0){
                    roomma.addRoom(a);
                    System.out.println("This new room capacity is " + a);
                    return true;
                }
                else{
                    System.out.println("Invalid capacity.");
                    return false;
                }

        }catch(Exception e) {
                System.out.println("Invalid capacity.");
                return false;
        }
    }

    private boolean createSpeaker(){
        Scanner input0 = new Scanner(System.in);
        System.out.println("Enter the name of this Speaker");
        String name = input0.next();
        if(userma.isUser(name, "Speaker") == 0){
            Scanner input1 = new Scanner(System.in);
            System.out.println("Enter the password of this Speaker");
            String password = input1.next();
            userma.createUser(name, password, "speaker");
            chatmana.addUser(name, chatmana.getCoopId());
            return true;
        }
        else{
            System.out.println("The speaker is already exist.");
            return false;
        }
    }

    protected void viewCoopChat(){
        UUID coopChatID = chatmana.getCoopId();
        ArrayList<String> message = chatmana.getHistoricalChats(coopChatID);
        // presenter
    }

    protected void sendCoopMessage(){
        UUID coopChatID = chatmana.getCoopId();
        Scanner messager = new Scanner(System.in);
        System.out.println("Please input your message below: ");
        String message = messager.nextLine();
        chatmana.sendMessage(message, coopChatID);
    }

    protected void rescheduleSpeaker(){
        ArrayList<String[]> allActivities = actmanag.viewUpcommingActivites();
        // presenter
        System.out.println("here are all activity IDs: " + extractActIDHelper(allActivities));

        Scanner actIDgetter = new Scanner(System.in);
        System.out.println("Please input the ID of activity you wish to change speaker: ");
        String actID = actIDgetter.nextLine();
        if (! extractActIDHelper(allActivities).contains(actID)){
            System.out.println("invalid activity ID! try again later");
            return;
        }
        String[] actInfo = actmanag.searchActivityByUUID(actID);
        LocalDateTime[] actTime = getTimeHelper(actInfo);
        ArrayList<String> freeSpeakers = userma.availableSpeakers(actTime);
        freeSpeakers.add(actInfo[5]);
        System.out.println("here are available speakers : "+ freeSpeakers);
        Scanner speakerScanner = new Scanner(System.in);

        System.out.println("Please input the speaker you wish to assign");
        String speaker = speakerScanner.nextLine();
        if (! freeSpeakers.contains(speaker)){
            System.out.println("invalid speaker! try again later");
            return;
        }
        actmanag.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        userma.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
        userma.deleteActivity(actInfo[5], actTime);
    }

    protected void messageAllAttendee(){
        ArrayList<String> attendeeName = userma.allAttendee();
        Scanner messageScanner = new Scanner(System.in);
        System.out.println("please input message: ");
        String message = messageScanner.nextLine();
        for (String attendee : attendeeName){
            send(attendee, message, "attendant");
        }
    }
    private boolean continuing(){
        boolean enteraction = true;
        System.out.println("Continue for other services? Please enter true or false. (false for log out)");
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true")){
            enteraction = false;
        }
        return enteraction;
    }

}
