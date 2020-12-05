package UI;

import Controllers.RescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.EventNotFoundException;
import globallyAccessible.NoEventsException;
import globallyAccessible.UserNotFoundException;
import menuPresenter.OrganizerRescheduleSpeakerPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class OrganizerRescheduleSpeakerUI extends AbstractUI {

    final private OrganizerRescheduleSpeakerPresenter organizerRescheduleSpeakerPresenter;
    private RescheduleSpeakerController rescheduleSpeaker;

    public OrganizerRescheduleSpeakerUI(UserController userController) {
        super(userController);
        rescheduleSpeaker = new RescheduleSpeakerController(userController);
        organizerRescheduleSpeakerPresenter = new OrganizerRescheduleSpeakerPresenter();
    }

    @Override
    public void run() {
        try{
            String actID = eventSelect();
            ArrayList<String> availableSpeakers = rescheduleSpeaker.availableSpeakers(actID);
            String speaker = chooseSpeaker(availableSpeakers);
            rescheduleSpeaker.updateRescheduledSpeaker(speaker);
        }catch(NoEventsException e){
            e.printStackTrace();
        }
    }

    private String chooseSpeaker(ArrayList<String> freeSpeakers){
        System.out.println(organizerRescheduleSpeakerPresenter.strSpeakerList(freeSpeakers));
        while(true){
            try{
                return inputSelectedSpeaker(freeSpeakers);
            }catch(UserNotFoundException e){
                System.out.println(organizerRescheduleSpeakerPresenter.strInvalidSpeaker());
            }
        }
    }

    private String inputSelectedSpeaker(ArrayList<String> freeSpeakers) throws UserNotFoundException {
        Scanner speakerScanner = new Scanner(System.in);
        System.out.println(organizerRescheduleSpeakerPresenter.strSpeakerAssignPrompt());
        String speaker = speakerScanner.nextLine();
        if (! freeSpeakers.contains(speaker)){
            throw new UserNotFoundException("No such user in list");
        }
        return speaker;
    }

    private String eventSelect() throws NoEventsException {
        ArrayList<String[]> allActivities = rescheduleSpeaker.getAllActivities();
        System.out.println(organizerRescheduleSpeakerPresenter.strAllEventMenuDes());
        System.out.println(organizerRescheduleSpeakerPresenter.strSchedule(allActivities));
        return inputSelection(allActivities);
    }

    private String inputSelection(ArrayList<String[]> allActivities) {
        while(true){
            try{
                String actID = inputActID();
                return rescheduleSpeaker.checkingValidEventID(allActivities, actID);
            }catch(EventNotFoundException e){
                System.out.println(organizerRescheduleSpeakerPresenter.strInvalidEventID());
            }
        }
    }

    private String inputActID() {
        Scanner actIDGetter = new Scanner(System.in);
        System.out.println(organizerRescheduleSpeakerPresenter.strEventChangeSpeakerPrompt());
        return actIDGetter.nextLine();
    }
}
