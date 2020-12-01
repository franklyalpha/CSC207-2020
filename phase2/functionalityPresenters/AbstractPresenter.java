package functionalityPresenters;

import Controllers.UserController;
import useCases.ActivityManager;
import useCases.MessageRoomManager;
import useCases.RoomManager;
import useCases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AbstractPresenter {
    protected RoomManager roomManager;
    protected ActivityManager activityManager;
    protected UserManager userManager;
    protected MessageRoomManager messageRoomManager;


    public AbstractPresenter(UserController userController){
        Object[] managers = userController.extractManagers();
        roomManager = (RoomManager) managers[3];
        activityManager = (ActivityManager) managers[1];
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
