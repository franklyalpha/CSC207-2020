package useCases;

import entities.Event;
import entities.Party;
import entities.Talk;
import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PartyManager extends EventManager {
    /**
     * This manager manages all of the Party events
     * Not much added functionality to be honest
     */

    /**
     * Creates a Party Manager that exclusively have Party Events in it
     * @param eventManager the original event manager
     */
    public PartyManager(EventManager eventManager){
        super(EventType.PARTY, eventManager.upcomingEvents.get(EventType.PARTY), eventManager.archivedEvents.get(EventType.PARTY));
    }

    /**
     * Return the type this manager is managing in String
     * @return the String "Party"
     */
    public String getTypeStr(){
        return "Party";
    }

    /**
     * Find the event this ID is representing
     * @param actID the ID of event we want to find
     * @return the Party event
     */
    @Override
    protected Party findEvent(UUID actID) {
        Party targetAct =  null;
        for(Event i: this.upcomingEvents.get(EventType.PARTY)){
            if(i.getIdentity().equals(actID)){
                targetAct = (Party) i;
            }
        }
        return targetAct;
    }
}
