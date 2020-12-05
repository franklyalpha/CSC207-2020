package useCases;

import entities.Party;

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
        super(eventManager);
    }

}
