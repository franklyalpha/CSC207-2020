package controllers;

import presenter.Presenter;
import useCases.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class SpeakerController extends OrganizerController {

    public SpeakerController(UserManager manager){
        super(manager);
    }
    /*
    require implementation:
    (view enrolled schedule is implemented in general userController)
    send group message
     */

    protected void sendActivityMessage(){
        ArrayList<String[]> info = showEnrolledSchedule();
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

    public void run() {
        boolean stop = false;
        while (!stop){
            Scanner choice = new Scanner(System.in);
            //System.out.println("Actions available: 1: view private message; 2: send private message; 3: " +
            //        "view coop message; 4: send coop message; 5: send activity message; 6: view enrolled schedules" +
            //        "; 7: view activity message; ");
            Presenter.printSpeakerActions();
            int action = choice.nextInt();
            switch (action){
                case 1 : viewPrivateMessage();
                break;
                case 2 : sendPrivateMessage();
                break;
                case 3 : viewCoopChat();
                break;
                case 4 : sendCoopMessage();
                break;
                case 5 : sendActivityMessage();
                break;
                case 6 : viewEnrolledSchedule();
                break;
                case 7 : viewGroupMessage();
                break;
            }

            Scanner stopScanner = new Scanner(System.in);
            //System.out.println("logout?");
            Presenter.printLogoutPrompt();
            if (stopScanner.hasNextBoolean()){
                stop = stopScanner.nextBoolean();
            }
        }
        logout();

    }


}


