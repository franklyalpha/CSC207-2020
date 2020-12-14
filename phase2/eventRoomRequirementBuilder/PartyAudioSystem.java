package eventRoomRequirementBuilder;

/**
 * Represents a party audio system as a <code>RoomItem</code> in the room.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class PartyAudioSystem extends EventRoomItem implements java.io.Serializable {

    /**
     * Get the name of party audio system.
     * @return the string "Party Audio System".
     */
    @Override
    public String name() {
        return "Party Audio System";
    }

    /**
     * Show whether has party audio system or not.
     * @return a string that shows: has party audio system.
     */
    @Override
    public String toString() {
        return "Has Party Audio System: yes";
    }
}
