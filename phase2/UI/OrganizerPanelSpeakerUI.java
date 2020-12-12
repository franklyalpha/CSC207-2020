package UI;

import Controllers.PanelRescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.NoEventsException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * UI for <code>Organizer</code> to reschedule event for panel speaker.
 */

public class OrganizerPanelSpeakerUI extends OrganizerRescheduleSpeakerUI {
    /**
     * an instance of <code>PanelRescheduleSpeakerController</code>
     * being instantiated.
     */
    private PanelRescheduleSpeakerController panelRescheduleSpeakerController;

    /**
     * Instantiates new <code>OrganizerPanelSpeakerUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OrganizerPanelSpeakerUI(UserController userController) {
        super(userController);
        panelRescheduleSpeakerController = new PanelRescheduleSpeakerController(userController);
    }

    /**
     * Lets <code>Organizer</code> choose to add or delete panel speaker
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        for(int i=0; i<3; i++){
            try{
                chooseAddDelete();
                break;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        throw new ExceedingMaxAttemptException("Exceeding maximum attempt times");
    }

    private void chooseAddDelete() throws Exception {
        System.out.println(organizerRescheduleSpeakerPresenter.strChooseAddDeleteSpeakerPrompt());
        Scanner choice = new Scanner(System.in);
        if (choice.hasNextInt()){
            switch(choice.nextInt()){
                case 0: addPanelSpeaker(); break;
                case 1: deletePanelSpeaker(); break;
                default: throw new Exception("invalid input");
            }
        }
    }

    private void addPanelSpeaker() throws ExceedingMaxAttemptException {
        try{
            String actID = eventSelect();
            ArrayList<String> availableSpeakers = panelRescheduleSpeakerController.availableSpeakers(actID);
            String speaker = chooseSpeaker(availableSpeakers, "assign");
            panelRescheduleSpeakerController.addNewSpeaker(speaker);
        } catch(NoEventsException e) {
            e.printStackTrace();
        }
    }

    private String eventSelect() throws NoEventsException {
        ArrayList<String[]> allActivities = panelRescheduleSpeakerController.getAllActivities();
        System.out.println(organizerRescheduleSpeakerPresenter.strAllEventMenuDes());
        System.out.println(organizerRescheduleSpeakerPresenter.strSchedule(allActivities));
        return inputSelection(allActivities, panelRescheduleSpeakerController);
    }

    private void deletePanelSpeaker() throws ExceedingMaxAttemptException {
        try{
            String actID = eventSelect();
            ArrayList<String> existingSpeakers = panelRescheduleSpeakerController.enrolledSpeakers(actID);
            String speaker = chooseSpeaker(existingSpeakers, "delete");
            panelRescheduleSpeakerController.deleteSpeaker(speaker);
        } catch(NoEventsException e) {
            e.printStackTrace();
        }
    }
}
