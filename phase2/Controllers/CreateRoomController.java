package Controllers;

import Presenters.Presenter;

import java.util.Scanner;

public class CreateRoomController extends AbstractController{


    public CreateRoomController(UserController userController) {
        super(userController);
    }


    public void createRoomWithCondition() throws Exception {
        Scanner input_1 = new Scanner(System.in);
        Presenter.printRoomCapacityPrompt();
        Scanner input_2 = new Scanner(System.in);
        Presenter.printRoomHaveProjectorPrompt();
        Scanner input_3 = new Scanner(System.in);
        Presenter.printRoomNumMicrophonePrompt();
        int a = input_1.nextInt();
        boolean b = input_2.nextBoolean();
        int c = input_3.nextInt();
        if (a > 0){
            roomManager.addRoom(a, b, c);
            Presenter.printRoomCapacityConfirmation(a);
        }
        else{
            throw new Exception();
        }
    }
}
