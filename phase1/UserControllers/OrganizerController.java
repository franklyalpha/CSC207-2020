package UserControllers;



import MessagingControllers.OrganizerMessagingController;
import com.sun.istack.internal.NotNull;
import globalConstants.*;
import presenter.Presenter;
import useCases.UserManager;

import java.time.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;


/**
 * Represents a <code>OrganizerController</code> extends from <code>UserController</code>.
 * Is specific for <code>Organizer</code> type usage.
 *
 * Includes:
 * Own constructor
 * createRoom: responsible for creating a conference room.
 * createSpeaker: responsible for creating a speaker for the conference.
 * addSchedule: responsible for creating a completely new conference.
 * rescheduleSpeaker: responsible for replacing the speaker with another for a particular conference.
 * sendCoopMessage: responsible for sending messages to all other organizers and speakers in a particular group;
 * viewCoopChat: responsible for viewing messages from the group with only organizers and speakers;
 * messageAllAttendee: responsible for sending messages to all registered attendee.
 */
public class OrganizerController extends UserController {
    //don't make below lines final: they definitely require modification.
    /**
     * an Arraylist of <code>availableAction</code>;
     * an Arraylist of <code>availableMethod</code>;
     */
    private ArrayList<String> availableAction = new ArrayList<>();
    private ArrayList<String> availableMethod = new ArrayList<>();
    private OrganizerMessagingController messageController;

    public OrganizerController(UserManager manager) {
        super(manager);
        Object[] managers = new Object[]{messageRoomManager, activityManager, userManager};
        messageController = new OrganizerMessagingController(managers);
    }

    /**
     * This method allows users to do actions corresponding to organizer's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
     */
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
            case 5: messageController.sendPrivateMessage(); break;
            case 6: messageController.viewPrivateMessage(); break;
            case 7: messageController.sendCoopMessage(); break;
            case 8: messageController.viewCoopChat(); break;
            case 9: messageController.messageAllAttendee(); break;
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


    /**
     * provides instructions for the user to create a new conference and store it in <code>ChatroomManager</code>.
     *
     * Will ask for specific time period first. Then display available rooms and speakers during that time period,
     * to allow user make choices. Then will ask user to fill in extra info(such as topic). Finally will update
     * all data in use-case classes.
     *
     * @return true iff the schedule is added successfully. 'false' otherwise.
     */
    protected boolean addSchedule(){
        while(true){
            try{
                LocalDateTime[] targetPeriod = periodProcessor();
                ArrayList<String> freeSpeaker = userManager.availableSpeakers(targetPeriod);
                ArrayList<UUID> freeRooms = roomManager.bookingAvailable(targetPeriod);
                if (freeRooms.size() != 0 && freeSpeaker.size() != 0){
                    activityCreator(freeSpeaker, freeRooms, targetPeriod);
                    break;
                }
                else{
                    throw new CannotCreateActivityException("Can't create activity");
                }
            }catch(CannotCreateActivityException e){
                Presenter.printInvalid("time period");
            }
        }
        return true;

    }
    //check speaker, positive number.

    private void activityCreator (ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms, LocalDateTime[] targetPeriod) {
        while(true){
            try{
                String[] speakerRoom = getSpeakerRoomTopic(freeSpeaker, freeRooms);
                checkInfoValid(freeSpeaker, freeRooms, speakerRoom);
                UUID assignedRoom = freeRooms.get(Integer.getInteger(speakerRoom[1]));
                Object[] actSetting = new Object[]{targetPeriod, assignedRoom, speakerRoom[2], speakerRoom[0]};
                newActivitySetter(actSetting);
                break;
            }catch(UserNotFoundException e){
                Presenter.printInvalid("speaker");
            }catch(IndexOutOfBoundsException e2){
                Presenter.printInvalid("room index");
            }
        }

    }

    private void checkInfoValid(ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms, String[] speakerRoom)
            throws UserNotFoundException {
        if (!freeSpeaker.contains(speakerRoom[0])){
            throw new UserNotFoundException("");
        }
        if (Integer.getInteger(speakerRoom[1]) < 0 || Integer.getInteger(speakerRoom[1]) >= freeRooms.size()){
            throw new IndexOutOfBoundsException();
        }
    }

    @NotNull
    private String[] getSpeakerRoomTopic(ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms) {
        Scanner moreInfo = new Scanner(System.in);
        Presenter.printSpeakerRoomPrompt(freeSpeaker, freeRooms);
        String topic = moreInfo.nextLine();
        String speaker = moreInfo.nextLine();
        int roomIndex = moreInfo.nextInt() - 1;
        return new String[]{speaker, Integer.toString(roomIndex), topic};
    }

