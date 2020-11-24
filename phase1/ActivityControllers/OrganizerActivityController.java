package ActivityControllers;

import globalConstants.CannotCreateActivityException;
import globalConstants.UserNotFoundException;
import presenter.Presenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class OrganizerActivityController extends AdministrativeActivityCOntroller implements IOrganizerActivity{

    public OrganizerActivityController(Object[] managers) {
        super(managers);
    }

    @Override
    public void addSchedule() {
        while(true){
            try{
                LocalDateTime[] targetPeriod = periodProcessor();
                ArrayList<String> freeSpeaker = userManager.availableSpeakers(targetPeriod);
                ArrayList<UUID> freeRooms = roomManager.bookingAvailable(targetPeriod);
                if (freeRooms.size() != 0 && freeSpeaker.size() != 0){
                    activityCreator(freeSpeaker, freeRooms, targetPeriod);
                    break;
                }
                else{
                    throw new CannotCreateActivityException("Can't create activity");
                }
            }catch(CannotCreateActivityException e){
                Presenter.printInvalid("time period");
            }
        }
    }

    private void activityCreator (ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms, LocalDateTime[] targetPeriod) {
        while(true){
            try{
                String[] speakerRoom = getSpeakerRoomTopic(freeSpeaker, freeRooms);
                checkInfoValid(freeSpeaker, freeRooms, speakerRoom);
                UUID assignedRoom = freeRooms.get(Integer.getInteger(speakerRoom[1]));
                Object[] actSetting = new Object[]{targetPeriod, assignedRoom, speakerRoom[2], speakerRoom[0]};
                newActivitySetter(actSetting);
                break;
            }catch(UserNotFoundException e){
                Presenter.printInvalid("speaker");
            }catch(IndexOutOfBoundsException e2){
                Presenter.printInvalid("room index");
            }
        }
    }

    private void checkInfoValid(ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms, String[] speakerRoom)
            throws UserNotFoundException, IndexOutOfBoundsException {
        if (!freeSpeaker.contains(speakerRoom[0])){
            throw new UserNotFoundException("");
        }
        if (Integer.getInteger(speakerRoom[1]) < 0 || Integer.getInteger(speakerRoom[1]) >= freeRooms.size()){
            throw new IndexOutOfBoundsException();
        }
    }

    private String[] getSpeakerRoomTopic(ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms) {
        Scanner moreInfo = new Scanner(System.in);
        Presenter.printSpeakerRoomPrompt(freeSpeaker, freeRooms);
        String topic = moreInfo.nextLine();
        String speaker = moreInfo.nextLine();
        int roomIndex = moreInfo.nextInt() - 1;
        return new String[]{speaker, Integer.toString(roomIndex), topic};
    }

    private void newActivitySetter(Object[] actSettings){
        UUID assignedChat = messageRoomManager.createChatroom(new ArrayList<>());
        LocalDateTime[] targetPeriod = (LocalDateTime[]) actSettings[0];
        UUID assignedRoom = (UUID) actSettings[1];
        String topic = (String) actSettings[2];
        String speaker = (String) actSettings[3];
        UUID actID = activityManager.addNewActivity(targetPeriod, new UUID[]{assignedChat, assignedRoom}, topic);
        activityManager.addSpeaker(actID, speaker);
        roomManager.BookRoom(targetPeriod, actID, assignedRoom);
        userManager.otherAddSchedule(speaker, targetPeriod, actID);
        messageRoomManager.addUser(speaker, assignedChat);
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
