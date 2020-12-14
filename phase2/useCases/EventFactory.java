package useCases;

import entities.*;
import globallyAccessible.EventType;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a <code>EventFactory</code> that is responsible for sorting and creating Events
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class EventFactory {
    private final EventManager eventManager;

    /**
     * Creates the EventFactory
     * @param eventManager the manager that it is based on
     */
    public EventFactory(EventManager eventManager){
        this.eventManager = eventManager;
    }

    /**
     * Constructs a Event entity and stores it into the EventManager.
     * @param period period this event is going to be hosted through
     * @param roomID ID of the message room and event room
     * @param topic topic of this event
     * @param MaxNum Max number of attendees
     * @param eventType type of the event
     * @return UUID of the event
     */
    public UUID construct(LocalDateTime[] period, UUID[] roomID, String topic,
                          Integer MaxNum, EventType eventType){
        switch (eventType) {
            case TALK:
                Talk newTalk = new Talk(period, roomID, topic, MaxNum);
                return eventManager.addEvent(newTalk, EventType.TALK);
            case PANEL:
                Panel newPanel = new Panel(period, roomID, topic, MaxNum);
                return eventManager.addEvent(newPanel, EventType.PANEL);
            case PARTY:
                Party newParty = new Party(period, roomID, topic, MaxNum);
                return eventManager.addEvent(newParty, EventType.PARTY);
        }
        return null;
    }
}