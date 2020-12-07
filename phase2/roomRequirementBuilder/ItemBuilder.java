package roomRequirementBuilder;

public class ItemBuilder {
    public static roomItem buildItem(String seriesNum, int equipPrice){
        roomItem item = new roomItem();
        item.setSeriesNum(seriesNum);
        item.setPrice(equipPrice);
        return item;
    }
}
