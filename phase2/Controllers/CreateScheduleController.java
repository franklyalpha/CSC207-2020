package Controllers;

import globallyAccessible.CannotCreateEventException;
import globallyAccessible.EventType;
import globallyAccessible.UserNotFoundException;
import globallyAccessible.MaxNumberBeyondRoomCapacityException;
import roomRequirementBuilder.RoomItems;
import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * The controller responsible for creating a new event.
 */
public class CreateScheduleController extends EventController {

    /**
     * An <>ArrayList</> containing username of speakers who are free during certain time period.
     */
    private ArrayList<String> freeSpeaker;

    /**
     * An <>ArrayList</> containing <>UUID</> of rooms which are free during certain time period.
     */
    private ArrayList<UUID> freeRooms;


    /**
     * An instance of <>OrganizerManager</>.
     */
    private OrganizerManager organizerManager;

    /**
     * The constructor of this controller.
     * @param userController An instance of UserController.
     */
    public CreateScheduleController(UserController userController) {
        super(userController);
        freeSpeaker = new ArrayList<>();
        freeRooms = new ArrayList<>();
        organizerManager = new OrganizerManager(userManager);
    }


    /**
     * Checks whether given time period has free speaker and free rooms. If it is the case then return an array of
     * speakers and rooms available during the period.
     * @param targetPeriod: an array of length 2 of <>LocalDateTime</> representing event start time and end time.
     * @return an array containing list of free speakers' username (can be casted to <>String</>) and list of free room's
     * ID (can be casted to <>UUID</>).
     * @throws CannotCreateEventException: when there is no room or speakers free during given period,
     * the exception will be thrown.
     */
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

    /**
     * Checks whether the given room and speaker are free, and whether the room's capacity is above the given maximum
     * attendant number.
     * @param room: the potential index of a room in <>freeRooms</>.
     * @param MaxNumber: the maximum number of attendee can enroll in potential event.
     * @param speakers: the list of usernames of speakers.
     * @throws UserNotFoundException: this exception is thrown when the speaker with this username is not free.
     * @throws IndexOutOfBoundsException: is thrown when the index <>room</> is not a valid index for <>freeRooms</>.
     * @throws MaxNumberBeyondRoomCapacityException: is thrown when <>MaxNumber</> is beyond given room's capacity.
     */
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


    /**
     * Sets the new event with all given information.
     * @param type: the type of this event.
     * @param period: the time period this event happens.
     * @param actSettings: all the information for setting an event, including: the type of event, the UUID of assigned room,
     *                   the topic in String, the maximum attendee can participate and speakers.
     */
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

    // this method will be deleted after not allowing user to input number of microphones and so on.
    public List<UUID> getSuggestedRoomList(int projectorNum, int microNum, int djNum, int partyaudioNum){
        List<UUID> suggestedList = new ArrayList<>();
        for(UUID i: freeRooms){
            RoomItems items = roomManager.getRoomItems(i);
            if(items.getQuantityByName("Projector") >= projectorNum &
                    items.getQuantityByName("Microphone") >= microNum &
                    items.getQuantityByName("DJ equipment") >= djNum &
                    items.getQuantityByName("Party Audio System") >= partyaudioNum){
                suggestedList.add(i);
            }
        }
        return suggestedList;
    }


}
