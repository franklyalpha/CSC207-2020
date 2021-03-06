package UI;



import Controllers.CreateRoomController;
import Controllers.CreateSpeakerController;
import Controllers.UserController;
import Presenters.Presenter;

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
public class OrganizerUI extends UserUI{
    public OrganizerUI(UserController userController) {
        super(userController);
    }

    /**
     * This method allows users to do actions corresponding to organizer's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
     */


    void addMenu(){
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
    // should move to presenter;

    protected void createRoom(){
        CreateRoomController createRoon = new CreateRoomController(userController);
        while(true){
            try {
                createRoon.createRoomWithCapacity();
                break;
            }catch(Exception e) {
                Presenter.printInvalid("capacity");
            }
        }
    }

    protected void createSpeaker(){
        CreateSpeakerController createSpeaker = new CreateSpeakerController(userController);
        while(true){
            try{
                Scanner input0 = new Scanner(System.in);
                Presenter.printSpeakerNamePrompt();
                String name = input0.next();
                createSpeaker.ValidateName(name);
                Presenter.printPasswordPrompt();
                String password = input0.next();
                Presenter.printUsernameIs(createSpeaker.createNewSpeaker(name, password));
                break;
            }catch (SpeakerAlreadyExistException e){
                Presenter.printSpeakerExist();
            }
        }
    }

    protected void addSchedule() {
        new OrgAddScheduleUI(userController).run();
    }

    protected void rescheduleSpeaker(){
        new OrgRescheduleSpeakerUI(userController).run();
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

    //check speaker, positive number.





    /**
     * Providing instructions for user to add a new conference room to the system.
     *
     * Will ask for capacity of the room when running the program. Then ask RoomManager to
     * create the new room.
     *
     * @return true iff the room is created (inputting appropriate capacity value). 'false' otherwise.
     */


    /**
     * Providing instructions for creating a new speaker.
     *
     * Will ask user to input the name and password of speaker; will printout
     * the username of speaker after creation.
     *
     * @return true iff the speaker is created successfully (the speaker is new).
     */


    /**
     * Will printout messages being sent in chats involving only organizers and speakers.
     */


    /**
     * Provides instructions for guiding user to send messages to all organizers and speakers.
     *
     * Will ask user to input the message want to send during running.
     */








    /**
     * Provides instructions for user to reassign another speaker for a given conference.
     *
     * Will first printout all conferences and ask the user to make a choice.
     * Then will show all speakers who are free during the conference's time period.
     * Next asks for user's choice on which speaker to assign.
     * Finally will update all information.
     */




    /**
     * Provides instructions for sending message to all registered attendee.
     *
     * Will ask for messages to send during running.
     */

    boolean continuing(){
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
