package Controllers;

import globallyAccessible.CannotCreateEventException;

import java.util.List;

/**
 * The controller responsible for creating a new room with given information. Extends from <>AbstractController</>.
 */
public class CreateRoomController extends AbstractController{

    /**
     * The constructor of this controller.
     * @param userController: An instance of <>UserController</>.
     */
    public CreateRoomController(UserController userController) {
        super(userController);
    }


    /**
     * Creates room with given capacity and technical requirement.
     * @param capacity an integer representing number of people this room can contain at maximum.
     * @param roomItems a list containing information of <>roomItems</>. Each sublist contains: quantity of <>RoomItem</>,
     *                  serial number of <>RoomItem</>, and price of <>RoomItem</>.
     * @throws CannotCreateEventException: is thrown when capacity is less than or equal to zero.
     */
    public void createRoomWithCondition(int capacity, List<Integer> roomItems) throws CannotCreateEventException {
        if (capacity > 0){
            roomManager.addRoom(capacity, roomItems);
        }
        else{
            throw new CannotCreateEventException("Invalid capacity!!!");
        }
    }
}
