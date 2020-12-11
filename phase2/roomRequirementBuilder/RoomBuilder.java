package roomRequirementBuilder;

import entities.EventRoom;
import useCases.RoomManager;

import java.util.ArrayList;

/**
 * This Class use Builder Pattern to build Room Instance.
 */
public class RoomBuilder {
    /**
     * Use Factory Pattern to build <code>RoomItem</code>.
     */
    private ItemFactory itemFactory;

    /**
     * new <code>Room</code> to be added room items.
     */
    private EventRoom newRoom;

    /**
     * room items for each new <code>Room</code>.
     */
    private RoomItems roomItems;

    /**
     * Create new <code>RoomBuilder</code> with a room capacity.
     * @param capacity to create a <code>Room</code> instance.
     */
    public RoomBuilder(int capacity){
        itemFactory = new ItemFactory();
        newRoom = new EventRoom(capacity);
        roomItems = new RoomItems();
    }

    /**
     * Get a new Room instance with room items added.
     * @return a new room instance.
     */
    public EventRoom getNewRoom(){
        newRoom.setRoomItems(roomItems);
        return newRoom;
    }

    /**
     * create projectors and add them into the room items.
     * @param quantity is the number of <code>Projector</code> we want to create.
     */
    public void buildProjector(int quantity){
        if (quantity != 0){
            roomItems.addItem(itemFactory.constructProjector());
        }
        else{
            roomItems.addItem(null);
        }
    }

    /**
     * create microphones and add them into the room items.
     * @param quantity is the number of <code>Microphone</code> we want to create.
     */
    public void buildMicrophone(int quantity){
        if(quantity != 0){
            roomItems.addItem(itemFactory.constructMicrophone(quantity));
        }
        else{
            roomItems.addItem(null);
        }
    }

    /**
     * create party audio systems and add them into the room items.
     * @param quantity is the number of <code>PartyAudioSystem</code> we want to create.
     */
    public void buildPartyAudio(int quantity){
        if(quantity != 0){
            roomItems.addItem(itemFactory.constructPartyAudio());
        }
        else{
            roomItems.addItem(null);
        }
    }
}
