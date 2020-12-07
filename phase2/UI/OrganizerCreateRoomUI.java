package UI;

import Controllers.CreateRoomController;
import Controllers.UserController;
import menuPresenter.OrganizerPresenter;
import roomRequirementBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OrganizerCreateRoomUI extends AbstractUI {
    private CreateRoomController createRoom;

    public OrganizerCreateRoomUI(UserController userController) {
        super(userController);
        createRoom = new CreateRoomController(userController);
    }

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

    private int createNewRoom(CreateRoomController createRoom, OrganizerPresenter organizerPresenter) throws Exception {

        System.out.println(organizerPresenter.strCreateRoomPrompt());
        Scanner input_1 = new Scanner(System.in);
        int room_capacity = input_1.nextInt();

        roomItems itemList = new roomItems();

        System.out.println(organizerPresenter.strNumOfMicrophone());
        Scanner input_2 = new Scanner(System.in);
        int equipQuantity_2 = input_2.nextInt();
        if(equipQuantity_2 != 0){
            String seriesNum = input_2.nextLine();
            int equipPrice = input_2.nextInt();
            microPhone micro = (microPhone)itemBuilder.buildItem(seriesNum, equipPrice);
            itemList.addItem(micro);
        }

        System.out.println(organizerPresenter.strNumOfMicrophone());
        Scanner input_3 = new Scanner(System.in);
        int equipQuantity_3 = input_3.nextInt();
        if(equipQuantity_3 != 0){
            String seriesNum = input_3.nextLine();
            int equipPrice = input_3.nextInt();
            projector projec = (projector) itemBuilder.buildItem(seriesNum, equipPrice);
            itemList.addItem(projec);
        }

        System.out.println(organizerPresenter.strNumOfMicrophone());
        Scanner input_4 = new Scanner(System.in);
        int equipQuantity_4 = input_4.nextInt();
        if(equipQuantity_4 != 0){
            String seriesNum = input_4.nextLine();
            int equipPrice = input_4.nextInt();
            DJ dj = (DJ)itemBuilder.buildItem(seriesNum, equipPrice);
            itemList.addItem(dj);
        }

        System.out.println(organizerPresenter.strNumOfMicrophone());
        Scanner input_5 = new Scanner(System.in);
        int equipQuantity_5 = input_5.nextInt();
        if(equipQuantity_5 != 0){
            String seriesNum = input_5.nextLine();
            int equipPrice = input_5.nextInt();
            PartyAudioSystem audioSys = (PartyAudioSystem) itemBuilder.buildItem(seriesNum, equipPrice);
            itemList.addItem(audioSys);
        }

        createRoom.createRoomWithCondition(room_capacity, itemList);
        System.out.println(organizerPresenter.strRoomCapacityConfirmation(room_capacity));
        return room_capacity;
    }
}
