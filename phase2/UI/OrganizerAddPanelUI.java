package UI;

import Controllers.CreateScheduleController;
import Controllers.UserController;
import globallyAccessible.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganizerAddPanelUI extends OrganizerAddScheduleUI{
    /**
     * The constructor for this UI.
     *
     * @param userController is an instance of UserController.
     */
    public OrganizerAddPanelUI(UserController userController, Object[] speakersRooms, CreateScheduleController controller) {
        super(userController);
        this.freeSpeakers = (ArrayList<String>) speakersRooms[0];
        this.freeRooms = (ArrayList<String[]>) speakersRooms[1];
        this.freeRoomsID = extractRoomID(freeRooms);
        this.createSchedule = controller;
    }

    public OrganizerAddPanelUI(UserController userController){
        super(userController);
    }

    public void run() throws ExceedingMaxAttemptException {
        Object[] speakerRoom = getSpeakerRoomTopic();
        Object[] actSetting = new Object[]{speakerRoom[0], speakerRoom[1], speakerRoom[3], speakerRoom[2]};
        createSchedule.newEventSetter(EventType.PANEL, actSetting);
    }

    private Object[] getSpeakerRoomTopic() throws ExceedingMaxAttemptException {
        for(int i = 0; i < 3; i ++){
            try{
                Object[] info = inputRoomTopic();
                info[3] = inputMultiSpeaker();
                checkPanelInfo(info);
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
        throw new ExceedingMaxAttemptException("no more attempts allowed!");
    }

    private void checkPanelInfo(Object[] roomTopicMaxEnroll)
            throws UserNotFoundException, MaxNumberBeyondRoomCapacityException {
        createSchedule.checkInfoValid(roomTopicMaxEnroll[0].toString(), (Integer)roomTopicMaxEnroll[2],
                (ArrayList<String>) roomTopicMaxEnroll[3]);
    }

    private ArrayList<String> inputMultiSpeaker(){
        System.out.println(organizerAddSchedulePresenter.strSpeakerPrompt(freeSpeakers));
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
