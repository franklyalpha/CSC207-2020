package roomRequirementBuilder;

/**
 * Represents Microphones as a <code>RoomItem</code> in the room.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 */

public class MicroPhone extends RoomItem implements java.io.Serializable {
    /**
     * numbers of microphones.
     */
    private int micQuantity;

    /**
     * Create microphones.
     * @param quantity is the quantity of microphones wants to create.
     */
    public MicroPhone(int quantity){
        super();
        micQuantity = quantity;
    }

    /**
     * name for microphone.
     * @return A string "Microphone".
     */
    @Override
    public String name() {
        return "Microphone";
    }

    /**
     * Gets the quantity of microphones.
     * @return the quantity of microphone.
     */
    public int quantity(){
        return micQuantity;
    }

    /**
     * show number of microphones.
     * @return A string representation for microphone.
     */
    @Override
    public String toString() {
        return "Num of Microphones: " + micQuantity;
    }
}
