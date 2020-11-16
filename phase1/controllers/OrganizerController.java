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
    //don't make below lines final: they definitely require modification.
    private ArrayList<String> availableAction = new ArrayList<>();
    private ArrayList<String> availableMethod = new ArrayList<>();

    public OrganizerController(UserManager manager) {
        super(manager);
    }

    @Override
    public void run() {
        addActions();
        addMenu();

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
                runMethod(action);
            }
            else{
                Presenter.printInvalid("input");
            }
            enterAction = continuing();
        }
        logout();
    }

    private void runMethod (int action){
        switch(action){
            case 1: createRoom(); break;
            case 2: createSpeaker(); break;
            case 3: addSchedule(); break;
            case 4: rescheduleSpeaker(); break;
            case 5: sendPrivateMessage(); break;
            case 6: viewPrivateMessage(); break;
            case 7: sendCoopMessage(); break;
            case 8: viewCoopChat(); break;
            case 9: messageAllAttendee(); break;
        }
    }

    private void addMenu(){
        availableAction.add("create conference room");
        availableAction.add("create speaker account");
        availableAction.add("schedule conference");
        availableAction.add("reschedule speaker");
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("send messages in coopChatroom");
        availableAction.add("view messages from coopChatroom");
        availableAction.add("message all attendees");
    }

    private void addActions(){
        availableMethod.add("createRoom");
        availableMethod.add("createSpeaker");
        availableMethod.add("addSchedule");
        availableMethod.add("rescheduleSpeaker");
        availableMethod.add("sendPrivateMessage");
        availableMethod.add("viewPrivateMessage");
        availableMethod.add("sendCoopMessage");
        availableMethod.add("viewCoopChat");
        availableMethod.add("messageAllAttendee");
    }

    /*
    require implementation:

    create room, create speaker account, modify speaker,
     */
    private LocalDateTime[] periodProcessor(){
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
        return new LocalDateTime[]{startDateTime, endDateTime};
    }


    protected boolean addSchedule(){
        LocalDateTime[] targetPeriod = periodProcessor();
        // input time;
        ArrayList<String> freeSpeaker = userManager.availableSpeakers(targetPeriod);
        ArrayList<UUID> freeRooms = roomManager.bookingAvailable(targetPeriod);

        if (freeRooms.size() != 0 && freeSpeaker.size() != 0){
            // check whether there are rooms available during that time; (use UUID, and int for capacity)
            // if that is, allow organizer to input info of conference (code below);
            activityCreator(freeSpeaker, freeRooms, targetPeriod);
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

    private void activityCreator (ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms, LocalDateTime[] targetPeriod){
        Scanner moreInfo = new Scanner(System.in);
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
        UUID assignedRoom = freeRooms.get(roomIndex);
        Object[] actSetting = new Object[]{targetPeriod, assignedRoom, topic, speaker};
        newActivitySetter(actSetting);
    }

    private void newActivitySetter(Object[] actSettings){
        UUID assignedChat = chatroomManager.createChatroom(new ArrayList<>());
        LocalDateTime[] targetPeriod = (LocalDateTime[]) actSettings[0];
        UUID assignedRoom = (UUID) actSettings[1];
        String topic = (String) actSettings[2];
        String speaker = (String) actSettings[3];
        // above arraylist has size zero, which will be assigned to conference chat automatically;
        UUID actID = activityManager.addNewActivity(targetPeriod, new UUID[]{assignedChat, assignedRoom}, topic);
        activityManager.addSpeaker(actID, speaker);
        roomManager.BookRoom(targetPeriod, actID, assignedRoom);
        userManager.otherAddSchedule(speaker, targetPeriod, actID);
        chatroomManager.addUser(speaker, assignedChat);
    }

    protected boolean createRoom() {
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

    protected boolean createSpeaker(){
        Scanner input0 = new Scanner(System.in);
        //System.out.println("Enter the name of this Speaker");
        Presenter.printSpeakerNamePrompt();
        String name = input0.next();
        if(userManager.isUser(name, "speaker") == 0){
            createNewSpeaker(name);
            return true;
        }
        else{
            //System.out.println("The speaker is already exist.");
            Presenter.printSpeakerExist();
            return false;
        }
    }

    protected void createNewSpeaker(String name){
        Scanner input1 = new Scanner(System.in);
        //System.out.println("Enter the password of this Speaker");
        Presenter.printPasswordPrompt();
        String password = input1.next();
        String username = userManager.createUser(name, password, "speaker");
        Presenter.printUsernameIs(username);
        chatroomManager.addUser(name, chatroomManager.getCoopId());
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

    private String activitySelect(){
        ArrayList<String[]> allActivities = activityManager.viewUpcommingActivites();
        if (allActivities.size() == 0){
            return "";
        }
        Presenter.printDescription("all activities");
        Presenter.printSchedule(allActivities);
        Scanner actIDGetter = new Scanner(System.in);
        Presenter.printChangeSpeakerIDPrompt();
        String actID = actIDGetter.nextLine();
        //check whether ID is valid;
        if (! extractActIDHelper(allActivities).contains(actID)){
            Presenter.printInvalid("activity ID");
            return "";
        }
        return actID;
    }

    private String chooseSpeaker(String[] actInfo, LocalDateTime[] actTime){
        ArrayList<String> freeSpeakers = userManager.availableSpeakers(actTime);
        freeSpeakers.add(actInfo[5]);
        Presenter.printSpeakers(freeSpeakers);
        Scanner speakerScanner = new Scanner(System.in);
        Presenter.printSpeakerAssignPrompt();
        String speaker = speakerScanner.nextLine();
        if (! freeSpeakers.contains(speaker)){
            Presenter.printInvalid("speaker");
            return "";
        }
        return speaker;
    }

    protected void rescheduleSpeaker(){
        String actID = activitySelect();
        if(actID.equals("")){
            return;
        }
        String[] actInfo = activityManager.searchActivityByUUID(actID);
        LocalDateTime[] actTime = getTimeHelper(actInfo);
        String speaker = chooseSpeaker(actInfo, actTime);
        if (speaker.equals("")){
            return;
        }
        updateRescheduledSpeaker(actInfo, actTime, speaker);
    }

    private void updateRescheduledSpeaker(String[] actInfo, LocalDateTime[] actTime, String speaker){
        activityManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        userManager.deleteActivity(actInfo[5], actTime);
        userManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
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
