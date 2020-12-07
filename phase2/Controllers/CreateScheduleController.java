package Controllers;

import globallyAccessible.CannotCreateEventException;
import globallyAccessible.EventType;
import globallyAccessible.UserNotFoundException;
import globallyAccessible.MaxNumberBeyondRoomCapacityException;
import roomRequirementBuilder.roomItems;
import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateScheduleController extends EventController {
    private ArrayList<String> freeSpeaker;
    private ArrayList<UUID> freeRooms;
    private OrganizerManager organizerManager;

    public CreateScheduleController(UserController userController) {
        super(userController);
        freeSpeaker = new ArrayList<>();
        freeRooms = new ArrayList<>();
        organizerManager = new OrganizerManager(userManager);
    }

    public Object[] checkTimePeriodValidity(LocalDateTime[] targetPeriod) throws CannotCreateEventException {
        freeSpeaker = organizerManager.availableSpeakers(targetPeriod);
        freeRooms = roomManager.bookingAvailable(targetPeriod);
        if (freeRooms.size() != 0 && freeSpeaker.size() != 0){
            return new Object[]{freeRooms, freeSpeaker};
        }
        else{
            throw new CannotCreateEventException("Can't create activity");
        }
    }

    public void checkInfoValid(String room, int MaxNumber, String speaker)
            throws UserNotFoundException, IndexOutOfBoundsException, MaxNumberBeyondRoomCapacityException {
        UUID RoomID = freeRooms.get(Integer.getInteger(room));
        int RoomCapacity = roomManager.getRoomCapacity(RoomID);

        if (!freeSpeaker.contains(speaker)){
            throw new UserNotFoundException("");
        }
        if (Integer.getInteger(room) < 0 || Integer.getInteger(room) >= freeRooms.size()){
            throw new IndexOutOfBoundsException();
        }
        if (RoomCapacity < MaxNumber){
            throw new MaxNumberBeyondRoomCapacityException("");
        }

    }

    public void checkInfoValid(String room, int MaxNumber, ArrayList<String> speakers)
            throws UserNotFoundException, IndexOutOfBoundsException, MaxNumberBeyondRoomCapacityException {
        UUID RoomID = freeRooms.get(Integer.getInteger(room));
        int RoomCapacity = roomManager.getRoomCapacity(RoomID);

        for(String speaker: speakers){
            if (!freeSpeaker.contains(speaker)){
                throw new UserNotFoundException("");
            }
        }
        if (Integer.getInteger(room) < 0 || Integer.getInteger(room) >= freeRooms.size()){
            throw new IndexOutOfBoundsException();
        }
        if (RoomCapacity < MaxNumber){
            throw new MaxNumberBeyondRoomCapacityException("");
        }

    }

    public void checkInfoValid(String room, int MaxNumber)
            throws UserNotFoundException, IndexOutOfBoundsException, MaxNumberBeyondRoomCapacityException {
        UUID RoomID = freeRooms.get(Integer.getInteger(room));
        int RoomCapacity = roomManager.getRoomCapacity(RoomID);

        if (Integer.getInteger(room) < 0 || Integer.getInteger(room) >= freeRooms.size()){
            throw new IndexOutOfBoundsException();
        }
        if (RoomCapacity < MaxNumber){
            throw new MaxNumberBeyondRoomCapacityException("");
        }

    }

    public void newEventSetter(EventType type, LocalDateTime[] period, Object[] actSettings){
        UUID assignedChat = messageRoomManager.createChatroom(new ArrayList<>());
        UUID assignedRoom = (UUID) actSettings[1];
        String topic = (String) actSettings[2];
        Integer MaxNum = (Integer) actSettings[3];
        UUID actID = null;
        if(type == EventType.TALK){
            actID = talkManager.createEvent(period, new UUID[]{assignedChat, assignedRoom}, topic, MaxNum, type);
            String speaker = (String) actSettings[4];
            talkManager.addSpeaker(actID, speaker);
            organizerManager.otherAddSchedule(speaker, period, actID);
            messageRoomManager.addUser(speaker, assignedChat);
        }else if(type == EventType.PANEL){
            actID = panelManager.createEvent(period, new UUID[]{assignedChat, assignedRoom}, topic, MaxNum, type);
            ArrayList<String> speakers = (ArrayList<String>) actSettings[4];
            for(String speaker: speakers){
                panelManager.addSpeaker(actID, speaker);
                organizerManager.otherAddSchedule(speaker, period, actID);
                messageRoomManager.addUser(speaker, assignedChat);
            }
        }else if(type == EventType.PARTY){
            actID = partyManager.createEvent(period, new UUID[]{assignedChat, assignedRoom}, topic, MaxNum, type);
        }
        roomManager.BookRoom(period, actID, assignedRoom);
    }

    public List<UUID> getSuggestedRoomList(int projectorNum, int microNum, int djNum, int partyaudioNum){
        List<UUID> suggestedList = new ArrayList<>();
        for(UUID i: freeRooms){
            roomItems items = roomManager.getRoomItems(i);
            if(items.getQuantityByName("Projector") >= projectorNum &
                    items.getQuantityByName("Microphone") >= projectorNum &
                    items.getQuantityByName("DJ equipment") >= projectorNum &
                    items.getQuantityByName("Party Audio System") >= projectorNum){
                suggestedList.add(i);
            }
        }
        return suggestedList;
    }


}
