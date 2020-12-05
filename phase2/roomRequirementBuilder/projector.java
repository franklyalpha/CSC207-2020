package roomRequirementBuilder;

public abstract class projector implements roomItem{

    public int price;
    public String seriesNum;

    @Override
    public abstract int getPrice();

    @Override
    public abstract void setPrice(int newPrice);

    @Override
    public abstract String getSeriesNum();

    @Override
    public abstract void setSeriesNum(String newSeriesNum);



}
