package UI;

import Controllers.CreateScheduleController;
import Controllers.UserController;
import globallyAccessible.CannotCreateActivityException;
import globallyAccessible.UserNotFoundException;
import menuPresenter.OrgAddSchedulePresenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class OrgAddScheduleUI extends UserUI{

    private final OrgAddSchedulePresenter orgAddSchedulePresenter = new OrgAddSchedulePresenter();

    public OrgAddScheduleUI(UserController userController) {
        super(userController);
    }

    @Override
    public void run() {
        CreateScheduleController createSchedule = new CreateScheduleController(userController);
        while(true){
            try{
                LocalDateTime[] targetPeriod = periodProcessor();
                Object[] speakersRooms = createSchedule.checkTimePeriodValidity(targetPeriod);
                Object[] speakerRoom = getSpeakerRoomTopic(speakersRooms, createSchedule);
                Object[] actSetting = new Object[]{targetPeriod, speakerRoom[1], speakerRoom[2], speakerRoom[0], speakerRoom[4]};
                createSchedule.newActivitySetter(actSetting);
                break;
            }catch(CannotCreateActivityException e){
                System.out.println(orgAddSchedulePresenter.strInvalidTimePeriod());
            }catch(Exception e){
                System.out.println(orgAddSchedulePresenter.strInvalidInput());
            }
        }
    }

    private Object[] getSpeakerRoomTopic(Object[] speakersRooms, CreateScheduleController createSchedule) {
        while(true){
            try{
                ArrayList<String> freeSpeaker = (ArrayList<String>) speakersRooms[1];
                ArrayList<UUID> freeRooms = (ArrayList<UUID>) speakersRooms[0];
                return InputSpeakerRoomTopic(createSchedule, freeSpeaker, freeRooms);
            }catch(UserNotFoundException e){
                System.out.println(orgAddSchedulePresenter.strInvalidSpeaker());
            }catch(IndexOutOfBoundsException e2){
                System.out.println(orgAddSchedulePresenter.strInvalidRoomIndex());
            }catch(InputMismatchException e3){
                System.out.println(orgAddSchedulePresenter.strInvalidInput());
            }catch(MaxNumberBeyondRoomCapacityException e4){
                //TODO Presenter.printInvalid("MaxNumber");
            }
        }
    }

    private Object[] InputSpeakerRoomTopic(CreateScheduleController createSchedule, ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms)
            throws UserNotFoundException, InputMismatchException, MaxNumberBeyondRoomCapacityException {
        System.out.println(orgAddSchedulePresenter.strSpeakerRoomPrompt(freeSpeaker, freeRooms));
        Scanner moreInfo = new Scanner(System.in);
        String topic = moreInfo.nextLine();
        String speaker = moreInfo.nextLine();
        int roomIndex = moreInfo.nextInt() - 1;
        int MaxNumber = moreInfo.nextInt();
        createSchedule.checkInfoValid(new String[]{speaker, freeRooms.get(roomIndex).toString()}, MaxNumber);
        return new Object[]{speaker, freeRooms.get(roomIndex), topic, MaxNumber};
    }

    private LocalDateTime[] periodProcessor(){
        Scanner start = new Scanner(System.in);
        System.out.println(orgAddSchedulePresenter.strStartTimePrompt());
        LocalDateTime startDateTime = LocalDateTime.of(start.nextInt(),
                start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt());
        Scanner end = new Scanner(System.in);
        System.out.println(orgAddSchedulePresenter.strEndTimePrompt());
        LocalDateTime endDateTime = LocalDateTime.of(end.nextInt(),
                end.nextInt(), end.nextInt(), end.nextInt(), end.nextInt());
        return new LocalDateTime[]{startDateTime, endDateTime};
    }
}
