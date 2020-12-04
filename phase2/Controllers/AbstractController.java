package Controllers;

import useCases.EventManager;
import useCases.MessageRoomManager;
import useCases.RoomManager;
import useCases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AbstractController {

    protected RoomManager roomManager;
    protected EventManager eventManager;
    protected UserManager userManager;
    protected MessageRoomManager messageRoomManager;

    public AbstractController(UserController userController){
        Object[] managers = userController.extractManagers();
        roomManager = (RoomManager) managers[3];
        eventManager = (EventManager) managers[1];
        userManager = (UserManager) managers[2];
        messageRoomManager = (MessageRoomManager) managers[0];
    }

    protected LocalDateTime[] getTimeHelper(String[] scheduleInfo){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime[] time = {LocalDateTime.parse(scheduleInfo[2], df),
                LocalDateTime.parse(scheduleInfo[3], df)};
        return time;
    }

}
