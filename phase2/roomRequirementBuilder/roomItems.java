package roomRequirementBuilder;

import java.util.ArrayList;
import java.util.List;

public class roomItems {
    private List<roomItem> items = new ArrayList<roomItem>();

    public void addItem(roomItem item){
        items.add(item);
    }

    public int getTotalRentalPrice(){
        int cost = 0;
        for(roomItem item: items){
            cost += item.rentalPrice() ;
        }
        return cost;
    }

    public void showItems(){
        for (roomItem item: items){
            System.out.print("Room Item: " + item.name());
            System.out.print("total rental price: " + getTotalRentalPrice());
        }
    }


}
