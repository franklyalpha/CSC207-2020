package useCases;

import entities.Event;
import entities.Panel;
import entities.Talk;
import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TalkManager extends EventManager {
    /*
    Additional Variables:
    None

    Additional Methods:
    Speaker manipulators
    Finder
     */
    public TalkManager(){
        super();
    }

    public TalkManager(EventManager eventManager){
        ArrayList<Event> talks = eventManager.upcomingEvents.get(EventType.TALK);
        this.upcomingEvents = new HashMap<>();
        upcomingEvents.put(EventType.TALK, talks);
    }

    @Override
    public void addSpeaker(UUID actID, String speakerName){
        Talk targetAct = findEvent(actID);
        targetAct.setSpeaker(speakerName);
    }

    @Override
    protected Talk findEvent(UUID actID) {
        Talk targetAct =  null;
        for(Event i: this.upcomingEvents.get(EventType.TALK)){
            if(i.getIdentity().equals(actID)){
                targetAct = (Talk) i;
            }
        }
        return targetAct;
    }

}
