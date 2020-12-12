package roomRequirementBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents all room items in a <code>EventRoom</code>.
 */
public class RoomItems implements java.io.Serializable {


    private List<RoomItem> items = new ArrayList<RoomItem>();

    /**
     * Get the room items
     * @return a list of room items.
     */
    public List<RoomItem> getItems(){
        return this.items;
    }

    /**
     * Add a room item into the room items collection.
     * @param item is the newly added room item.
     */
    public void addItem(RoomItem item){
        items.add(item);
    }

    /**
     * Remove a room item from the room items collection.
     * @param item is the removed room item.
     */
    public void removeOneItem(RoomItem item){
        items.remove(item);
    }

    /**
     * Get the total price of the room item collection.
     * @return the total price in integer.
     */
    public int getTotalPrice(){
        int cost = 0;
        for(RoomItem item: items){
            cost += item.getPrice() ;
        }
        return cost;
    }


    /**
     * Get the quantity of a specific category of room item by inputting the category name.
     * @param itemName is the name string of that category of room item.
     * @return the total number of that category of room item.
     */
    public int getQuantityByName(String itemName){
        int result = 0;
        for(RoomItem item: items){
            if (item.name().equals(itemName)){
                result += 1;
            }
        }
        return result;
    }

    /**
     * Get the quantity of a specific category of room item by inputting the category series number.
     * @param seriesNum is the string representation of the series number of that category of room item.
     * @return the total number of that category of room item.
     */
    public int getQuantityBySeriesNum(String seriesNum){
        int result = 0;
        for(RoomItem item: items){
            if (item.getSeriesNum().equals(seriesNum)){
                result += 1;
            }
        }
        return result;
    }


    /**
     * Show a string representation for all the items in <code>RoomItems</code>.
     * @return a string contains all the room items' string representation.
     */
    public String showItems(){
        String itemsString = "";
        for (RoomItem item: items){
            if(item != null){
                itemsString = itemsString + item.toString() + "\n";
            }
        }
        return itemsString;
    }

    /**
     * Check whether <code>RoomItems</code> has <code>Microphone</code> in it.
     * @return true if <code>RoomItems</code> has <code>Microphone</code> in it.
     */
    public boolean hasMicrophone(){
        return items.get(0) != null;
    }

    /**
     * Check whether <code>RoomItems</code> has <code>Projector</code> in it.
     * @return true if <code>RoomItems</code> has <code>Projector</code> in it.
     */
    public boolean hasProjector(){
        return items.get(1) != null;
    }

    /**
     * Check whether <code>RoomItems</code> has <code>PartyAudioSystem</code> in it.
     * @return true if <code>RoomItems</code> has <code>PartyAudioSystem</code> in it.
     */
    public boolean hasPartyAudio(){
        return items.get(2) != null;
    }



}
