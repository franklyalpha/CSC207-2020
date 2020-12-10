package UI;

import Controllers.CreateScheduleController;
import Controllers.UserController;
import globallyAccessible.*;
import menuPresenter.ModifyEventPresenter;
import menuPresenter.OrganizerAddSchedulePresenter;

import java.time.LocalDateTime;
import java.util.*;

public class OrganizerAddScheduleUI extends AbstractUI {

    private final OrganizerAddSchedulePresenter organizerAddSchedulePresenter = new OrganizerAddSchedulePresenter();
    private CreateScheduleController createSchedule;
    private ModifyEventPresenter modifyEventPresenter;

    public OrganizerAddScheduleUI(UserController userController) {
        super(userController);
        createSchedule = new CreateScheduleController(userController);
        modifyEventPresenter = new ModifyEventPresenter();
    }

    @Override
    public void run() {
        while(true){
            try{
                majorProcessor();
                break;
            }catch(CannotCreateEventException e){
                System.out.println(organizerAddSchedulePresenter.strInvalidTimePeriod());
            }catch(Exception e){
                System.out.println(organizerAddSchedulePresenter.strInvalidInput());
            }
        }
    }

    private void majorProcessor() throws CannotCreateEventException {
        LocalDateTime[] targetPeriod = periodProcessor();
        Object[] speakersRooms = createSchedule.checkTimePeriodValidity(targetPeriod);
        Object[] speakerRoom = getSpeakerRoomTopic(speakersRooms, createSchedule);
        Object[] actSetting = new Object[]{speakerRoom[1], speakerRoom[2], speakerRoom[4], speakerRoom[3]};
        createSchedule.newEventSetter((EventType) speakerRoom[0], targetPeriod, actSetting);
    }

    private Object[] getSpeakerRoomTopic(Object[] speakersRooms, CreateScheduleController createSchedule) {
        while(true){
            try{
                ArrayList<String> freeSpeaker = (ArrayList<String>) speakersRooms[1];
                ArrayList<String[]> freeRooms = new ArrayList<>(getSuggestedRoom());
                ArrayList<UUID> freeRoomsID = extractRoomID(freeRooms);
                return inputTypeSpeakerRoomTopic(createSchedule, freeSpeaker, freeRoomsID);
            }catch(UserNotFoundException e){
                System.out.println(organizerAddSchedulePresenter.strInvalidSpeaker());
            }catch(IndexOutOfBoundsException e2){
                System.out.println(organizerAddSchedulePresenter.strInvalidRoomIndex());
            }catch(InputMismatchException e3){
                System.out.println(organizerAddSchedulePresenter.strInvalidInput());
            }catch(MaxNumberBeyondRoomCapacityException e4){
                System.out.println(organizerAddSchedulePresenter.strInvalidMaxNum());
            }catch(WrongEventTypeException e5){
                System.out.println(organizerAddSchedulePresenter.strInvalidEventType());
            }
        }
    }

    private ArrayList<UUID> extractRoomID(ArrayList<String[]> freeRooms) {
        ArrayList<UUID> freeRoomsID = new ArrayList<>();
        for (String[] roomInfo: freeRooms){
            freeRoomsID.add(UUID.fromString(roomInfo[0]));
        }
        System.out.println(modifyEventPresenter.printSuggestedRoomPrompt(freeRooms));
        return freeRoomsID;
    }

    private List<String[]> getSuggestedRoom() {
        Scanner input = new Scanner(System.in);
        System.out.println(modifyEventPresenter.askForRequirementPrompt());
        boolean hasProjector = input.nextBoolean();
        boolean hasMicrophone = input.nextBoolean();
        boolean hasPartyAudio = input.nextBoolean();
        return createSchedule.getSuggestedRoomList(hasProjector, hasMicrophone, hasPartyAudio);
    }

    private Object[] inputTypeSpeakerRoomTopic(CreateScheduleController createSchedule, ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms)
            throws UserNotFoundException, InputMismatchException, MaxNumberBeyondRoomCapacityException, WrongEventTypeException {
        Scanner moreInfo = new Scanner(System.in);
        if(freeSpeaker.size() != 0){
            Object[] roomTopicMaxEnroll = getRoomTopicMaxenroll(moreInfo);
            System.out.println(organizerAddSchedulePresenter.strTypePrompt());
            int typeNum = moreInfo.nextInt();
            return returnInfoByEventType(createSchedule, freeSpeaker, freeRooms, roomTopicMaxEnroll, typeNum);
        }
        else{
            Object[] roomTopicMaxEnroll = getRoomTopicMaxenroll(moreInfo);
            return getPartyInfo(createSchedule, freeRooms.get((Integer) roomTopicMaxEnroll[0]), roomTopicMaxEnroll);
        }
    }

