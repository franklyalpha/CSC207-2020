package Controllers;

import functionalityPresenters.Presenter;
import roomRequirementBuilder.roomItems;

import java.util.List;
import java.util.Scanner;

public class CreateRoomController extends AbstractController{


    public CreateRoomController(UserController userController) {
        super(userController);
    }


    public void createRoomWithCondition(int a, roomItems b) throws Exception {
        if (a > 0){
            roomManager.addRoom(a, b);
        }
        else{
            throw new Exception();
        }
    }
}
