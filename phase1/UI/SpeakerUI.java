package UI;

import Controllers.SendActivityMessageController;
import Controllers.UserController;
import Presenters.Presenter;
import org.jetbrains.annotations.Nullable;
import useCases.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a <code>SpeakerController</code> extends from <code>UserController</code>.
 * Is specific for <code>Speaker</code> type usage.
 *
 * Includes:
 * Own constructor
 * sendActivityMessage: a method responsible for guiding the user to choose a conference
 * and send message to all attendees enrolled.
 */
public class SpeakerUI extends OrganizerUI2{
    public SpeakerUI(UserController userController) {
        super(userController);
    }
    /**
     * an Arraylist of <code>availableAction</code>;
     * an Arraylist of <code>availableMethod</code>;
     */
    /*
    require implementation:
    (view enrolled schedule is implemented in general userController)
    send group message
     */

    /**
     * This method allows users to do actions corresponding to speaker's allowed actions.
     * Will print out a list of actions the user can implement, ask for choice of action the user
     * want to do and call corresponding method.
     */
    public void run() {
        addMenu();
        int action;
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            /*System.out.println("Services apply\n");
            for(String a: availableAction){
                System.out.println(availableAction.indexOf(a)+1 + ": " + a);

            }*/
            Presenter.printAvailableActions(availableAction);
            action = scan.nextInt();
            if (0 < action && action <= availableMethod.size()) {
                runMethod(action);
            }
            else{
                Presenter.printInvalid("input");
            }
            enterAction = continuing();
        }
        userController.logout();
    }

    /**
     * This method takes user's intent(action) to let user choose from given actions, those
     * actions are self-explained.
     * @param action that user can choose from.
     */

    private void runMethod (int action){
        switch(action){
            case 1: sendPrivateMessage(); break;
            case 2: viewPrivateMessage(); break;
            case 3: viewGroupMessage(); break;
            case 4: sendActivityMessage(); break;
            case 5: sendCoopMessage(); break;
            case 6: viewCoopChat(); break;
            case 7: viewEnrolledSchedule(); break;
        }
    }

    /**
     * This method add actions to the class attribute availableAction.
     */
    @Override
    protected void addMenu(){
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("view group messages");
        availableAction.add("send group messages");
        availableAction.add("send messages in coopChatroom");
        availableAction.add("view messages from coopChatroom");
        availableAction.add("view signed conferences");
    }

    protected void sendActivityMessage(){
        SendActivityMessageController activityMessager = new SendActivityMessageController(userController);
        ArrayList<String[]> info = presentEnrolledActivities(activityMessager);
        if (info == null) return;
        while(true){
            try{
                findAndSendMessage(activityMessager, info);
                break;
            }catch(IndexOutOfBoundsException e){
                Presenter.printInvalid("index of chat list");
            }
        }
    }

    private ArrayList<String[]> presentEnrolledActivities(SendActivityMessageController activityMessager) {
        ArrayList<String[]> info = activityMessager.showEnrolledSchedule();
        if (info.size() == 0){
            return null;
        }
        Presenter.printDescription("activities you've enrolled");
        Presenter.printSchedule(info);
        return info;
    }

    private void findAndSendMessage(SendActivityMessageController activityMessager, ArrayList<String[]> info) {
        int actID = determineChatIDValidity(info);
        Scanner messageScanner = new Scanner(System.in);
        Presenter.printMessagePrompt();
        String message = messageScanner.nextLine();
        activityMessager.sendActivityMessage(actID, message);
    }

    private int determineChatIDValidity(ArrayList<String[]> info)
            throws IndexOutOfBoundsException{
        Scanner actIDScanner = new Scanner(System.in);
        Presenter.printActivityMessagePrompt();
        int actID = actIDScanner.nextInt();
        if (actID < 1 || actID > info.size()){
            throw new IndexOutOfBoundsException("invalid index for chat");
        }
        return actID;
    }

    /**
     * This method add actions to the class attribute availableMethod.
     */



    /**
     * The method providing instructions for user to choose a conference they've assigned/enrolled
     * and send a message to all other users participated in this activity.
     */

    @Override
    protected boolean continuing(){
        boolean enterAction = true;
        //System.out.println("Continue for other services? Please enter true or false. (false for log out)");
        Presenter.printContinueServicePrompt();
        Scanner scan2 = new Scanner(System.in);
        if(!scan2.nextLine().equals("true")){
            enterAction = false;
        }
        return enterAction;
    }




}


