package useCases;

import entities.Event;
import entities.Panel;
import globallyAccessible.EventType;

import java.util.ArrayList;
import java.util.UUID;

public class PanelManager extends EventManager {
    /*
    Additional Variables:
    None

    Additional Methods:
    Speaker manipulators
    Finder
     */

    public PanelManager(EventManager eventManager){
        super(eventManager);
    }

    public void addSpeaker(UUID actID, String speakerName){
        Panel targetAct = findEvent(actID);
        ArrayList<String> speakers = targetAct.getSpeakers();
        speakers.add(speakerName);
        targetAct.setSpeakers(speakers);
    }

    public void deleteSpeaker(UUID actID, String speakerName){
        Panel targetAct = findEvent(actID);
        ArrayList<String> speakers = targetAct.getSpeakers();
        speakers.remove(speakerName);
        targetAct.setSpeakers(speakers);
    }

    public void changeSpeaker(UUID actID, String deleteName, String addName){
        deleteSpeaker(actID, deleteName);
        addSpeaker(actID, addName);
    }

    @Override
    protected Panel findEvent(UUID actID) {
        Panel targetAct =  null;
        for(Event i: this.upcomingEvents.get(EventType.PANEL)){
            if(i.getIdentity().equals(actID)){
                targetAct = (Panel) i;
            }
        }
        return targetAct;
    }

}
