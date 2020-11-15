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
        try {
            Method method = this.getClass().getDeclaredMethod(availableMethod.get(action - 1));
            try {
                method.invoke(this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void addMenu(){
        availableAction.add("create conference room");
        availableAction.add("create speaker account");
        availableAction.add("schedule conference");
        availableAction.add("reschedule speaker");
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("view group messages");
        availableAction.add("send messages in coopChatroom");
        availableAction.add("view messages from coopChatroom");
        availableAction.add("message all attendees");
        availableAction.add("view singed conferences");

    }

    private void addActions(){
        availableMethod.add("createRoom");
        availableMethod.add("createSpeaker");
        availableMethod.add("addSchedule");
        availableMethod.add("rescheduleSpeaker");
        availableMethod.add("sendPrivateMessage");
        availableMethod.add("viewPrivateMessage");
        availableMethod.add("viewGroupMessage");
        availableMethod.add("sendCoopMessage");
        availableMethod.add("viewCoopChat");
        availableMethod.add("messageAllAttendee");
        availableMethod.add("viewEnrolledSchedule");
    }


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


