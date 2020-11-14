package controllers;

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
        HashMap<LocalDateTime[], UUID> schedulesEnrolled = userma.schedules();
        ArrayList<String[]> info = new ArrayList<String[]>();
        for (UUID actID: schedulesEnrolled.values()){
            info.add(actmanag.searchActivityByUUID(actID.toString()));
        }
        // presenter
        System.out.println("here are activities you've been assigned: "
                + extractActIDHelper(info));
        //
        Scanner actIDScanner = new Scanner(System.in);
        System.out.println("please input the ith activity you wish to send " +
                "a message (e.g: the 1st in the list, then type 1):");
        int actID = actIDScanner.nextInt();
        if (actID < 1 || actID > info.size()){
            System.out.println("invalid input!!! try again later. ");
        }
        UUID chatID = actmanag.getConferenceChat(UUID.fromString(info.get(actID - 1)[0]));
        Scanner messageScanner = new Scanner(System.in);
        System.out.println("Please input the message you wish to send:");
        String message = messageScanner.nextLine();
        chatmana.sendMessage(message, chatID);
    }


}
