package UI;

import Controllers.CreateScheduleController;
import Controllers.UserController;
import Presenters.Presenter;
import globallyAccessible.CannotCreateActivityException;
import globallyAccessible.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class OrgAddScheduleUI extends UserUI{
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
                Object[] actSetting = new Object[]{targetPeriod, speakerRoom[1], speakerRoom[2], speakerRoom[0]};
                createSchedule.newActivitySetter(actSetting);
                break;
            }catch(CannotCreateActivityException e){
                Presenter.printInvalid("time period");
            }catch(Exception e){
                Presenter.printInvalid("input");
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
                Presenter.printInvalid("speaker");
            }catch(IndexOutOfBoundsException e2){
                Presenter.printInvalid("room index");
            }
        }
    }

    private Object[] InputSpeakerRoomTopic(CreateScheduleController createSchedule, ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms)
            throws UserNotFoundException {
        Presenter.printSpeakerRoomPrompt(freeSpeaker, freeRooms);
        Scanner moreInfo = new Scanner(System.in);
        String topic = moreInfo.nextLine();
        String speaker = moreInfo.nextLine();
        int roomIndex = moreInfo.nextInt() - 1;
        createSchedule.checkInfoValid(new String[]{speaker, freeRooms.get(roomIndex).toString()});
        return new Object[]{speaker, freeRooms.get(roomIndex), topic};
    }

    private LocalDateTime[] periodProcessor(){
        Scanner start = new Scanner(System.in);
        Presenter.printTimePrompt("start");
        LocalDateTime startDateTime = LocalDateTime.of(start.nextInt(),
                start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt());
        Scanner end = new Scanner(System.in);
        Presenter.printTimePrompt("end");
        LocalDateTime endDateTime = LocalDateTime.of(end.nextInt(),
                end.nextInt(), end.nextInt(), end.nextInt(), end.nextInt());
        return new LocalDateTime[]{startDateTime, endDateTime};
    }
}
