package Controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import gateways.GatewayEvent;
import gateways.GatewayChat;
import gateways.GatewayRoom;
import gateways.GatewayUser;
import gateways.GatewayRequest;
import useCases.EventManager;
import useCases.MessageRoomManager;
import useCases.RoomManager;
import useCases.UserManager;
import useCases.RequestManager;
import gateways.Mongodb;

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
        messageRoomManager = new GatewayChat().deserialize();
        eventManager = new GatewayEvent().deserialize();
        roomManager = new GatewayRoom().deserialize();
        requestManager = new GatewayRequest().deserialize();
    }

    /**
     * Abstract run method to be overridden.
     */

    public Object[] extractManagers(){
        return new Object[]{messageRoomManager, eventManager, userManager, roomManager, requestManager};
    }

//    public void logout(MongoDatabase database){
    public void logout(){
        userManager.logout();
//        new GatewayUser().ser(userManager, database);
        new GatewayUser().ser(userManager);
        new GatewayRoom().ser(roomManager);
        new GatewayChat().ser(messageRoomManager);
        new GatewayEvent().ser(eventManager);
        new GatewayRequest().ser(requestManager);
    }


}
