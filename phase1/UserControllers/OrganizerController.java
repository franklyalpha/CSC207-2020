package UserControllers;



import ActivityControllers.OrganizerActivityController;
import MessagingControllers.OrganizerMessagingController;
import globalConstants.*;
import presenter.Presenter;
import useCases.UserManager;

import java.util.ArrayList;
import java.util.Scanner;


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
    private OrganizerActivityController activityController;

    public OrganizerController(UserManager manager) {
        super(manager);
        Object[] managers = new Object[]{messageRoomManager, activityManager, userManager, roomManager};
        messageController = new OrganizerMessagingController(managers);
        activityController = new OrganizerActivityController(managers);
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
            case 3: activityController.addSchedule(); break;
            case 4: activityController.rescheduleSpeaker(); break;
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




    /**
     * provides instructions for the user to create a new conference and store it in <code>ChatroomManager</code>.
     *
     * Will ask for specific time period first. Then display available rooms and speakers during that time period,
     * to allow user make choices. Then will ask user to fill in extra info(such as topic). Finally will update
     * all data in use-case classes.
     *
     * @return true iff the schedule is added successfully. 'false' otherwise.
     */
    protected void addSchedule(){


    }
    //check speaker, positive number.





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
            Presenter.printRoomCapacityConfirmation(a);
        }
        else{
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







    /**
     * Provides instructions for user to reassign another speaker for a given conference.
     *
     * Will first printout all conferences and ask the user to make a choice.
     * Then will show all speakers who are free during the conference's time period.
     * Next asks for user's choice on which speaker to assign.
     * Finally will update all information.
     */
    protected void rescheduleSpeaker(){

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