package UI;



import Controllers.*;
import Controllers.CreateAttendeeController;
import Presenters.Presenter;
import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;
import org.graalvm.compiler.phases.graph.ScheduledNodeIterator;
import menuPresenter.OrganizerPresenter;

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

    final protected OrganizerPresenter organizerPresenter = new OrganizerPresenter();

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
        availableAction.add("create other user account");
        availableAction.add("schedule conference");
        availableAction.add("reschedule speaker");
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("send messages in coopChatroom");
        availableAction.add("view messages from coopChatroom");
        availableAction.add("message all attendees");
    }
    //TODO should move to presenter;

    protected void createRoom(){
        CreateRoomController createRoom = new CreateRoomController(userController);
        while(true){
            try {
                createRoom.createRoomWithCondition();
                break;
            }catch(Exception e) {
                System.out.println(organizerPresenter.strInvalidCapacity());
            }
        }
    }

    protected void createSpeaker(){
        CreateSpeakerController createSpeaker = new CreateSpeakerController(userController);
        while(true){
            try{
                Scanner input0 = new Scanner(System.in);
                System.out.println(organizerPresenter.strSpeakerPrompt());
                String name = input0.next();
                createSpeaker.ValidateName(name);
                System.out.println(organizerPresenter.strPasswordPrompt());
                String password = input0.next();
                System.out.println(organizerPresenter.strUsernameConfirmation(createSpeaker.createNewSpeaker(name, password)));
                //TODO Presenter.printUsernameIs(createSpeaker.createUser(name, password));
                break;
            }catch (SpeakerAlreadyExistException e){
                //TODO Presenter.printSpeakerExist();
            }
        }
    }

    protected void createUser(){
        CreateUserController createUser = new CreateUserController(userController);
        while(true){
            try{
                Scanner input0 = new Scanner(System.in);
                Scanner input1 = new Scanner(System.in);
                Presenter.printUserType();
                int type = input1.nextInt();
                while(true){
                    if(type >= 1 && type <= UserType.values().length){
                        Presenter.printSpeakerNamePrompt();
                        String name = input0.next();
                        createUser.ValidateName(name);
                        Presenter.printPasswordPrompt();
                        String password = input0.next();
                        Presenter.printUsernameIs(createUser.createUser(UserType.values()[type], name, password));
                        break;
                    }
                    else{
                        Presenter.printInvalid("User Type");
                    }
                }
                break;

            }catch (SpeakerAlreadyExistException e){
                System.out.println(organizerPresenter.strSpeakerExistWarning());
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
        organizerPresenter.strContinueServicePrompt();
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true")){
            enterAction = false;
        }
        return enterAction;
    }

}
