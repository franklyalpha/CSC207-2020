package Controllers;

import Presenters.Presenter;

import java.util.Scanner;

public class CreateRoomController extends AbstractController{


    public CreateRoomController(UserController userController) {
        super(userController);
    }


    public void createRoomWithCapacity() throws Exception {
        Scanner input = new Scanner(System.in);
        Presenter.printRoomCapacityPrompt();
        int a = input.nextInt();
        if (a > 0){
            roomManager.addRoom(a);
            Presenter.printRoomCapacityConfirmation(a);
        }
        else{
            throw new Exception();
        }
    }
}
