package entities;

import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Talk extends Event {
    /*
    Additional Variables:
    An Arraylist of speakers

    Additional Methods:
    Getter and setter for Event
    toString with more information
    get event type
     */
    private String speaker;

    public Talk(LocalDateTime[] period, UUID[] chatRoomID,
                 String topic, Integer MaxNum){
        super(period, chatRoomID, topic, MaxNum);
        type = EventType.TALK;
    }

    public boolean setSpeaker(String speaker){
        this.speaker = speaker;
        return true;
    }

    public String getSpeaker(){return this.speaker;}

    public EventType getEventType(){return type;}

    @Override
    public String toString() {
        return "Type: Talk\n" +
                "Speakers: " + speaker +
                super.toString();
    }
}
