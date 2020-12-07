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
        Object[] actSetting = new Object[]{targetPeriod, speakerRoom[1], speakerRoom[2], speakerRoom[0], speakerRoom[4]};
        createSchedule.newEventSetter((EventType) speakerRoom[0], targetPeriod, speakerRoom);
    }

    private Object[] getSpeakerRoomTopic(Object[] speakersRooms, CreateScheduleController createSchedule) {
        while(true){
            try{
                ArrayList<String> freeSpeaker = (ArrayList<String>) speakersRooms[1];
                ArrayList<UUID> freeRooms = (ArrayList<UUID>) speakersRooms[0];
                modifyEventPresenter.printSuggestedRoomPrompt(getSuggestedRoom());
                return inputTypeSpeakerRoomTopic(createSchedule, freeSpeaker, freeRooms);
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

    private List<UUID> getSuggestedRoom() {
        Scanner input = new Scanner(System.in);
        System.out.println(modifyEventPresenter.askForRequirementPrompt());
        int projectorNum = input.nextInt();
        int microNum = input.nextInt();
        int djNum = input.nextInt();
        int partyaudioNum = input.nextInt();
        return createSchedule.getSuggestedRoomList(projectorNum, microNum, djNum, partyaudioNum);
    }

    private Object[] inputTypeSpeakerRoomTopic(CreateScheduleController createSchedule, ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms)
            throws UserNotFoundException, InputMismatchException, MaxNumberBeyondRoomCapacityException, WrongEventTypeException {
        Scanner moreInfo = new Scanner(System.in);
        System.out.println(organizerAddSchedulePresenter.strTypePrompt());
        int typeNum = moreInfo.nextInt();
        System.out.println(organizerAddSchedulePresenter.strTopicPrompt());
        String topic = moreInfo.nextLine();
        System.out.println(organizerAddSchedulePresenter.strRoomPrompt(freeRooms));
        int roomIndex = moreInfo.nextInt();
        System.out.println(organizerAddSchedulePresenter.strMaxNumPrompt());
        int MaxNumber = moreInfo.nextInt();
        if(typeNum == 1){
            System.out.println(organizerAddSchedulePresenter.strSpeakerPrompt(freeSpeaker));
            String speaker = inputOneSpeaker();
            createSchedule.checkInfoValid(freeRooms.get(roomIndex).toString(), MaxNumber, speaker);
            return new Object[]{EventType.TALK, freeRooms.get(roomIndex), topic, MaxNumber, speaker};
        }else if(typeNum == 2){
            System.out.println(organizerAddSchedulePresenter.strSpeakerPrompt(freeSpeaker));
            ArrayList<String> speakers = inputMultiSpeaker();
            createSchedule.checkInfoValid(freeRooms.get(roomIndex).toString(), MaxNumber, speakers);
            return new Object[]{EventType.PANEL, freeRooms.get(roomIndex), topic, MaxNumber, speakers};
        }else if(typeNum == 3){
            createSchedule.checkInfoValid(freeRooms.get(roomIndex).toString(), MaxNumber);
            return new Object[]{EventType.PARTY, freeRooms.get(roomIndex), topic, MaxNumber};
        }else{
            throw new WrongEventTypeException("");
        }
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

    private String inputOneSpeaker(){
        System.out.println(organizerAddSchedulePresenter.strSingleSpeakerPrompt());
        Scanner moreInfo = new Scanner(System.in);
        String newInput = moreInfo.nextLine();;
        return newInput;
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
