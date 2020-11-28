package Controllers;

import globallyAccessible.CannotCreateActivityException;
import globallyAccessible.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class CreateScheduleController extends ActivityController {
    private ArrayList<String> freeSpeaker;
    private ArrayList<UUID> freeRooms;

    public CreateScheduleController(UserController userController) {
        super(userController);
        freeSpeaker = new ArrayList<>();
        freeRooms = new ArrayList<>();
    }

    public Object[] checkTimePeriodValidity(LocalDateTime[] targetPeriod) throws CannotCreateActivityException {
        freeSpeaker = userManager.availableSpeakers(targetPeriod);
        freeRooms = roomManager.bookingAvailable(targetPeriod);
        if (freeRooms.size() != 0 && freeSpeaker.size() != 0){
            return new Object[]{freeRooms, freeSpeaker};
        }
        else{
            throw new CannotCreateActivityException("Can't create activity");
        }
    }

    public void checkInfoValid(String[] speakerRoom)
            throws UserNotFoundException, IndexOutOfBoundsException {
        if (!freeSpeaker.contains(speakerRoom[0])){
            throw new UserNotFoundException("");
        }
        if (Integer.getInteger(speakerRoom[1]) < 0 || Integer.getInteger(speakerRoom[1]) >= freeRooms.size()){
            throw new IndexOutOfBoundsException();
        }
    }

    public void newActivitySetter(Object[] actSettings){
        UUID assignedChat = messageRoomManager.createChatroom(new ArrayList<>());
        LocalDateTime[] targetPeriod = (LocalDateTime[]) actSettings[0];
        UUID assignedRoom = (UUID) actSettings[1];
        String topic = (String) actSettings[2];
        String speaker = (String) actSettings[3];
        UUID actID = activityManager.addNewActivity(targetPeriod, new UUID[]{assignedChat, assignedRoom}, topic);
        activityManager.addSpeaker(actID, speaker);
        roomManager.BookRoom(targetPeriod, actID, assignedRoom);
        userManager.otherAddSchedule(speaker, targetPeriod, actID);
        messageRoomManager.addUser(speaker, assignedChat);
    }


}
