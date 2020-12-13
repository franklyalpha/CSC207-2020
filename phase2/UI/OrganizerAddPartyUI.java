package UI;

import Controllers.CreateScheduleController;
import Controllers.UserController;
import globallyAccessible.*;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class OrganizerAddPartyUI extends OrganizerAddScheduleUI{
    /**
     * The constructor for this UI.
     *
     * @param userController is an instance of UserController.
     */
    public OrganizerAddPartyUI(UserController userController) {
        super(userController);
    }

    public OrganizerAddPartyUI(UserController userController, Object[] speakersRooms, CreateScheduleController controller) {
        super(userController);
        this.freeSpeakers = (ArrayList<String>) speakersRooms[0];
        this.freeRooms = (ArrayList<String[]>) speakersRooms[1];
        this.freeRoomsID = extractRoomID(freeRooms);
        this.createSchedule = controller;
    }

    @Override
    public void run() throws ExceedingMaxAttemptException {
        Object[] speakerRoom = getRoomTopic();
        Object[] actSetting = new Object[]{speakerRoom[0], speakerRoom[1], new ArrayList<>(), speakerRoom[2]};
        createSchedule.newEventSetter(EventType.PARTY, actSetting);
    }

    private Object[] getRoomTopic() throws ExceedingMaxAttemptException {
        for(int i = 0; i < 3; i ++){
            try{
                Object[] info = inputRoomTopic();
                checkPartyInfo(info);
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
        throw new ExceedingMaxAttemptException("No more attempts");
    }

    private void checkPartyInfo(Object[] roomTopicMaxEnroll)
            throws UserNotFoundException, MaxNumberBeyondRoomCapacityException {
        createSchedule.checkInfoValid(roomTopicMaxEnroll[0].toString(), (Integer) roomTopicMaxEnroll[2],
                (ArrayList<String>) roomTopicMaxEnroll[3]);
    }
}
