package UI;

import Controllers.CreateRoomController;
import Controllers.UserController;
import menuPresenter.OrganizerPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is a UI for <code>Organizer</code> to create room. Organizers can set the capacity and <code>RoomItem</code> in
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
     * Run method for this UI.
     * Will give instructions for invalid inputs.
     */
    @Override
    public void run() {
        OrganizerPresenter organizerPresenter = new OrganizerPresenter();
        while(true){
            try {
                createNewRoom(createRoom, organizerPresenter);
                break;
            }catch(Exception e) {
                System.out.println(organizerPresenter.strInvalidInput());
            }
        }
    }

    /**
     *
     * @param createRoom A instance of <code>CreateRoomController</code>.
     * @param organizerPresenter An instance of <code>OrganizerPresenter</code>.
     * @throws Exception when room capacity is less than or equal to zero.
     */
    private void createNewRoom(CreateRoomController createRoom, OrganizerPresenter organizerPresenter) throws Exception {
        System.out.println(organizerPresenter.strCreateRoomPrompt());
        Scanner input = new Scanner(System.in);
        int room_capacity = input.nextInt();
        input.nextLine();
        List<Integer> itemList = new ArrayList<>();
        for(int x = 0; x < 3; x++){
            if(x==0){
                System.out.println(organizerPresenter.strNumOfMicrophone());
            }else if(x==1){
                System.out.println(organizerPresenter.strNumOfProjector());
            }else{
                System.out.println(organizerPresenter.strNumOfPartyAudioSystem());
            }
            int equipQuantity = input.nextInt();
            itemList.add(equipQuantity);
        }
        createRoom.createRoomWithCondition(room_capacity, itemList);
        System.out.println(organizerPresenter.strRoomCapacityConfirmation(room_capacity));
    }
}
