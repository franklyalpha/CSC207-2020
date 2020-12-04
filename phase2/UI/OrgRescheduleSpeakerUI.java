package UI;

import Controllers.RescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.EventNotFoundException;
import globallyAccessible.NoEventsException;
import globallyAccessible.UserNotFoundException;
import menuPresenter.OrgRescheduleSpeakerPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class OrgRescheduleSpeakerUI extends AbstractUI {

    final private OrgRescheduleSpeakerPresenter orgRescheduleSpeakerPresenter;
    private RescheduleSpeakerController rescheduleSpeaker;

    public OrgRescheduleSpeakerUI(UserController userController) {
        super(userController);
        rescheduleSpeaker = new RescheduleSpeakerController(userController);
        orgRescheduleSpeakerPresenter = new OrgRescheduleSpeakerPresenter();
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
        System.out.println(orgRescheduleSpeakerPresenter.strSpeakerList(freeSpeakers));
        while(true){
            try{
                return inputSelectedSpeaker(freeSpeakers);
            }catch(UserNotFoundException e){
                System.out.println(orgRescheduleSpeakerPresenter.strInvalidSpeaker());
            }
        }
    }

    private String inputSelectedSpeaker(ArrayList<String> freeSpeakers) throws UserNotFoundException {
        Scanner speakerScanner = new Scanner(System.in);
        System.out.println(orgRescheduleSpeakerPresenter.strSpeakerAssignPrompt());
        String speaker = speakerScanner.nextLine();
        if (! freeSpeakers.contains(speaker)){
            throw new UserNotFoundException("No such user in list");
        }
        return speaker;
    }

    private String eventSelect() throws NoEventsException {
        ArrayList<String[]> allActivities = rescheduleSpeaker.getAllActivities();
        System.out.println(orgRescheduleSpeakerPresenter.strAllEventMenuDes());
        System.out.println(orgRescheduleSpeakerPresenter.strSchedule(allActivities));
        return inputSelection(allActivities);
    }

    private String inputSelection(ArrayList<String[]> allActivities) {
        while(true){
            try{
                String actID = inputActID();
                return rescheduleSpeaker.checkingValidEventID(allActivities, actID);
            }catch(EventNotFoundException e){
                System.out.println(orgRescheduleSpeakerPresenter.strInvalidEventID());
            }
        }
    }

    private String inputActID() {
        Scanner actIDGetter = new Scanner(System.in);
        System.out.println(orgRescheduleSpeakerPresenter.strEventChangeSpeakerPrompt());
        return actIDGetter.nextLine();
    }
}
