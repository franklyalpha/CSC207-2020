package Controllers;

import globallyAccessible.CannotCreateEventException;
import globallyAccessible.EventType;
import globallyAccessible.MaxNumberBeyondRoomCapacityException;
import globallyAccessible.UserNotFoundException;
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
     * An <>ArrayList</> containing ID and info of rooms which are free during certain time period.
     */
    private ArrayList<String[]> freeRooms;


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
     * @return an array containing a list of free room's
     * ID (can be casted to <>UUID</>) and a list of free speakers' username (can be casted to <>String</>)
     * @throws CannotCreateEventException: when there is no room or speakers free during given period,
     * the exception will be thrown.
     */
    public Object[] checkTimePeriodValidity(LocalDateTime[] targetPeriod) throws CannotCreateEventException {
        freeSpeaker = organizerManager.availableSpeakers(targetPeriod);
        freeRooms = roomManager.bookingAvailable(targetPeriod);
        if (freeRooms.size() != 0 && freeSpeaker.size() != 0){
            return new Object[]{freeRooms, freeSpeaker};
        }
        else if(freeRooms.size() != 0){
            return new Object[]{freeRooms, new ArrayList<>()};
        }
        else{
            throw new CannotCreateEventException("Can't create activity");
        }
    }

    /**
     * Checks whether the given room and speaker are free, and whether the room's capacity is above the given maximum
     * attendant number.
     * @param room: the <>UUID</> in <>String</> of potential event's room.
     * @param MaxNumber: the maximum number of attendee can enroll in potential event.
     * @param speakers: the list of usernames of speakers.
     * @throws UserNotFoundException: this exception is thrown when the speaker with this username is not free.
     * @throws IndexOutOfBoundsException: is thrown when the index <>room</> is not a valid index for <>freeRooms</>.
     * @throws MaxNumberBeyondRoomCapacityException: is thrown when <>MaxNumber</> is beyond given room's capacity.
     */
    public void checkInfoValid(String room, int MaxNumber, ArrayList<String> speakers)
            throws UserNotFoundException, IndexOutOfBoundsException, MaxNumberBeyondRoomCapacityException {
        UUID RoomID = UUID.fromString(room);
        int RoomCapacity = roomManager.getRoomCapacity(RoomID);

        for(String speaker: speakers){
            if (!freeSpeaker.contains(speaker)){
                throw new UserNotFoundException("");
            }
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
     *                   the topic in String, the maximum attendee can participate and list of speakers.
     */
    public void newEventSetter(EventType type, LocalDateTime[] period, Object[] actSettings){
        UUID assignedChat = messageRoomManager.createChatroom(new ArrayList<>());
        UUID assignedRoom = (UUID) actSettings[0];
        String topic = (String) actSettings[1];
        Integer MaxNum = (Integer) actSettings[3];
        UUID actID = null;
        if(type == EventType.TALK){
            actID = talkSetter(type, period, actSettings[2], assignedChat, assignedRoom, topic, MaxNum);
        }else if(type == EventType.PANEL){
            actID = panelSetter(type, period, actSettings[2], assignedChat, assignedRoom, topic, MaxNum);
        }else if(type == EventType.PARTY){
            actID = partyManager.createEvent(period, new UUID[]{assignedChat, assignedRoom}, topic, MaxNum, type);
        }
        roomManager.BookRoom(period, actID, assignedRoom);
    }

    private UUID panelSetter(EventType type, LocalDateTime[] period, Object actSetting, UUID assignedChat,
                             UUID assignedRoom, String topic, Integer maxNum) {
        UUID actID;
        actID = panelManager.createEvent(period, new UUID[]{assignedChat, assignedRoom}, topic, maxNum, type);
        ArrayList<String> speakers = (ArrayList<String>) actSetting;
        for(String speaker: speakers){
            panelManager.addSpeaker(actID, speaker);
            organizerManager.otherAddSchedule(speaker, period, actID);
            messageRoomManager.addUser(speaker, assignedChat);
        }
        return actID;
    }

    private UUID talkSetter(EventType type, LocalDateTime[] period, Object actSetting, UUID assignedChat,
                            UUID assignedRoom, String topic, Integer maxNum) {
        UUID actID;
        actID = talkManager.createEvent(period, new UUID[]{assignedChat, assignedRoom}, topic, maxNum, type);
        ArrayList<String> speakers = (ArrayList<String>) actSetting;
        talkManager.addSpeaker(actID, speakers.get(0));
        organizerManager.otherAddSchedule(speakers.get(0), period, actID);
        messageRoomManager.addUser(speakers.get(0), assignedChat);
        return actID;
    }


    /**
     * return a list of possible room's info fitting technical requirements.
     * @param hasProjector a Boolean value representing whether a projector is required for the event.
     * @param hasMicrophone a Boolean representing whether microphone is required for the event.
     * @param hasPartyAudio a Boolean representing whether Party Audio equipments are required for the event.
     * @return An instance of <>List<String[]></> containing room's id and info of those satisfying technical constraint.
     */
    public List<String[]> getSuggestedRoomList(boolean hasProjector, boolean hasMicrophone, boolean hasPartyAudio){
        List<String[]> suggestedList = new ArrayList<>();
        for(String[] i: freeRooms){
            UUID roomID = UUID.fromString(i[0]);
            if (roomManager.checkRoomItems(roomID, new Boolean[]{hasProjector, hasMicrophone, hasPartyAudio})){
                suggestedList.add(i);
            }
        }
        return suggestedList;
    }


}
