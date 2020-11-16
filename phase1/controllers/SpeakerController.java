package controllers;

import presenter.Presenter;
import useCases.UserManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class SpeakerController extends OrganizerController {
    private ArrayList<String> availableAction = new ArrayList<>();
    private ArrayList<String> availableMethod = new ArrayList<>();

    public SpeakerController(UserManager manager){
        super(manager);
    }
    /*
    require implementation:
    (view enrolled schedule is implemented in general userController)
    send group message
     */

    public void run() {
        addActions();
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
        logout();
    }

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

    private void addMenu(){
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("view group messages");
        availableAction.add("send group messages");
        availableAction.add("send messages in coopChatroom");
        availableAction.add("view messages from coopChatroom");
        availableAction.add("view signed conferences");
    }

    private void addActions(){
        availableMethod.add("sendPrivateMessage");
        availableMethod.add("viewPrivateMessage");
        availableMethod.add("viewGroupMessage");
        availableMethod.add("sendActivityMessage");
        availableMethod.add("sendCoopMessage");
        availableMethod.add("viewCoopChat");
        availableMethod.add("viewEnrolledSchedule");
    }


    protected void sendActivityMessage(){
        ArrayList<String[]> info = showEnrolledSchedule();
        if (info.size() == 0){
            Presenter.printNotEnrolled();
            return;
        }
        UUID chatID = findRightChat(info);
        Scanner messageScanner = new Scanner(System.in);
        Presenter.printMessagePrompt();
        String message = messageScanner.nextLine();
        chatroomManager.sendMessage(message, chatID);
    }

    private ArrayList<String[]> showEnrolledSchedule(){
        HashMap<LocalDateTime[], UUID> schedulesEnrolled = userManager.schedules();
        ArrayList<String[]> info = new ArrayList<>();
        for (UUID actID: schedulesEnrolled.values()){
            info.add(activityManager.searchActivityByUUID(actID.toString()));
        }
        Presenter.printDescription("activities you've enrolled");
        Presenter.printSchedule(info);
        return info;
    }

    private UUID findRightChat(ArrayList<String[]> info){
        Scanner actIDScanner = new Scanner(System.in);
        Presenter.printActivityMessagePrompt();
        int actID = actIDScanner.nextInt();
        if (actID < 1 || actID > info.size()){
            Presenter.printInvalid("input");
        }
        UUID chatID = activityManager.getConferenceChat(
                UUID.fromString(info.get(actID - 1)[0]));
        return chatID;
    }


    private boolean continuing(){
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


