package UI;

import Controllers.MessageAllAttendeeController;
import Controllers.SendOrganizerSpeakerMessageController;
import Controllers.UserController;
import functionalityPresenters.OrganizerSpeakerMessagePresenter;
import Controllers.ModifyActivityController;
import globallyAccessible.NoActivitiesException;
import menuPresenter.ModifyActivityPresenter;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class OrganizerUI2 extends OrganizerUI {
    public OrganizerUI2(UserController userController) {
        super(userController);
    }

    public void run() {
        addMenu();
        int action;


        // replace with try-catch in while loop



        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            System.out.println(organizerPresenter.strAvailableActions(availableAction));
            action = scan.nextInt();
            if (0 < action && action <= availableAction.size()) {
                runMethod(action);
            }
            else{
                System.out.println(organizerPresenter.strInvalidInput());
            }
            enterAction = continuing();
        }
        userController.logout();
    }

    private void runMethod (int action){
        switch(action){
            case 1: createRoom(); break;
            case 2: createUser(); break;
            case 3: addSchedule(); break;
            case 4: rescheduleSpeaker(); break;
            case 5: sendPrivateMessage(); break;
            case 6: viewPrivateMessage(); break;
            case 7: sendCoopMessage(); break;
            case 8: viewCoopChat(); break;
            case 9: messageAllAttendee(); break;
            case 10: modifyActivity(); break;
        }
    }

    protected void sendCoopMessage(){
        SendOrganizerSpeakerMessageController orgSpeSendMessage = new SendOrganizerSpeakerMessageController(userController);
        Scanner messenger = new Scanner(System.in);
        System.out.println(organizerPresenter.strMessagePrompt());
        String message = messenger.nextLine();
        orgSpeSendMessage.sendCoopMessage(message);
    }

    protected void viewCoopChat(){
        OrganizerSpeakerMessagePresenter orgSpeMessager =
                new OrganizerSpeakerMessagePresenter(userController);
        ArrayList<String> messages = orgSpeMessager.viewCoopChat();
        System.out.println(organizerPresenter.strMessagesInInterval(messages, 1, messages.size()));
    }

    protected void messageAllAttendee(){
        Scanner messageScanner = new Scanner(System.in);
        System.out.println(organizerPresenter.strMessagePrompt());
        String message = messageScanner.nextLine();
        MessageAllAttendeeController messageAll = new MessageAllAttendeeController(userController);
        messageAll.messageAllAttendee(message);
    }

    protected void modifyActivity() {
        ModifyActivityController modifyActivity = new ModifyActivityController(userController);
        try{
            ModifyActivityPresenter.printMaxNumActivityPrompt_1(modifyActivity.getAllActivities());
            Scanner input_1 = new Scanner(System.in);
            ModifyActivityPresenter.printMaxNumActivityPrompt_2();
            Scanner input_2 = new Scanner(System.in);
            UUID activityId = UUID.fromString(input_1.nextLine());
            int newMaxNum = input_2.nextInt();
            modifyActivity.changeActivityMaxNumPeople(activityId, newMaxNum);
        }catch (NoActivitiesException e){
            ModifyActivityPresenter.printNoActivity();
        }

    }
}
