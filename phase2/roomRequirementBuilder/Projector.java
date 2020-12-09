package roomRequirementBuilder;

public class Projector extends RoomItem implements java.io.Serializable {

    @Override
    public String name() {
        return "Projector";
    }

    @Override
    public String toString() {
        return "Has Projector: yes";
    }
}
