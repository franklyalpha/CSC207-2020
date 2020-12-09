package roomRequirementBuilder;

public class PartyAudioSystem extends RoomItem implements java.io.Serializable {

    @Override
    public String name() {
        return "Party Audio System";
    }

    @Override
    public String toString() {
        return "Has Party Audio System: yes";
    }
}
