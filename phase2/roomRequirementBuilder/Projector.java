package roomRequirementBuilder;

/**
 * Represents Projector as a <code>RoomItem</code> in the room.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 */
public class Projector extends RoomItem implements java.io.Serializable {

    /**
     * Get the name of Projector.
     * @return the string "Projector".
     */
    @Override
    public String name() {
        return "Projector";
    }

    /**
     * Show whether has projector or not.
     * @return a string that shows: has projector.
     */
    @Override
    public String toString() {
        return "Has Projector: yes";
    }
}
