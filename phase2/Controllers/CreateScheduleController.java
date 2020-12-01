package Controllers;

import globallyAccessible.CannotCreateActivityException;
import globallyAccessible.UserNotFoundException;
import globallyAccessible.MaxNumberBeyondRoomCapacityException;

import useCases.RoomManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
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

    public void checkInfoValid(String[] speakerRoom, int MaxNumber)
            throws UserNotFoundException, IndexOutOfBoundsException, MaxNumberBeyondRoomCapacityException {
        UUID RoomID = freeRooms.get(Integer.getInteger(speakerRoom[1]));
        int RoomCapacity = roomManager.getRoomCapacity(RoomID);

        if (!freeSpeaker.contains(speakerRoom[0])){
            throw new UserNotFoundException("");
        }
        if (Integer.getInteger(speakerRoom[1]) < 0 || Integer.getInteger(speakerRoom[1]) >= freeRooms.size()){
            throw new IndexOutOfBoundsException();
        }
        if (RoomCapacity < MaxNumber){
            throw new MaxNumberBeyondRoomCapacityException("");
        }

    }


    public void newActivitySetter(Object[] actSettings){
        UUID assignedChat = messageRoomManager.createChatroom(new ArrayList<>());
        LocalDateTime[] targetPeriod = (LocalDateTime[]) actSettings[0];
        UUID assignedRoom = (UUID) actSettings[1];
        String topic = (String) actSettings[2];
        String speaker = (String) actSettings[3];
        Integer MaxNum = (Integer) actSettings[4];
        UUID actID = activityManager.addNewActivity(targetPeriod, new UUID[]{assignedChat, assignedRoom}, topic, MaxNum);
        activityManager.addSpeaker(actID, speaker);
        roomManager.BookRoom(targetPeriod, actID, assignedRoom);
        userManager.otherAddSchedule(speaker, targetPeriod, actID);
        messageRoomManager.addUser(speaker, assignedChat);
    }


}
