package controllers;


import presenter.Presenter;
import useCases.UserManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        ArrayList<String> availableAction = new ArrayList<>();
        availableAction.add("create conference room");
        availableAction.add("create speaker account");
        availableAction.add("schedule conference");
        availableAction.add("reschedule speaker");
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("view group messages");
        availableAction.add("send messages in coopChatroom");
        availableAction.add("view messages from coopChatroom");
        availableAction.add("message all attendees");
        availableAction.add("view singed conferences");
        availableAction.add("log out");
        ArrayList<String> availableMethod = new ArrayList<>();
        availableMethod.add("createRoom");
        availableMethod.add("createSpeaker");
        availableMethod.add("addSchedule");
        availableMethod.add("rescheduleSpeaker");
        availableMethod.add("sendPrivateMessage");
        availableMethod.add("viewPrivateMessage");
        availableMethod.add("viewGroupMessage");
        availableMethod.add("sendCoopMessage");
        availableMethod.add("viewCoopChat");
        availableMethod.add("messageAllAttendee");
        availableMethod.add("viewEnrolledSchedule");
        int action;
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            /*System.out.println("Services apply\n");
            for(String a: availableAction){
                System.out.println(availableAction.indexOf(a)+1 + ": " + a);

            }*/
            Presenter.printAvailableActions(availableAction);
            action = scan.nextInt();
            if (0 < action && action <= availableMethod.size()) {
                try {
                    Method method = this.getClass().getMethod(availableMethod.get(action - 1));
                    try {
                        method.invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            else{
                Presenter.printInvalid("input");
            }
            enterAction = continuing();
        }
    }

    /*
    require implementation:

    create room, create speaker account, modify speaker,
     */

    private boolean addSchedule(){
        Scanner start = new Scanner(System.in);
        //System.out.println("Please input year, month, day, hour, minute of start time IN ORDER: ");
        Presenter.printTimePrompt("start");
        LocalDateTime startDateTime = LocalDateTime.of(start.nextInt(),
                start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt());

        Scanner end = new Scanner(System.in);
        //System.out.println("Please input year, month, day, hour, minute of end time IN ORDER: ");
        Presenter.printTimePrompt("end");
        LocalDateTime endDateTime = LocalDateTime.of(end.nextInt(),
                end.nextInt(), end.nextInt(), end.nextInt(), end.nextInt());
        LocalDateTime[] targetPeriod = {startDateTime, endDateTime};
        // input time;
        ArrayList<String> freeSpeaker = userManager.availableSpeakers(targetPeriod);
        ArrayList<UUID> freeRooms = roomManager.bookingAvailable(targetPeriod);

        if (freeRooms.size() != 0 &&
                freeSpeaker.size() != 0){
            // check whether there are rooms available during that time; (use UUID, and int for capacity)
            // if that is, allow organizer to input info of conference (code below);
            Scanner moreInfo = new Scanner(System.in);
            /*System.out.println("Here are available speaker names: " + freeSpeaker + "\n Here are available rooms ID: " +
                    "" + freeRooms);
            System.out.println("Please input topic, speaker and ith room (e.g, 1st room: input 1) for this activity" +
                    " IN ORDER and in different lines: (if there are invalid inputs, will use the first one as default)");*/
            Presenter.printSpeakerRoomPrompt(freeSpeaker, freeRooms);
            String topic = moreInfo.nextLine();
            String speaker = moreInfo.nextLine();
            int roomIndex = moreInfo.nextInt() - 1;
            if (!freeSpeaker.contains(speaker)){
                speaker = freeSpeaker.get(0);
            }
            if (roomIndex < 0 || roomIndex >= freeRooms.size()){
                roomIndex = 0;
            }
            UUID assignedChat = chatroomManager.createChatroom(new ArrayList<>());
            // above arraylist has size zero, which will be assigned to conference chat automatically;
            UUID assignedRoom = freeRooms.get(roomIndex);
            UUID actID = activityManager.addNewActivity(targetPeriod[0], targetPeriod[1], assignedChat, assignedRoom, topic);
            activityManager.addSpeaker(actID, speaker);
            roomManager.BookRoom(targetPeriod, actID, assignedRoom);
            userManager.otherAddSchedule(speaker, targetPeriod, actID);
            chatroomManager.addUser(speaker, assignedChat);
            // then choose a room;
            // then program creates schedule automatically, and update both activity, speaker and room.
            // saving file???
            return true;
        }
        else{
            //System.out.println("Invalid time period! Please reconsider another time!!!");
            Presenter.printInvalid("time period");
        }
        return false;
    }
    //check speaker, positive number.

    private boolean createRoom() {
        int a;
        Scanner input = new Scanner(System.in);
        //System.out.println("Enter the capacity of this room");
        Presenter.printRoomCapacityPrompt();
        try {
                a = input.nextInt();
                if (a > 0){
                    roomManager.addRoom(a);
                    //System.out.println("This new room capacity is " + a);
                    Presenter.printRoomCapacityConfirmation(a);
                    return true;
                }
                else{
                    //System.out.println("Invalid capacity.");
                    Presenter.printInvalid("capacity");
                    return false;
                }

        }catch(Exception e) {
                //System.out.println("Invalid capacity.");
                Presenter.printInvalid("capacity");
                return false;
        }
    }

    private boolean createSpeaker(){
        Scanner input0 = new Scanner(System.in);
        //System.out.println("Enter the name of this Speaker");
        Presenter.printSpeakerNamePrompt();
        String name = input0.next();
        if(userManager.isUser(name, "Speaker") == 0){
            Scanner input1 = new Scanner(System.in);
            //System.out.println("Enter the password of this Speaker");
            Presenter.printPasswordPrompt();
            String password = input1.next();
            userManager.createUser(name, password, "speaker");
            chatroomManager.addUser(name, chatroomManager.getCoopId());
            return true;
        }
        else{
            //System.out.println("The speaker is already exist.");
            Presenter.printSpeakerExist();
            return false;
        }
    }

    protected void viewCoopChat(){
        UUID coopChatID = chatroomManager.getCoopId();
        ArrayList<String> message = chatroomManager.getHistoricalChats(coopChatID);
        Presenter.printMessagesInInterval(message, 1, message.size());
    }

    protected void sendCoopMessage(){
        UUID coopChatID = chatroomManager.getCoopId();
        Scanner messenger = new Scanner(System.in);
        //System.out.println("Please input your message below: ");
        Presenter.printMessagePrompt();
        String message = messenger.nextLine();
        chatroomManager.sendMessage(message, coopChatID);
    }

    protected void rescheduleSpeaker(){
        ArrayList<String[]> allActivities = activityManager.viewUpcommingActivites();
        // presenter: printSchedule
        //System.out.println("here are all activity IDs: " + extractActIDHelper(allActivities));
        Presenter.printDescription("all activities");
        Presenter.printSchedule(allActivities);

        Scanner actIDGetter = new Scanner(System.in);
        //System.out.println("Please input the ID of activity you wish to change speaker: ");
        Presenter.printChangeSpeakerIDPrompt();
        String actID = actIDGetter.nextLine();
        if (! extractActIDHelper(allActivities).contains(actID)){
            //System.out.println("invalid activity ID! try again later");
            Presenter.printInvalid("activity ID");
            return;
        }
        String[] actInfo = activityManager.searchActivityByUUID(actID);
        LocalDateTime[] actTime = getTimeHelper(actInfo);
        ArrayList<String> freeSpeakers = userManager.availableSpeakers(actTime);
        freeSpeakers.add(actInfo[5]);
        //System.out.println("here are available speakers : "+ freeSpeakers);
        Presenter.printSpeakers(freeSpeakers);
        Scanner speakerScanner = new Scanner(System.in);

        //System.out.println("Please input the speaker you wish to assign");
        Presenter.printSpeakerAssignPrompt();
        String speaker = speakerScanner.nextLine();
        if (! freeSpeakers.contains(speaker)){
            //System.out.println("invalid speaker! try again later");
            Presenter.printInvalid("speaker");
            return;
        }
        activityManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        userManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
        userManager.deleteActivity(actInfo[5], actTime);
    }

    protected void messageAllAttendee(){
        ArrayList<String> attendeeName = userManager.allAttendee();
        Scanner messageScanner = new Scanner(System.in);
        //System.out.println("please input message: ");
        Presenter.printMessagePrompt();
        String message = messageScanner.nextLine();
        for (String attendee : attendeeName){
            send(attendee, message, "attendant");
        }
    }
    private boolean continuing(){
        boolean enterAction = true;
        //System.out.println("Continue for other services? Please enter true or false. (false for log out)");
        Presenter.printContinueServicePrompt();
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true")){
            enterAction = false;
        }
        return enterAction;
    }

}
