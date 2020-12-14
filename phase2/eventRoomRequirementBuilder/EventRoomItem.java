package eventRoomRequirementBuilder;

/**
 * Represents room items in the room.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 */

public class EventRoomItem implements java.io.Serializable{

    /**
     * price for each event room item.
     */
    public int price;

    /**
     * series number for each event room item.
     */
    public String seriesNum;

    /**
     * Get the name for this event room item.
     * @return null, Needs to override.
     */
    public String name() {
        return null;
    }

    /**
     * Gets the price for this <code>EventRoomItem</code>
     * @return the price.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Change the price of this <code>EventRoomItem</code> with given new price.
     * @param newPrice the new price.
     */
    public void setPrice(int newPrice){
        this.price = newPrice;
    }

    /**
     * Get the series number of this <code>EventRoomItem</code>.
     * @return the series number.
     */
    public String getSeriesNum() {
        return this.seriesNum;
    }

    /**
     * Change the series number of this <code>EventRoomItem</code> with given new series number.
     * @param newSeriesNum the new series number.
     */
    public void setSeriesNum(String newSeriesNum) {
        this.seriesNum = newSeriesNum;
    }

}
