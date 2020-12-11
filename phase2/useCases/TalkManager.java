package useCases;

import entities.Event;
import entities.Talk;
import globallyAccessible.EventType;

import java.util.UUID;

/**
 * A Manager that manages talks and talks only
 * Added functionality: viewing, adding, and deleting speaker
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class TalkManager extends EventManager {

    /**
     * Creates a Talk manager that only have Talk events
     * @param eventManager the original event Manager where we take the events entities from
     */
    public TalkManager(EventManager eventManager){
        super(EventType.TALK, eventManager.upcomingEvents.get(EventType.TALK), eventManager.archivedEvents.get(EventType.TALK));
    }

    /**
     * adding a speaker
     * @param actID ID of event
     * @param speakerName Name of the speaker
     */
    @Override
    public void addSpeaker(UUID actID, String speakerName){
        Talk targetAct = findEvent(actID);
        targetAct.setSpeaker(speakerName);
    }

    /**
     * Deleting the speaker
     * @param actID ID of event
     */
    public void deleteSpeaker(UUID actID){
        Talk targetAct = findEvent(actID);
        targetAct.setSpeaker(null);
    }

    /**
     * get the speaker from the talk
     * @param actID ID of event
     * @return the String representing the speaker
     */
    public String getSpeaker(UUID actID){
        return findEvent(actID).getSpeaker();
    }

    /**
     * get the type that this manager is managing in String form
     * @return the String "Talk"
     */
    public String getTypeStr(){
        return "Talk";
    }

    /**
     * Find the event this ID is representing
     * @param actID the ID of event we want to find
     * @return the Talk event
     */
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
