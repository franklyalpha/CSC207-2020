package Controllers;

import useCases.*;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is an abstract controller which will be extended for all other controllers.
 */
public class AbstractController {

    /**
     * an instance of <>RoomManager</>.
     */
    protected RoomManager roomManager;

    /**
     * an instance of <>EventManager</>.
     */
    protected EventManager eventManager;
    protected PanelManager panelManager;
    protected TalkManager talkManager;
    protected PartyManager partyManager;

    /**
     * an instance of <>UserManager</>.
     */
    protected UserManager userManager;


    /**
     * an instance of <>MessageRoomManager</>.
     */
    protected MessageRoomManager messageRoomManager;


    /**
     * an instance of <>RequestManager</>.
     */
    protected RequestManager requestManager;


    /**
     * Instantiates new <code>AbstractController</code>.
     * @param userController: An instance of user controller.
     */
    public AbstractController(UserController userController){
        Object[] managers = userController.extractManagers();
        requestManager = (RequestManager) managers[4];
        roomManager = (RoomManager) managers[3];
        eventManager = (EventManager) managers[1];
        userManager = (UserManager) managers[2];
        messageRoomManager = (MessageRoomManager) managers[0];
        panelManager = new PanelManager(eventManager);
        talkManager = new TalkManager(eventManager);
        partyManager = new PartyManager(eventManager);

    }

    /**
     * Extracts the time period of given activity information and convert to the format of
     * which computer stores and uses.
     *
     * @param scheduleInfo: An array of String with length 6, describing all relevant information of an <code>Event</code>,
     *                    where the third and fourth elements are start time and end time of <code>Event</code>.
     * @return an array of length two with first element the start time of given event and second the end time of event;
     * both with type <code>LocalDateTime</code>.
     */
    protected LocalDateTime[] getTimeHelper(String[] scheduleInfo){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime[] time = {LocalDateTime.parse(scheduleInfo[2], df),
                LocalDateTime.parse(scheduleInfo[3], df)};

        return time;
    }

}
