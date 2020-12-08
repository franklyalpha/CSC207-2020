package UI;

import Controllers.RemoveEventController;
import Controllers.UserController;
import globallyAccessible.NoEventsException;
import menuPresenter.RemoveEventPresenter;
import java.util.Scanner;
import java.util.UUID;

package useCases;

import UI.AbstractUI;
import entities.Event;
import entities.Panel;
import entities.Talk;
import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class OrganizerModifyEventUI extends AbstractUI {
    private RemoveEventController removeEvent;

    public OrganizerModifyEventUI(UserController userController) {
        super(userController);
        removeEvent = new RemoveEventController(userController);
    }

    @Override
    public void run() {
        RemoveEventPresenter presenter = new RemoveEventPresenter();
        try{
            inputAndUpdateModification(removeEvent, presenter);
        }catch (NoEventsException e){
            presenter.printNoEvent();
        }
    }

    private void inputAndUpdateModification(RemoveEventController modifyActivity, RemoveEventPresenter presenter)
            throws NoEventsException {
        presenter.printIDForTheEventToBeCancelled(removeEvent.getAllActivities());
        Scanner input_1 = new Scanner(System.in);
        UUID activityId = UUID.fromString(input_1.nextLine());
        removeEvent.cancelEvent(activityId);
    }
}

public class TalkManager extends EventManager {
    /*
    Additional Variables:
    None

    Additional Methods:
    Speaker manipulators
    Finder
     */

    public TalkManager(EventManager eventManager){
        super(eventManager);
    }

    public void setSpeaker(UUID actID, String speakerName){
        Talk targetAct = findEvent(actID);
        targetAct.setSpeaker(speakerName);
    }

    public void deleteSpeaker(UUID actID){
        Talk targetAct = findEvent(actID);
        targetAct.setSpeaker(null);
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

