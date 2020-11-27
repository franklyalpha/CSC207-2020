package UI;

import Controllers.RescheduleSpeakerController;
import Controllers.UserController;
import Presenters.Presenter;
import globallyAccessible.ActivityNotFoundException;
import globallyAccessible.NoActivitiesException;
import globallyAccessible.UserNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class OrgRescheduleSpeakerUI extends UserUI{
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
        Presenter.printSpeakers(freeSpeakers);
        while(true){
            try{
                Scanner speakerScanner = new Scanner(System.in);
                Presenter.printSpeakerAssignPrompt();
                String speaker = speakerScanner.nextLine();
                if (! freeSpeakers.contains(speaker)){
                    throw new UserNotFoundException("No such user in list");
                }
                return speaker;
            }catch(UserNotFoundException e){
                Presenter.printInvalid("speaker's name");
            }
        }
    }

    private String activitySelect() throws NoActivitiesException {
        ArrayList<String[]> allActivities = rescheduleSpeaker.getAllActivities();
        Presenter.printDescription("all activities");
        Presenter.printSchedule(allActivities);
        while(true){
            try{
                Scanner actIDGetter = new Scanner(System.in);
                Presenter.printChangeSpeakerIDPrompt();
                String actID = actIDGetter.nextLine();
                return rescheduleSpeaker.checkingValidActivityID(allActivities, actID);
            }catch(ActivityNotFoundException e){
                Presenter.printInvalid("activity ID");
            }
        }
    }




}
