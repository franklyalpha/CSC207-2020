package eventRoomRequirementBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents all event room items in a <code>EventRoom</code>.
 */
public class EventRoomItems implements java.io.Serializable {


    private List<EventRoomItem> items = new ArrayList<>();

    /**
     * Get the event room items
     * @return a list of event room items.
     */
    public List<EventRoomItem> getItems(){
        return this.items;
    }

    /**
     * Add a room item into the event room items collection.
     * @param item is the newly added event room item.
     */
    public void addItem(EventRoomItem item){
        items.add(item);
    }

    /**
     * Remove a room item from the event room items collection.
     * @param item is the removed event room item.
     */
    public void removeOneItem(EventRoomItem item){
        items.remove(item);
    }

    /**
     * Get the total price of the event room item collection.
     * @return the total price in integer.
     */
    public int getTotalPrice(){
        int cost = 0;
        for(EventRoomItem item: items){
            cost += item.getPrice() ;
        }
        return cost;
    }


    /**
     * Get the quantity of a specific category of event room item by inputting the category name.
     * @param itemName is the name string of that category of event room item.
     * @return the total number of that category of event room item.
     */
    public int getQuantityByName(String itemName){
        int result = 0;
        for(EventRoomItem item: items){
            if (item.name().equals(itemName)){
                result += 1;
            }
        }
        return result;
    }

    /**
     * Get the quantity of a specific category of event room item by inputting the category series number.
     * @param seriesNum is the string representation of the series number of that category of room item.
     * @return the total number of that category of room item.
     */
    public int getQuantityBySeriesNum(String seriesNum){
        int result = 0;
        for(EventRoomItem item: items){
            if (item.getSeriesNum().equals(seriesNum)){
                result += 1;
            }
        }
        return result;
    }


    /**
     * Show a string representation for all the items in <code>EventRoomItems</code>.
     * @return a string contains all the event room items' string representation.
     */
    public String showItems(){
        StringBuilder itemsString = new StringBuilder();
        for (EventRoomItem item: items){
            if(item != null){
                itemsString.append(item.toString()).append("\n");
            }
        }
        return itemsString.toString();
    }

    /**
     * Check whether <code>EventRoomItems</code> has <code>Microphone</code> in it.
     * @return true if <<code>EventRoomItems</code> has <code>Microphone</code> in it.
     */
    public boolean hasMicrophone(){
        return items.get(0) != null;
    }

    /**
     * Check whether <code>EventRoomItems</code> has <code>Projector</code> in it.
     * @return true if <code>EventRoomItems</code> has <code>Projector</code> in it.
     */
    public boolean hasProjector(){
        return items.get(1) != null;
    }

    /**
     * Check whether <code>EventRoomItems</code> has <code>PartyAudioSystem</code> in it.
     * @return true if <code>EventRoomItems</code> has <code>PartyAudioSystem</code> in it.
     */
    public boolean hasPartyAudio(){
        return items.get(2) != null;
    }



}
