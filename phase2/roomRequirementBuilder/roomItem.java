package roomRequirementBuilder;


public class roomItem implements java.io.Serializable{

    public int price;
    public String seriesNum;

    public String name() {
        return null;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int newPrice){
        this.price = newPrice;
    }

    public String getSeriesNum() {
        return this.seriesNum;
    }

    public void setSeriesNum(String newSeriesNum) {
        this.seriesNum = newSeriesNum;
    }

}
