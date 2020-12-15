package Controllers;

import gateways.*;
import globallyAccessible.CannotSerializeException;
import useCases.*;


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
    protected EventRoomManager eventRoomManager;
    protected RequestManager requestManager;

    /**
     * Creates a <code>UserController</code> instance.
     * @param manager <code>UserManager</code> object to be stored.
     */
    public UserController(UserManager manager) {
        userManager = manager;
        try {
            messageRoomManager = (MessageRoomManager) new GatewaySerialize().deserialize("chats");
            eventManager = (EventManager) new GatewaySerialize().deserialize("events");
            eventRoomManager = (EventRoomManager) new GatewaySerialize().deserialize("rooms");
            requestManager = (RequestManager) new GatewaySerialize().deserialize("requests");
        } catch (CannotSerializeException e) {
            e.printStackTrace();
        }
    }

    /**
     * return a list of all <>Managers</> this controller contains.
     * @return an instance of <>Object[]</> containing all managers stored by this controller.
     */
    public Object[] extractManagers(){
        return new Object[]{messageRoomManager, eventManager, userManager, eventRoomManager, requestManager};
    }

    /**
     * updates information and save file when <>User</> using the program is about to quit this system.
     */
    public void logout(){

        userManager.logout();
        new GatewaySerialize().serializeUser(userManager);
        new GatewaySerialize().serialize(eventRoomManager);
        new GatewaySerialize().serialize(messageRoomManager);
        new GatewaySerialize().serialize(eventManager);
        new GatewaySerialize().serialize(requestManager);
    }


}
