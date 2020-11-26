package ActivityControllers;

import globallyAccessible.ActivityNotFoundException;
import globallyAccessible.UserNotFoundException;
import presenter.Presenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class AdministrativeActivityController extends UserActivityController{
    public AdministrativeActivityController(Object[] managers) {
        super(managers);
    }

    public void rescheduleSpeaker() {
        String actID = activitySelect();
        if(actID.equals("")){
            return;
        }
        String[] actInfo = activityManager.searchActivityByUUID(actID);
        LocalDateTime[] actTime = getTimeHelper(actInfo);
        String speaker = chooseSpeaker(actInfo, actTime);
        if (speaker.equals("")){
            return;
        }
        updateRescheduledSpeaker(actInfo, actTime, speaker);
    }

    private void updateRescheduledSpeaker(String[] actInfo, LocalDateTime[] actTime, String speaker){
        activityManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        userManager.deleteActivity(actInfo[5], actTime);
        userManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
    }

    private String chooseSpeaker(String[] actInfo, LocalDateTime[] actTime){
        ArrayList<String> freeSpeakers = userManager.availableSpeakers(actTime);
        freeSpeakers.add(actInfo[5]);
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

    private String activitySelect(){
        ArrayList<String[]> allActivities = activityManager.viewUpcommingActivites();
        if (allActivities.size() == 0){
            return "";
        }
        Presenter.printDescription("all activities");
        Presenter.printSchedule(allActivities);
        while(true){
            try{
                return checkingValidActivityID(allActivities);
            }catch(ActivityNotFoundException e){
                Presenter.printInvalid("activity ID");
            }
        }
    }

    private String checkingValidActivityID(ArrayList<String[]> allActivities)
            throws ActivityNotFoundException {
        Scanner actIDGetter = new Scanner(System.in);
        Presenter.printChangeSpeakerIDPrompt();
        String actID = actIDGetter.nextLine();
        if (! extractActIDHelper(allActivities).contains(actID)){
            throw new ActivityNotFoundException("invalid activity ID");
        }
        return actID;
    }
}
