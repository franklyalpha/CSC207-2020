package Controllers;

import functionalityPresenters.Presenter;

import java.util.Scanner;

public class CreateRoomController extends AbstractController{


    public CreateRoomController(UserController userController) {
        super(userController);
    }


    public void createRoomWithCondition(int a, boolean b, int c) throws Exception {
        if (a > 0){
            roomManager.addRoom(a, b, c);
        }
        else{
            throw new Exception();
        }
    }
}
