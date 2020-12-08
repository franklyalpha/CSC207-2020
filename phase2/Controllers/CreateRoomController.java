package Controllers;

import roomRequirementBuilder.roomItems;

import java.util.List;

public class CreateRoomController extends AbstractController{


    public CreateRoomController(UserController userController) {
        super(userController);
    }


    public void createRoomWithCondition(int a, List<List<Object>> b) throws Exception {
        if (a > 0){
            roomManager.addRoom(a, b);
        }
        else{
            throw new Exception();
        }
    }
}
