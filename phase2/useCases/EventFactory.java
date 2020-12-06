package useCases;

import entities.*;
import globallyAccessible.EventType;
import globallyAccessible.InvalidUserTypeException;
import globallyAccessible.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventFactory {
    private EventManager eventManager;

    public EventFactory(EventManager eventManager){
        this.eventManager = eventManager;
    }

    public UUID construct(LocalDateTime[] period, UUID[] chatRoomID, String topic,
                          Integer MaxNum, EventType eventType){
        switch (eventType) {
            case TALK:
                Talk newTalk = new Talk(period, chatRoomID, topic, MaxNum);
                return eventManager.addEvent(newTalk, EventType.TALK);
            case PANEL:
                Panel newPanel = new Panel(period, chatRoomID, topic, MaxNum);
                return eventManager.addEvent(newPanel, EventType.PANEL);
            case PARTY:
                Party newParty = new Party(period, chatRoomID, topic, MaxNum);
                return eventManager.addEvent(newParty, EventType.PARTY);
        }
        return null;
    }
}