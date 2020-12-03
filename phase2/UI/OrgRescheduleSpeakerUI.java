package UI;

import Controllers.RescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.ActivityNotFoundException;
import globallyAccessible.NoActivitiesException;
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
            String actID =activitySelect();
            ArrayList<String> availableSpeakers = rescheduleSpeaker.availableSpeakers(actID);
            String speaker = chooseSpeaker(availableSpeakers);
            rescheduleSpeaker.updateRescheduledSpeaker(speaker);
        }catch(NoActivitiesException e){
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

    private String activitySelect() throws NoActivitiesException {
        ArrayList<String[]> allActivities = rescheduleSpeaker.getAllActivities();
        System.out.println(orgRescheduleSpeakerPresenter.strAllActivityMenuDes());
        System.out.println(orgRescheduleSpeakerPresenter.strSchedule(allActivities));
        return inputSelection(allActivities);
    }

    private String inputSelection(ArrayList<String[]> allActivities) {
        while(true){
            try{
                String actID = inputActID();
                return rescheduleSpeaker.checkingValidActivityID(allActivities, actID);
            }catch(ActivityNotFoundException e){
                System.out.println(orgRescheduleSpeakerPresenter.strInvalidActivityID());
            }
        }
    }

    private String inputActID() {
        Scanner actIDGetter = new Scanner(System.in);
        System.out.println(orgRescheduleSpeakerPresenter.strActivityChangeSpeakerPrompt());
        return actIDGetter.nextLine();
    }
}
