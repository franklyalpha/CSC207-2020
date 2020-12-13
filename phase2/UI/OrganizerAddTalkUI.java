package UI;

import Controllers.CreateScheduleController;
import Controllers.UserController;
import globallyAccessible.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class OrganizerAddTalkUI extends OrganizerAddScheduleUI{
    /**
     * The constructor for this UI.
     *
     * @param userController is an instance of UserController.
     */
    public OrganizerAddTalkUI(UserController userController) {
        super(userController);
    }

    public OrganizerAddTalkUI(UserController userController, Object[] speakersRooms, CreateScheduleController controller) {
        super(userController);
        this.freeSpeakers = (ArrayList<String>) speakersRooms[0];
        this.freeRooms = (ArrayList<String[]>) speakersRooms[1];
        this.freeRoomsID = extractRoomID(freeRooms);
        this.createSchedule = controller;
    }

    public void run() throws ExceedingMaxAttemptException {
        Object[] speakerRoom = getSpeakerRoomTopic();
        Object[] actSetting = new Object[]{speakerRoom[0], speakerRoom[1], speakerRoom[3], speakerRoom[2]};
        createSchedule.newEventSetter(EventType.TALK, actSetting);
    }

    private Object[] getSpeakerRoomTopic() throws ExceedingMaxAttemptException {
        for(int i = 0; i < 3; i ++){
            try{
                Object[] info = inputRoomTopic();
                info[3] = inputOneSpeaker();
                checkTalkInfo(info);
                return info;
            }catch(UserNotFoundException e){
                System.out.println(organizerAddSchedulePresenter.strInvalidSpeaker());
            }catch(IndexOutOfBoundsException e2){
                System.out.println(organizerAddSchedulePresenter.strInvalidRoomIndex());
            }catch(InputMismatchException e3){
                System.out.println(organizerAddSchedulePresenter.strInvalidInput());
            }catch(MaxNumberBeyondRoomCapacityException e4){
                System.out.println(organizerAddSchedulePresenter.strInvalidMaxNum());
            }
        }
        throw new ExceedingMaxAttemptException("max attempts reached!!!");
    }

    private ArrayList<String> inputOneSpeaker(){
        System.out.println(organizerAddSchedulePresenter.strSpeakerPrompt(freeSpeakers));
        System.out.println(organizerAddSchedulePresenter.strSingleSpeakerPrompt());
        Scanner moreInfo = new Scanner(System.in);
        String newInput = moreInfo.nextLine();
        ArrayList<String> speaker = new ArrayList<>();
        speaker.add(newInput);
        return speaker;
    }

    private void checkTalkInfo(Object[] roomTopicMaxEnroll)
            throws UserNotFoundException, MaxNumberBeyondRoomCapacityException {
        createSchedule.checkInfoValid(roomTopicMaxEnroll[0].toString(),
                (Integer)roomTopicMaxEnroll[2], (ArrayList<String>) roomTopicMaxEnroll[3]);
    }
}