    private Object[] returnInfoByEventType(CreateScheduleController createSchedule, ArrayList<String> freeSpeaker,
                                           ArrayList<UUID> freeRooms, Object[] roomTopicMaxEnroll, int typeNum)
            throws UserNotFoundException, MaxNumberBeyondRoomCapacityException, WrongEventTypeException {
        if(typeNum == 1){
            return getTalkInfo(createSchedule, freeSpeaker, freeRooms.get((Integer) roomTopicMaxEnroll[0]), roomTopicMaxEnroll);
        }else if(typeNum == 2){
            return getPanelInfo(createSchedule, freeSpeaker, freeRooms.get((Integer) roomTopicMaxEnroll[0]), roomTopicMaxEnroll);
        }else if(typeNum == 3){
            return getPartyInfo(createSchedule, freeRooms.get((Integer) roomTopicMaxEnroll[0]), roomTopicMaxEnroll);
        }else{
            throw new WrongEventTypeException("");
        }
    }

    private Object[] getPartyInfo(CreateScheduleController createSchedule, UUID uuid, Object[] roomTopicMaxEnroll)
            throws UserNotFoundException, MaxNumberBeyondRoomCapacityException {
        createSchedule.checkInfoValid(uuid.toString(), (Integer) roomTopicMaxEnroll[2],
                new ArrayList<>());
        return new Object[]{EventType.PARTY, uuid, roomTopicMaxEnroll[1],
                roomTopicMaxEnroll[2], new ArrayList<>()};
    }

    private Object[] getPanelInfo(CreateScheduleController createSchedule, ArrayList<String> freeSpeaker, UUID uuid,
                                  Object[] roomTopicMaxEnroll) throws UserNotFoundException, MaxNumberBeyondRoomCapacityException {
        System.out.println(organizerAddSchedulePresenter.strSpeakerPrompt(freeSpeaker));
        ArrayList<String> speakers = inputMultiSpeaker();
        createSchedule.checkInfoValid(uuid.toString(), (Integer)roomTopicMaxEnroll[2], speakers);
        return new Object[]{EventType.PANEL, uuid, roomTopicMaxEnroll[1],
                roomTopicMaxEnroll[2], speakers};
    }

    private Object[] getTalkInfo(CreateScheduleController createSchedule, ArrayList<String> freeSpeaker, UUID uuid,
                                 Object[] roomTopicMaxEnroll) throws UserNotFoundException, MaxNumberBeyondRoomCapacityException {
        System.out.println(organizerAddSchedulePresenter.strSpeakerPrompt(freeSpeaker));
        ArrayList<String> speaker = inputOneSpeaker();
        createSchedule.checkInfoValid(uuid.toString(),
                (Integer)roomTopicMaxEnroll[2], speaker);
        return new Object[]{EventType.TALK, uuid, roomTopicMaxEnroll[1],
                roomTopicMaxEnroll[2], speaker};
    }

    private Object[] getRoomTopicMaxenroll(Scanner moreInfo){
        System.out.println("Please input the room number of which you wish to use: (e.g. No.1, then input '1')");
        int roomIndex = moreInfo.nextInt();
        moreInfo.nextLine();
        System.out.println(organizerAddSchedulePresenter.strTopicPrompt());
        String topic = moreInfo.nextLine();
        System.out.println(organizerAddSchedulePresenter.strMaxNumPrompt());
        int MaxNumber = moreInfo.nextInt();
        moreInfo.nextLine();
        return new Object[]{roomIndex, topic, MaxNumber};
    }

    private LocalDateTime[] periodProcessor(){
        Scanner start = new Scanner(System.in);
        System.out.println(organizerAddSchedulePresenter.strStartTimePrompt());
        LocalDateTime startDateTime = LocalDateTime.of(start.nextInt(),
                start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt());
        Scanner end = new Scanner(System.in);
        System.out.println(organizerAddSchedulePresenter.strEndTimePrompt());
        LocalDateTime endDateTime = LocalDateTime.of(end.nextInt(),
                end.nextInt(), end.nextInt(), end.nextInt(), end.nextInt());
        return new LocalDateTime[]{startDateTime, endDateTime};
    }

    private ArrayList<String> inputOneSpeaker(){
        System.out.println(organizerAddSchedulePresenter.strSingleSpeakerPrompt());
        Scanner moreInfo = new Scanner(System.in);
        String newInput = moreInfo.nextLine();
        ArrayList<String> speaker = new ArrayList<>();
        speaker.add(newInput);
        return speaker;
    }

    private ArrayList<String> inputMultiSpeaker(){
        System.out.println(organizerAddSchedulePresenter.strMultiSpeakerPrompt());
        Scanner moreInfo = new Scanner(System.in);
        String newInput = moreInfo.nextLine();
        ArrayList<String> speakerList = new ArrayList<>();
        while(!newInput.equals("end")){
            speakerList.add(newInput);
            newInput = moreInfo.nextLine();
        }
        return speakerList;
    }

}
