package UI;

import Controllers.CreateRoomController;
import Controllers.UserController;
import menuPresenter.OrganizerPresenter;

import java.util.Scanner;


public class OrgCreateRoomUI extends AbstractUI {
    private CreateRoomController createRoom;

    public OrgCreateRoomUI(UserController userController) {
        super(userController);
        createRoom = new CreateRoomController(userController);
    }

    @Override
    public void run() {
        OrganizerPresenter organizerPresenter = new OrganizerPresenter();
        while(true){
            try {
                startCreateRoom(organizerPresenter);
                break;
            }catch(Exception e) {
                System.out.println(organizerPresenter.strInvalidInput());
            }
        }
    }

    private void startCreateRoom(OrganizerPresenter organizerPresenter) throws Exception {
        System.out.println(organizerPresenter.strCreateRoomPrompt());
        int a = createNewRoom(createRoom);
        System.out.println(organizerPresenter.strRoomCapacityConfirmation(a));
    }

    private int createNewRoom(CreateRoomController createRoom) throws Exception {
        Scanner input = new Scanner(System.in);
        int a = input.nextInt();
        boolean b = input.nextBoolean();
        int c = input.nextInt();
        createRoom.createRoomWithCondition(a, b, c);
        return a;
    }
}
