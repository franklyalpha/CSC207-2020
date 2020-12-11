package useCases;

import entities.Event;
import entities.Panel;
import globallyAccessible.EventType;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The PanelManager manages exclusively the Panel type of events
 * This has the added functionality of manipulating the speaker lists of each Panel event
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class PanelManager extends EventManager {

    /**
     * Creates a PanelManager that contains exclusively the Panel entities.
     * Take those entities from the original eventManager
     * @param eventManager the event manager that already contains all of the Events
     */
    public PanelManager(EventManager eventManager){
        super(EventType.PANEL, eventManager.upcomingEvents.get(EventType.PANEL), eventManager.archivedEvents.get(EventType.PANEL));
    }

    /**
     * add a speaker to the Panel
     * @param actID ID of event
     * @param speakerName Name of the speaker
     */
    @Override
    public void addSpeaker(UUID actID, String speakerName){
        Panel targetAct = findEvent(actID);
        ArrayList<String> speakers = targetAct.getSpeakers();
        speakers.add(speakerName);
        targetAct.setSpeakers(speakers);
    }

    /**
     * Delete a speaker from the Panel
     * @param actID The ID of the event
     * @param speakerName Name of the Speaker
     */
    public void deleteSpeaker(UUID actID, String speakerName){
        Panel targetAct = findEvent(actID);
        ArrayList<String> speakers = targetAct.getSpeakers();
        speakers.remove(speakerName);
        targetAct.setSpeakers(speakers);
    }

    /**
     * changes one of the speakers to another
     * @param actID ID of the event
     * @param deleteName Name of the speaker to be removed
     * @param addName Name of the speaker to be added
     */
    public void changeSpeaker(UUID actID, String deleteName, String addName){
        deleteSpeaker(actID, deleteName);
        addSpeaker(actID, addName);
    }

    /**
     * Return the list of speakers of the Panel
     * @param actID ID of the event
     * @return the list containing all the speakers
     */
    public ArrayList<String> getSpeakers(UUID actID){
        return findEvent(actID).getSpeakers();
    }

    /**
     * Return the type this manager manages in string
     * @return the String "Panel"
     */
    public String getTypeStr(){
        return "Panel";
    }

    /**
     * find the event associated with this ID
     * @param actID the ID of event we want to find
     * @return the Panel event
     */
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

    /**
     * getter for the speaker list with this ID
     * @param panelID ID in String
     * @return The String list containing the speaker names
     */
    public ArrayList<String> getSpeakersList(String panelID){
        return findEvent(UUID.fromString(panelID)).getSpeakers();
    }

}
