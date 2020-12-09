package roomRequirementBuilder;

import entities.EventRoom;
import useCases.RoomManager;

import java.util.ArrayList;

public class RoomBuilder {
    private ItemFactory itemFactory;
    private EventRoom newRoom;
    private RoomItems roomItems;

    public RoomBuilder(int capacity){
        itemFactory = new ItemFactory();
        newRoom = new EventRoom(capacity);
        roomItems = new RoomItems();
    }

    public EventRoom getNewRoom(){
        newRoom.setRoomItems(roomItems);
        return newRoom;
    }

    public void buildProjector(int quantity){
        if (quantity != 0){
            roomItems.addItem(itemFactory.constructProjector());
        }
        else{
            roomItems.addItem(null);
        }
    }

    public void buildMicrophone(int quantity){
        if(quantity != 0){
            roomItems.addItem(itemFactory.constructMicrophone(quantity));
        }
        else{
            roomItems.addItem(null);
        }
    }

    public void buildPartyAudio(int quantity){
        if(quantity != 0){
            roomItems.addItem(itemFactory.constructPartyAudio());
        }
        else{
            roomItems.addItem(null);
        }
    }
}
