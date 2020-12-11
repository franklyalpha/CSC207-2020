package Controllers;

import globallyAccessible.CannotCreateEventException;

import java.util.List;
import java.util.UUID;

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
     * @param roomItems a list containing quantities of <>roomItems</> as <>Integer</>.
     * @throws CannotCreateEventException: is thrown when capacity is less than or equal to zero.
     */
    public UUID createRoomWithCondition(int capacity, List<Integer> roomItems) throws CannotCreateEventException {
        if (capacity > 0){
            return roomManager.addRoom(capacity, roomItems);
        }
        else{
            throw new CannotCreateEventException("Invalid capacity!!!");
        }
    }
}
