package functionalityPresenters;

import Controllers.UserController;
import useCases.EventManager;
import useCases.MessageRoomManager;
import useCases.EventRoomManager;
import useCases.UserManager;
//import useCases.RequestManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * an abstract presenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class AbstractPresenter {
    protected EventRoomManager eventRoomManager;
    protected EventManager eventManager;
    protected UserManager userManager;
    protected MessageRoomManager messageRoomManager;

    /**
     * Creates an abstract presenter
     * @param userController the UserController that is going to contain the Managers
     */
    public AbstractPresenter(UserController userController){
        Object[] managers = userController.extractManagers();
        eventRoomManager = (EventRoomManager) managers[3];
        eventManager = (EventManager) managers[1];
        userManager = (UserManager) managers[2];
        messageRoomManager = (MessageRoomManager) managers[0];
    }

    /**
     * Helper to get time
     * @param scheduleInfo the time info that need to be parsed
     * @return the time list but in the new time format
     */
    protected LocalDateTime[] getTimeHelper(String[] scheduleInfo){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return new LocalDateTime[]{LocalDateTime.parse(scheduleInfo[2], df),
                LocalDateTime.parse(scheduleInfo[3], df)};
    }
}
