package UI;

import Controllers.CreateRoomController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.OrganizerPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the UI for <code>Organizer</code> to create room. Organizers can set the capacity and <code>RoomItem</code> in
 * this room.
 */
public class OrganizerCreateRoomUI extends AbstractUI {

    /**
     * A instance of <code>CreateRoomController</code>.
     */
    private CreateRoomController createRoom;

    /**
     * the constructor for this UI.
     * @param userController an instance of <code>UserController</code>.
     */
    public OrganizerCreateRoomUI(UserController userController) {
        super(userController);
        createRoom = new CreateRoomController(userController);
    }

    /**
     * Create new room with information given by the <code>Organizer</code>.
     * Will give instructions for invalid inputs.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        OrganizerPresenter organizerPresenter = new OrganizerPresenter();
        for (int i = 0; i < 3; i++){
            try {
                createNewRoom(createRoom, organizerPresenter);
                return;
            }catch(Exception e) {
                System.out.println(organizerPresenter.strInvalidInput());
            }
        }
        throw new ExceedingMaxAttemptException("Maximum number of attempts exceeded");
    }

    /**
     * Create new event room with Organizer given capacity and number of 3 different room items.
     * @param createRoom A instance of <code>CreateRoomController</code>.
     * @param presenter An instance of <code>OrganizerPresenter</code>.
     * @throws Exception when room capacity is less than or equal to zero.
     */
    private void createNewRoom(CreateRoomController createRoom, OrganizerPresenter presenter) throws Exception {
        System.out.println(presenter.strCreateRoomPrompt());
        Scanner input = new Scanner(System.in);
        int room_capacity = input.nextInt();
        input.nextLine();
        List<Integer> itemList = new ArrayList<>();
        String[] techPrompts = new String[]{presenter.strNumOfMicrophone(), presenter.strNumOfProjector(), presenter.strNumOfPartyAudioSystem()};
        for(int x = 0; x < 3; x++){
            System.out.println(techPrompts[x]);
            int equipQuantity = input.nextInt();
            itemList.add(equipQuantity);
        }
        createRoom.createRoomWithCondition(room_capacity, itemList);
        System.out.println(presenter.strRoomCapacityConfirmation(room_capacity));
    }
}