    private void newActivitySetter(Object[] actSettings){
        UUID assignedChat = messageRoomManager.createChatroom(new ArrayList<>());
        LocalDateTime[] targetPeriod = (LocalDateTime[]) actSettings[0];
        UUID assignedRoom = (UUID) actSettings[1];
        String topic = (String) actSettings[2];
        String speaker = (String) actSettings[3];
        UUID actID = activityManager.addNewActivity(targetPeriod, new UUID[]{assignedChat, assignedRoom}, topic);
        activityManager.addSpeaker(actID, speaker);
        roomManager.BookRoom(targetPeriod, actID, assignedRoom);
        userManager.otherAddSchedule(speaker, targetPeriod, actID);
        messageRoomManager.addUser(speaker, assignedChat);
    }

    /**
     * Providing instructions for user to add a new conference room to the system.
     *
     * Will ask for capacity of the room when running the program. Then ask RoomManager to
     * create the new room.
     *
     * @return true iff the room is created (inputting appropriate capacity value). 'false' otherwise.
     */
    protected boolean createRoom() {
        while(true){
            try {
                createRoomWithCapacity();
                break;
            }catch(Exception e) {
                //System.out.println("Invalid capacity.");
                Presenter.printInvalid("capacity");
            }
        }
        return true;
    }

    private void createRoomWithCapacity() throws Exception {
        Scanner input = new Scanner(System.in);
        Presenter.printRoomCapacityPrompt();
        int a = input.nextInt();
        if (a > 0){
            roomManager.addRoom(a);
            //System.out.println("This new room capacity is " + a);
            Presenter.printRoomCapacityConfirmation(a);
        }
        else{
            //System.out.println("Invalid capacity.");
            throw new Exception();
        }
    }

    /**
     * Providing instructions for creating a new speaker.
     *
     * Will ask user to input the name and password of speaker; will printout
     * the username of speaker after creation.
     *
     * @return true iff the speaker is created successfully (the speaker is new).
     */
    protected boolean createSpeaker(){
        while(true){
            try{
                createSpeakerWithValidName();
                break;
            }catch (SpeakerAlreadyExistException e){
                Presenter.printSpeakerExist();
            }
        }
        return true;
    }

    private void createSpeakerWithValidName() throws SpeakerAlreadyExistException {
        Scanner input0 = new Scanner(System.in);
        Presenter.printSpeakerNamePrompt();
        String name = input0.next();
        if(userManager.isUser(name) == 0){
            createNewSpeaker(name);
        }
        else{
            throw new SpeakerAlreadyExistException("Speaker already exist!");
        }
    }

    private void createNewSpeaker(String name){
        Scanner input1 = new Scanner(System.in);
        //System.out.println("Enter the password of this Speaker");
        Presenter.printPasswordPrompt();
        String password = input1.next();
        String username = userManager.createUser(name, password, UserType.SPEAKER);
        Presenter.printUsernameIs(username);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
    }

    /**
     * Will printout messages being sent in chats involving only organizers and speakers.
     */
    protected void viewCoopChat(){

    }

    /**
     * Provides instructions for guiding user to send messages to all organizers and speakers.
     *
     * Will ask user to input the message want to send during running.
     */
    protected void sendCoopMessage(){

    }

    private String activitySelect(){
        ArrayList<String[]> allActivities = activityManager.viewUpcommingActivites();
        if (allActivities.size() == 0){
            return "";
        }
        Presenter.printDescription("all activities");
        Presenter.printSchedule(allActivities);
        while(true){
            try{
                return checkingValidActivityID(allActivities);
            }catch(ActivityNotFoundException e){
                Presenter.printInvalid("activity ID");
            }
        }
    }

    private String checkingValidActivityID(ArrayList<String[]> allActivities)
            throws ActivityNotFoundException {
        Scanner actIDGetter = new Scanner(System.in);
        Presenter.printChangeSpeakerIDPrompt();
        String actID = actIDGetter.nextLine();
        if (! extractActIDHelper(allActivities).contains(actID)){
            throw new ActivityNotFoundException("invalid activity ID");
        }
        return actID;
    }

    private String chooseSpeaker(String[] actInfo, LocalDateTime[] actTime){
        ArrayList<String> freeSpeakers = userManager.availableSpeakers(actTime);
        freeSpeakers.add(actInfo[5]);
        Presenter.printSpeakers(freeSpeakers);
        while(true){
            try{
                Scanner speakerScanner = new Scanner(System.in);
                Presenter.printSpeakerAssignPrompt();
                String speaker = speakerScanner.nextLine();
                if (! freeSpeakers.contains(speaker)){
                    throw new UserNotFoundException("No such user in list");
                }
                return speaker;
            }catch(UserNotFoundException e){
                Presenter.printInvalid("speaker's name");
            }
        }
    }

    /**
     * Provides instructions for user to reassign another speaker for a given conference.
     *
     * Will first printout all conferences and ask the user to make a choice.
     * Then will show all speakers who are free during the conference's time period.
     * Next asks for user's choice on which speaker to assign.
     * Finally will update all information.
     */
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

    /**
     * Provides instructions for sending message to all registered attendee.
     *
     * Will ask for messages to send during running.
     */
    protected void messageAllAttendee() {


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
