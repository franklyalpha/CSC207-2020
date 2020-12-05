package entities;

import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Party extends Event {
    /*
    Additional Variables:
    None

    Additional Methods:
    toString with more information
     */
    public Party(LocalDateTime[] period, UUID[] chatRoomID,
                 String topic, Integer MaxNum){
        super(period, chatRoomID, topic, MaxNum);
        type = EventType.PARTY;
    }

    @Override
    public String toString() {
        return "Type: Party\n" +
                super.toString();
    }
}
