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
    get event type
     */
    public Party(LocalDateTime[] period, UUID[] chatRoomID,
                 String topic, Integer MaxNum){
        super(period, chatRoomID, topic, MaxNum);
        type = EventType.PARTY;
    }

    public EventType getEventType(){return type;}

    @Override
    public String toString() {
        return "Type: Party\n" +
                super.toString();
    }
}
