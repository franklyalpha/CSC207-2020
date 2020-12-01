package UI;

import Controllers.RescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.ActivityNotFoundException;
import globallyAccessible.NoActivitiesException;
import globallyAccessible.UserNotFoundException;
import menuPresenter.OrgRescheduleSpeakerPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class OrgRescheduleSpeakerUI extends UserUI{

    final private OrgRescheduleSpeakerPresenter orgRescheduleSpeakerPresenter = new OrgRescheduleSpeakerPresenter();

    private RescheduleSpeakerController rescheduleSpeaker;

    public OrgRescheduleSpeakerUI(UserController userController) {
        super(userController);
        rescheduleSpeaker = new RescheduleSpeakerController(userController);
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
                Scanner speakerScanner = new Scanner(System.in);
                System.out.println(orgRescheduleSpeakerPresenter.strSpeakerAssignPrompt());
                String speaker = speakerScanner.nextLine();
                if (! freeSpeakers.contains(speaker)){
                    throw new UserNotFoundException("No such user in list");
                }
                return speaker;
            }catch(UserNotFoundException e){
                System.out.println(orgRescheduleSpeakerPresenter.strInvalidSpeaker());
            }
        }
    }

    private String activitySelect() throws NoActivitiesException {
        ArrayList<String[]> allActivities = rescheduleSpeaker.getAllActivities();
        System.out.println(orgRescheduleSpeakerPresenter.strAllActivityMenuDes());
        System.out.println(orgRescheduleSpeakerPresenter.strSchedule(allActivities));
        while(true){
            try{
                Scanner actIDGetter = new Scanner(System.in);
                System.out.println(orgRescheduleSpeakerPresenter.strActivityChangeSpeakerPrompt());
                String actID = actIDGetter.nextLine();
                return rescheduleSpeaker.checkingValidActivityID(allActivities, actID);
            }catch(ActivityNotFoundException e){
                System.out.println(orgRescheduleSpeakerPresenter.strInvalidActivityID());
            }
        }
    }




}
