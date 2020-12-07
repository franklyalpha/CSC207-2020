package UI;

import Controllers.PanelRescheduleSpeakerController;
import Controllers.SpeakerReschedulingController;
import Controllers.TalkRescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.EventNotFoundException;
import globallyAccessible.NoEventsException;
import globallyAccessible.UserNotFoundException;
import menuPresenter.OrganizerRescheduleSpeakerPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class OrganizerRescheduleSpeakerUI extends AbstractUI {

    final protected OrganizerRescheduleSpeakerPresenter organizerRescheduleSpeakerPresenter;

    public OrganizerRescheduleSpeakerUI(UserController userController) {
        super(userController);
        organizerRescheduleSpeakerPresenter = new OrganizerRescheduleSpeakerPresenter();
    }

    @Override
    public void run() {
        int choice = typeSelection();
        switch(choice){
            case 0: new OrganizerTalkSpeakerUI(userController).run(); break;
            case 1: new OrganizerPanelSpeakerUI(userController).run(); break;
        }
    }

    protected String chooseSpeaker(ArrayList<String> freeSpeakers, String action){
        System.out.println(organizerRescheduleSpeakerPresenter.strSpeakerList(freeSpeakers));
        while(true){
            try{
                return inputSelectedSpeaker(freeSpeakers, action);
            }catch(UserNotFoundException e){
                System.out.println(organizerRescheduleSpeakerPresenter.strInvalidSpeaker());
            }
        }
    }

    protected String inputSelectedSpeaker(ArrayList<String> freeSpeakers, String action) throws UserNotFoundException {
        Scanner speakerScanner = new Scanner(System.in);
        System.out.println(organizerRescheduleSpeakerPresenter.strSpeakerAssignPrompt(action));
        String speaker = speakerScanner.nextLine();
        if (! freeSpeakers.contains(speaker)){
            throw new UserNotFoundException("No such user in list");
        }
        return speaker;
    }


    private int typeSelection() {
        while(true){
            organizerRescheduleSpeakerPresenter.strActivityTypePrompt();
            Scanner typeScanner = new Scanner(System.in);
            if(typeScanner.hasNextInt()){
                Integer choice = determineTypeChoiceValidity(typeScanner);
                if (choice != null) return choice;
            }
            System.out.println(organizerRescheduleSpeakerPresenter.strInvalidInput());
        }
    }

    private Integer determineTypeChoiceValidity(Scanner typeScanner) {
        int choice = typeScanner.nextInt();
        if (choice == 0 || choice == 1){
            return choice;
        }
        return null;
    }

    protected String inputActID() {
        Scanner actIDGetter = new Scanner(System.in);
        System.out.println(organizerRescheduleSpeakerPresenter.strEventChangeSpeakerPrompt());
        return actIDGetter.nextLine();
    }

    protected String inputSelection(ArrayList<String[]> allActivities, SpeakerReschedulingController controller) {
        while(true){
            try{
                String actID = inputActID();
                return controller.checkingValidEventID(allActivities, actID);
            }catch(EventNotFoundException e){
                System.out.println(organizerRescheduleSpeakerPresenter.strInvalidEventID());
            }
        }
    }
}
