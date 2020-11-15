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

    private void sendActivityMessage(){
        HashMap<LocalDateTime[], UUID> schedulesEnrolled = userManager.schedules();
        ArrayList<String[]> info = new ArrayList<>();
        for (UUID actID: schedulesEnrolled.values()){
            info.add(activityManager.searchActivityByUUID(actID.toString()));
        }
        // presenter: printSchedule
        System.out.println("here are activities you've been assigned: "
                + extractActIDHelper(info));
        //
        Scanner actIDScanner = new Scanner(System.in);
        //System.out.println("please input the ith activity you wish to send " +
        //        "a message (e.g: the 1st in the list, then type 1):");
        Presenter.printActivityMessagePrompt();
        int actID = actIDScanner.nextInt();
        if (actID < 1 || actID > info.size()){
            //System.out.println("invalid input!!! try again later. ");
            Presenter.printInvalid("input");
        }
        UUID chatID = activityManager.getConferenceChat(UUID.fromString(info.get(actID - 1)[0]));
        Scanner messageScanner = new Scanner(System.in);
        //System.out.println("Please input the message you wish to send:");
        Presenter.printMessagePrompt();
        String message = messageScanner.nextLine();
        chatroomManager.sendMessage(message, chatID);
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
                case 2 : sendPrivateMessage();
                case 3 : viewCoopChat();
                case 4 : sendCoopMessage();
                case 5 : sendActivityMessage();
                case 6 : viewEnrolledSchedule();
                case 7 : viewGroupMessage();
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


