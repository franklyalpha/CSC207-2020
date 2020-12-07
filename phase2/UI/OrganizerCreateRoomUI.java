package UI;

import Controllers.CreateRoomController;
import Controllers.UserController;
import menuPresenter.OrganizerPresenter;
import roomRequirementBuilder.*;

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

    private void createNewRoom(CreateRoomController createRoom, OrganizerPresenter organizerPresenter) throws Exception {

        System.out.println(organizerPresenter.strCreateRoomPrompt());
        Scanner input = new Scanner(System.in);

        int room_capacity = input.nextInt();

        roomItems itemList = new roomItems();

        System.out.println(organizerPresenter.strNumOfMicrophone());
        int equipQuantity_2 = input.nextInt();
        if(equipQuantity_2 != 0){
            String seriesNum = input.nextLine();
            int equipPrice = input.nextInt();
            for (int i=0; i<equipQuantity_2; i++){
                microPhone micro = (microPhone) ItemBuilder.buildItem(seriesNum, equipPrice);
                itemList.addItem(micro);
            }
        }

        System.out.println(organizerPresenter.strNumOfMicrophone());
        int equipQuantity_3 = input.nextInt();
        if(equipQuantity_3 != 0){
            String seriesNum = input.nextLine();
            int equipPrice = input.nextInt();
            for (int i=0; i<equipQuantity_3; i++) {
                projector projec = (projector) ItemBuilder.buildItem(seriesNum, equipPrice);
                itemList.addItem(projec);
            }
        }

        System.out.println(organizerPresenter.strNumOfMicrophone());
        int equipQuantity_4 = input.nextInt();
        if(equipQuantity_4 != 0){
            String seriesNum = input.nextLine();
            int equipPrice = input.nextInt();
            for (int i=0; i<equipQuantity_4; i++) {
                DJ dj = (DJ) ItemBuilder.buildItem(seriesNum, equipPrice);
                itemList.addItem(dj);
            }
        }

        System.out.println(organizerPresenter.strNumOfMicrophone());
        int equipQuantity_5 = input.nextInt();
        if(equipQuantity_5 != 0){
            String seriesNum = input.nextLine();
            int equipPrice = input.nextInt();
            for (int i=0; i<equipQuantity_5; i++) {
                PartyAudioSystem audioSys = (PartyAudioSystem) ItemBuilder.buildItem(seriesNum, equipPrice);
                itemList.addItem(audioSys);
            }
        }

        createRoom.createRoomWithCondition(room_capacity, itemList);
        System.out.println(organizerPresenter.strRoomCapacityConfirmation(room_capacity));
    }
}
