package entities;

import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Panel extends Event {
    /*
    Additional Variables:
    An Arraylist of speakers

    Additional Methods:
    Getter and setter for speakers
    toString with more information
     */
    private ArrayList<String> speakers;

    public Panel(LocalDateTime[] period, UUID[] chatRoomID,
                 String topic, Integer MaxNum){
        super(period, chatRoomID, topic, MaxNum);
        type = EventType.PANEL;
    }

    public void setSpeakers(ArrayList<String> speakers){this.speakers = speakers;}

    public ArrayList<String> getSpeakers(){return speakers;}

    @Override
    public String toString() {
        return "Type: Panel\n" +
                "Speakers: \n" + speakerToString() +
                super.toString();
    }

    @Override
    public String speakerToString(){
        StringBuilder finalList = new StringBuilder();
        for(String i: speakers){
            finalList.append("    ").append(i).append("\n");
        }
        return finalList.toString();
    }
}
