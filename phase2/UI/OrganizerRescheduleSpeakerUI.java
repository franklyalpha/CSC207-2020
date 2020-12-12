package UI;

import Controllers.PanelRescheduleSpeakerController;
import Controllers.SpeakerReschedulingController;
import Controllers.TalkRescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.EventNotFoundException;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.NoEventsException;
import globallyAccessible.UserNotFoundException;
import menuPresenter.OrganizerRescheduleSpeakerPresenter;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * UI for <code>Organizer</code> to reschedule events for Speaker<code>User</code>.
 */
public class OrganizerRescheduleSpeakerUI extends AbstractUI {
    /**
     * An instance of <code>OrganizerRescheduleSpeakerPresenter</code>
     * being instantiated.
     */
    final protected OrganizerRescheduleSpeakerPresenter organizerRescheduleSpeakerPresenter;

    /**
     * Instantiates new <code>OrganizerRescheduleSpeakerUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OrganizerRescheduleSpeakerUI(UserController userController) {
        super(userController);
        organizerRescheduleSpeakerPresenter = new OrganizerRescheduleSpeakerPresenter();
    }

    /**
     * Chooses the type of speaker <code>Organizer</code> wants to reschedule events.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        int choice = typeSelection();
        switch(choice){
            case 0: new OrganizerTalkSpeakerUI(userController).run(); break;
            case 1: new OrganizerPanelSpeakerUI(userController).run(); break;
        }
    }

    /**
     * Chooses the speaker <code>Organizer</code> wants to reschedule events.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    protected String chooseSpeaker(ArrayList<String> freeSpeakers, String action) throws ExceedingMaxAttemptException {
        System.out.println(organizerRescheduleSpeakerPresenter.strSpeakerList(freeSpeakers));
        for(int i=0; i<3; i++){
            try{
                return inputSelectedSpeaker(freeSpeakers, action);
            }catch(UserNotFoundException e){
                System.out.println(organizerRescheduleSpeakerPresenter.strInvalidSpeaker());
            }
        }
        throw new ExceedingMaxAttemptException("Maximum number of attempts exceeded");
    }

    /**
     * Lets <code>Organizer</code> inputs speaker which wants to reschedule.
     * @return The ID of this speaker
     */

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
            System.out.println(organizerRescheduleSpeakerPresenter.strActivityTypePrompt());
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
    /**
     * Lets <code>Organizer</code> inputs event's ID which wants to reschedule.
     * @return The ID of this event.
     */

    protected String inputActID() {
        Scanner actIDGetter = new Scanner(System.in);
        System.out.println(organizerRescheduleSpeakerPresenter.strEventChangeSpeakerPrompt());
        return actIDGetter.nextLine();
    }

    /**
     * Lets <code>Organizer</code> inputs selection which wants to reschedule.
     * @return an instance of <>String<> representing the <>Event<>.
     */
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
