package roomRequirementBuilder;

public class EpsonProjector extends projector{

    @Override
    public String name() {
        return "Epson Projector";
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(int newPrice){
        this.price = newPrice;
    }

    @Override
    public String getSeriesNum() {
        return this.seriesNum;
    }

    @Override
    public void setSeriesNum(String newSeriesNum) {
        this.seriesNum = newSeriesNum;
    }
}