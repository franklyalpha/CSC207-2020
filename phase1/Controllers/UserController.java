package Controllers;

import gateways.GatewayActivity;
import useCases.ActivityManager;
import useCases.MessageRoomManager;
import useCases.RoomManager;
import useCases.UserManager;

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

    public Object[] extractManagers(){
        return new Object[]{messageRoomManager, activityManager, userManager, roomManager};
    }

    public void logout(){
        userManager.logout();
        new GatewayUser().ser(userManager);
        new GatewayRoom().ser(roomManager);
        new GatewayChat().ser(messageRoomManager);
        new GatewayActivity().ser(activityManager);
    }




}
