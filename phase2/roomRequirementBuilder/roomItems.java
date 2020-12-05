package roomRequirementBuilder;

import java.util.ArrayList;
import java.util.List;

public class roomItems {
    private List<roomItem> items = new ArrayList<roomItem>();

    public void addItem(roomItem item){
        items.add(item);
    }

    public void removeOneItem(roomItem item){
        items.remove(item);
    }

    public int getTotalPrice(){
        int cost = 0;
        for(roomItem item: items){
            cost += item.getPrice() ;
        }
        return cost;
    }

    public int getQuantityByName(String itemName){
        int result = 0;
        for(roomItem item: items){
            if (item.name().equals(itemName)){
                result += 1;
            }
        }
        return result;
    }

    public int getQuantityBySeriesNum(String seriesNum){
        int result = 0;
        for(roomItem item: items){
            if (item.getSeriesNum().equals(seriesNum)){
                result += 1;
            }
        }
        return result;
    }


    public void showItems(){
        for (roomItem item: items){
            System.out.print("Room Item: " + item.name());
            System.out.print("total rental price: " + getTotalPrice());
        }
    }



}
