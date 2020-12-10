package Controllers;

import gateways.*;
import globallyAccessible.CannotSerializeException;
import useCases.*;

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
    protected EventManager eventManager;
    protected RoomManager roomManager;
    protected RequestManager requestManager;

    /**
     * Creates a <code>UserController</code> with a new chatroomManager, activityManager, and roomManager, and specified userManager.
     * @param manager <code>UserManager</code> object to be stored.
     */

    public UserController(UserManager manager) {
        userManager = manager;
        try {
            messageRoomManager = (MessageRoomManager) new GatewaySerialize().deserialize("chats");
            eventManager = (EventManager) new GatewaySerialize().deserialize("events");
            roomManager = (RoomManager) new GatewaySerialize().deserialize("rooms");
            requestManager = (RequestManager) new GatewaySerialize().deserialize("requests");
        } catch (CannotSerializeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abstract run method to be overridden.
     */

    public Object[] extractManagers(){
        return new Object[]{messageRoomManager, eventManager, userManager, roomManager, requestManager};
    }

    public void logout(){

        userManager.logout();
        new GatewaySerialize().serializeUser(userManager);

        new GatewaySerialize().serialize(roomManager);
        new GatewaySerialize().serialize(messageRoomManager);
        new GatewaySerialize().serialize(eventManager);
        new GatewaySerialize().serialize(requestManager);
    }


}
