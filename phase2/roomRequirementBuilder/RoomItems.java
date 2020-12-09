package roomRequirementBuilder;

import java.util.ArrayList;
import java.util.List;

public class RoomItems {
    private List<RoomItem> items = new ArrayList<RoomItem>();


    public List<RoomItem> getItems(){
        return this.items;
    }

    public void addItem(RoomItem item){
        items.add(item);
    }

    public void removeOneItem(RoomItem item){
        items.remove(item);
    }

    public int getTotalPrice(){
        int cost = 0;
        for(RoomItem item: items){
            cost += item.getPrice() ;
        }
        return cost;
    }

    public int getQuantityByName(String itemName){
        int result = 0;
        for(RoomItem item: items){
            if (item.name().equals(itemName)){
                result += 1;
            }
        }
        return result;
    }

    public int getQuantityBySeriesNum(String seriesNum){
        int result = 0;
        for(RoomItem item: items){
            if (item.getSeriesNum().equals(seriesNum)){
                result += 1;
            }
        }
        return result;
    }


    public String showItems(){
        String itemsString = "";
        for (RoomItem item: items){
            if(item != null){
                itemsString = itemsString + item.toString() + "\n";
            }
        }
        return itemsString;
    }



}
