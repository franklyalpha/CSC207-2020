package UI;

import Controllers.CreateScheduleController;
import Controllers.UserController;
import globallyAccessible.*;
import menuPresenter.ModifyEventPresenter;
import menuPresenter.OrganizerAddSchedulePresenter;

import java.time.LocalDateTime;
import java.util.*;

/**
 * This is the UI for <code>Organizer</code> to create a event.
 * Organizers can create a new event and select a room to hold it, also can set event to be a specific type of event
 * and set speakers for it.
 */
public class OrganizerAddScheduleUI extends AbstractUI {


    /**
     * An instance of <code>OrganizerAddSchedulePresenter</code>.
     */
    protected final OrganizerAddSchedulePresenter organizerAddSchedulePresenter = new OrganizerAddSchedulePresenter();

    /**
     * An instance of <code>CreateScheduleController</code>.
     */
    protected CreateScheduleController createSchedule;

    /**
     * An instance of <code>ModifyEventPresenter</code>.
     */
    protected ModifyEventPresenter modifyEventPresenter;

    protected ArrayList<String> freeSpeakers;

    protected ArrayList<String[]> freeRooms;

    protected ArrayList<UUID> freeRoomsID;

    /**
     * The constructor for this UI.
     * @param userController is an instance of UserController.
     */
    public OrganizerAddScheduleUI(UserController userController) {
        super(userController);
        createSchedule = new CreateScheduleController(userController);
        modifyEventPresenter = new ModifyEventPresenter();
        freeRooms = new ArrayList<>();
        freeSpeakers = new ArrayList<>();
        freeRoomsID = new ArrayList<>();
    }

    /**
     * Creates event with given <code>Organizer</code> input.
     * Will display instructions if the input time period is invalid or has other invalid input.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        for (int i = 0; i < 3; i++){
            try{
                majorProcessor();
                return;
            }catch(CannotCreateEventException e){
                System.out.println(organizerAddSchedulePresenter.strInvalidTimePeriod());
            }catch(Exception e){
                System.out.println(organizerAddSchedulePresenter.strInvalidInput());
            }
        }
        throw new ExceedingMaxAttemptException("Maximum number of attempts exceeded");
    }

    private void majorProcessor() throws CannotCreateEventException, ExceedingMaxAttemptException {
        LocalDateTime[] targetPeriod = periodProcessor();
        Object[] speakersRooms = createSchedule.checkTimePeriodValidity(targetPeriod);
        freeSpeakers = (ArrayList<String>) speakersRooms[1];
        freeRooms = new ArrayList<>(getSuggestedRoom());
        if(freeRooms.size() == 0){
            System.out.println("Sorry, there are no rooms fitting your request....");
            throw new CannotCreateEventException("Invalid tech requirement");
        }
        freeRoomsID = extractRoomID(freeRooms);
        createByType();
    }

    private void createByType()
            throws ExceedingMaxAttemptException{
        if (freeSpeakers.size() == 0){
            System.out.println("You can only create party");
            new OrganizerAddPartyUI(userController, new Object[]{freeSpeakers, freeRooms}, createSchedule).run();
        }
        else{
            Scanner type = new Scanner(System.in);
            System.out.println(organizerAddSchedulePresenter.strTypePrompt());
            int typeNum = type.nextInt();
            createByType(typeNum);
        }
    }

    private void createByType(int typeNum) throws ExceedingMaxAttemptException {
        if(typeNum == 1){
            new OrganizerAddTalkUI(userController, new Object[]{freeSpeakers, freeRooms}, createSchedule).run();
        }else if(typeNum == 2){
            new OrganizerAddPanelUI(userController, new Object[]{freeSpeakers, freeRooms}, createSchedule).run();
        }else{
            new OrganizerAddPartyUI(userController, new Object[]{freeSpeakers, freeRooms}, createSchedule).run();
        }
    }

    protected ArrayList<UUID> extractRoomID(ArrayList<String[]> freeRooms) {
        ArrayList<UUID> freeRoomsID = new ArrayList<>();
        for (String[] roomInfo: freeRooms){
            freeRoomsID.add(UUID.fromString(roomInfo[0]));
        }
        return freeRoomsID;
    }

    protected List<String[]> getSuggestedRoom() {
        Scanner input = new Scanner(System.in);
        System.out.println(modifyEventPresenter.askForRequirementPrompt());
        boolean hasProjector = input.nextBoolean();
        boolean hasMicrophone = input.nextBoolean();
        boolean hasPartyAudio = input.nextBoolean();
        return createSchedule.getSuggestedRoomList(hasProjector, hasMicrophone, hasPartyAudio);
    }

    protected Object[] inputRoomTopic()
            throws InputMismatchException{
        Scanner moreInfo = new Scanner(System.in);
        System.out.println(modifyEventPresenter.printSuggestedRoomPrompt(freeRooms));
        return getRoomTopicMaxEnroll(moreInfo);
    }

    private Object[] getRoomTopicMaxEnroll(Scanner moreInfo){
        System.out.println(organizerAddSchedulePresenter.strRoomNumPrompt());
        int roomIndex = moreInfo.nextInt();
        moreInfo.nextLine();
        System.out.println(organizerAddSchedulePresenter.strTopicPrompt());
        String topic = moreInfo.nextLine();
        System.out.println(organizerAddSchedulePresenter.strMaxNumPrompt());
        int MaxNumber = moreInfo.nextInt();
        moreInfo.nextLine();
        return new Object[]{freeRoomsID.get(roomIndex), topic, MaxNumber, new ArrayList<>()};
    }

    private LocalDateTime[] periodProcessor() throws CannotCreateEventException {
        Scanner start = new Scanner(System.in);
        System.out.println(organizerAddSchedulePresenter.strStartTimePrompt());
        LocalDateTime startDateTime = LocalDateTime.of(start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt());
        Scanner end = new Scanner(System.in);
        System.out.println(organizerAddSchedulePresenter.strEndTimePrompt());
        LocalDateTime endDateTime = LocalDateTime.of(end.nextInt(), end.nextInt(), end.nextInt(), end.nextInt(), end.nextInt());
        if(endDateTime.isEqual(startDateTime)){
            throw new CannotCreateEventException("invalid time period!");
        }
        return new LocalDateTime[]{startDateTime, endDateTime};
    }
}
