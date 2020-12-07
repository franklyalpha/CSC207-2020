package useCases;

import entities.Party;
import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PartyManager extends EventManager {
    /*
    Additional Variables:
    None

    Additional Methods:
    Finder
     */

    public PartyManager(EventManager eventManager){
        super();
        this.upcomingEvents.put(EventType.PARTY, eventManager.upcomingEvents.get(EventType.PARTY));
        this.archivedEvents.put(EventType.PARTY, eventManager.archivedEvents.get(EventType.PARTY));
    }

    public String getTypeStr(){
        return "Party";
    }

}
