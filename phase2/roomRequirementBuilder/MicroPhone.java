package roomRequirementBuilder;

public class MicroPhone extends RoomItem {
    private int micQuantity;

    public MicroPhone(int quantity){
        super();
        micQuantity = quantity;
    }

    @Override
    public String name() {
        return "Microphone";
    }

    public int quantity(){
        return micQuantity;
    }

    @Override
    public String toString() {
        return "Num of Microphones: " + micQuantity;
    }
}
