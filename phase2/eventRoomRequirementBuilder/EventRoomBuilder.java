package eventRoomRequirementBuilder;

import entities.EventRoom;

/**
 * This Class use Builder Pattern to build Event Room Instance.
 */
public class EventRoomBuilder {
    /**
     * Use Factory Pattern to build <code>EventRoomItem</code>.
     */
    private ItemFactory itemFactory;

    /**
     * new <code>EventRoom</code> to be added room items.
     */
    private EventRoom newRoom;

    /**
     * room items for each new <code>EventRoom</code>.
     */
    private EventRoomItems eventRoomItems;

    /**
     * Create new <code>EventRoomBuilder</code> with a room capacity.
     * @param capacity to create a <code>EventRoom</code> instance.
     */
    public EventRoomBuilder(int capacity){
        itemFactory = new ItemFactory();
        newRoom = new EventRoom(capacity);
        eventRoomItems = new EventRoomItems();
    }

    /**
     * Get a new Event Room instance with room items added.
     * @return a new event room instance.
     */
    public EventRoom getNewRoom(){
        newRoom.setRoomItems(eventRoomItems);
        return newRoom;
    }

    /**
     * create projectors and add them into the event room items.
     * @param quantity is the number of <code>Projector</code> we want to create.
     */
    public void buildProjector(int quantity){
        if (quantity != 0){
            eventRoomItems.addItem(itemFactory.constructProjector());
        }
        else{
            eventRoomItems.addItem(null);
        }
    }

    /**
     * create microphones and add them into the event room items.
     * @param quantity is the number of <code>Microphone</code> we want to create.
     */
    public void buildMicrophone(int quantity){
        if(quantity != 0){
            eventRoomItems.addItem(itemFactory.constructMicrophone(quantity));
        }
        else{
            eventRoomItems.addItem(null);
        }
    }

    /**
     * create party audio systems and add them into the event room items.
     * @param quantity is the number of <code>PartyAudioSystem</code> we want to create.
     */
    public void buildPartyAudio(int quantity){
        if(quantity != 0){
            eventRoomItems.addItem(itemFactory.constructPartyAudio());
        }
        else{
            eventRoomItems.addItem(null);
        }
    }
}
