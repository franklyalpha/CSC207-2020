package UserControllers;

import gateways.GatewayActivity;
import gateways.GatewayChat;
import gateways.GatewayRoom;
import gateways.GatewayUser;
import presenter.Presenter;
import useCases.ActivityManager;
import useCases.MessageRoomManager;
import useCases.RoomManager;
import useCases.UserManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

//public abstract class controllers.UserController

/**
 * Controller for <code>User</code>-related functions, calling the appropriate methods.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class UserController {
    protected UserManager userManager;
    protected MessageRoomManager messageRoomManager;
    protected ActivityManager activityManager;
    protected RoomManager roomManager;

    /**
     * Creates a <code>UserController</code> with a new chatroomManager, activityManager, and roomManager, and specified userManager.
     * @param manager <code>UserManager</code> object to be stored.
     */
    public UserController(UserManager manager) {
        userManager = manager;
        messageRoomManager = new GatewayChat().deserialize();
        activityManager = new GatewayActivity().deserialize();
        roomManager = new GatewayRoom().deserialize();
    }

    /**
     * Abstract run method to be overridden.
     */
    public void run(){}

    /**
     * Displays the user's private messages.
     */
    protected void viewPrivateMessage(){
        // may add particular user for viewing;
        // should call presenter to display; but will acquire data here;


    }



    /**
     * Displays the user's group messages.
     */
    protected void viewGroupMessage(){


    }

    /**
     * Prints the group messages from the specified activity, if it is valid and exists.
     * @param historyChat ArrayList of Strings representing the user's group chats.
     */


    /**
     * Asks the user to input the username of the person they want to contact. If valid, asks the user to input the message
     * they want to send, then sends it.
     */
    protected void sendPrivateMessage() {



    }



    /**
     * Creates a new private <code>Chatroom</code> with the current user and the user specified by <code>userName</code>
     * as the participants.
     * @param userName String representing the username of the other participant of this new <code>Chatroom</code>.
     * @return Returns the newly created <code>Chatroom</code> object.
     */


    /**
     * Outputs a schedule of all events the user is registered for.
     * @return  Returns an ArrayList of Strings representing the events that this user is enrolled in.
     */
    protected ArrayList<String[]> viewEnrolledSchedule(){
        HashMap<LocalDateTime[], UUID> schedules = userManager.schedules();
        ArrayList<String[]> allSchedule = new ArrayList<>();
        for (LocalDateTime[] time : schedules.keySet()){
            String[] partialInfo = activityManager.searchActivityByUUID(schedules.get(time).toString());
            allSchedule.add(partialInfo);
        }
        Presenter.printSchedule(allSchedule);
        return allSchedule;
    }

    /**
     * Helper function for getting the UUIDs of available activities.
     * @param available ArrayList of Strings representing all available activities.
     * @return Returns an ArrayList of Strings representing the UUIDs of all available activities.
     */
    protected ArrayList<String> extractActIDHelper (ArrayList<String[]> available){
        ArrayList<String> actIDs = new ArrayList<>();
        for (String[] schedule: available){
            actIDs.add(schedule[0]);
        }
        return actIDs;
    }

    /**
     * Helper function for formatting user-inputted times.
     * @param scheduleInfo String representing user-inputted time information, to be formatted.
     * @return Returns the formatted time information in LocalDateTime format.
     */
    protected LocalDateTime[] getTimeHelper(String[] scheduleInfo){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime[] time = {LocalDateTime.parse(scheduleInfo[2], df),
                LocalDateTime.parse(scheduleInfo[3], df)};
        return time;
    }

    /**
     * Logs the current user out of the system.
     */
    protected void logout(){
        userManager.logout();
        new GatewayUser().ser(userManager);
        new GatewayRoom().ser(roomManager);
        new GatewayChat().ser(messageRoomManager);
        new GatewayActivity().ser(activityManager);
    }




}
