package UI;

import Controllers.CreateRoomController;
import Controllers.UserController;
import menuPresenter.OrganizerPresenter;

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

    private void createNewRoom(CreateRoomController createRoom, OrganizerPresenter organizerPresenter) throws Exception {

        System.out.println(organizerPresenter.strCreateRoomPrompt());
        Scanner input = new Scanner(System.in);

        int room_capacity = input.nextInt();

        List<List<Object>> itemList = new ArrayList<>();

        for(int x = 0; x < 4; x++){
            if(x==0){
                System.out.println(organizerPresenter.strNumOfMicrophone());
            }else if(x==1){
                System.out.println(organizerPresenter.strNumOfProjector());
            }else if(x==2){
                System.out.println(organizerPresenter.strNumOfDJEquipment());
            }else{
                System.out.println(organizerPresenter.strNumOfPartyAudioSystem());
            }
            List<Object> temp = new ArrayList<>();
            int equipQuantity = input.nextInt();
            temp.add(equipQuantity);
            if(equipQuantity > 0){
                String seriesNum = input.nextLine();
                int equipPrice = input.nextInt();
                temp.add(seriesNum);
                temp.add(equipPrice);
                itemList.add(temp);
            }
        }

        createRoom.createRoomWithCondition(room_capacity, itemList);
        System.out.println(organizerPresenter.strRoomCapacityConfirmation(room_capacity));
    }
}
