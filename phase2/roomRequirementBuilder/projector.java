package roomRequirementBuilder;

public abstract class projector implements roomItem{

    @Override
    public abstract int rentalPrice();

    @Override
    public abstract int quantity();
}
