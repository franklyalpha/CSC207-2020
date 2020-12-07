package UI;

import Controllers.PanelRescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.NoEventsException;

import java.util.ArrayList;
import java.util.Scanner;

public class OrganizerPanelSpeakerUI extends OrganizerRescheduleSpeakerUI {
    private PanelRescheduleSpeakerController panelRescheduleSpeakerController;

    public OrganizerPanelSpeakerUI(UserController userController) {
        super(userController);
        panelRescheduleSpeakerController = new PanelRescheduleSpeakerController(userController);
    }

    @Override
    public void run() {
        while(true){
            try{
                chooseAddDelete();
                break;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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

    private void addPanelSpeaker(){
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

    private void deletePanelSpeaker(){
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
