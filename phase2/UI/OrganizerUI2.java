package UI;

import Controllers.MessageAllAttendeeController;
import Controllers.SendOrganizerSpeakerMessageController;
import Controllers.UserController;
import Presenters.OrganizerSpeakerMessagePresenter;
import Presenters.Presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
            Presenter.printAvailableActions(availableAction);
            action = scan.nextInt();
            if (0 < action && action <= availableAction.size()) {
                runMethod(action);
            }
            else{
                Presenter.printInvalid("input");
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
        }
    }

    protected void sendCoopMessage(){
        SendOrganizerSpeakerMessageController orgSpeSendMessage = new SendOrganizerSpeakerMessageController(userController);
        Scanner messenger = new Scanner(System.in);
        Presenter.printMessagePrompt();
        String message = messenger.nextLine();
        orgSpeSendMessage.sendCoopMessage(message);
    }

    protected void viewCoopChat(){
        OrganizerSpeakerMessagePresenter orgSpeMessager =
                new OrganizerSpeakerMessagePresenter(userController);
        ArrayList<String> messages = orgSpeMessager.viewCoopChat();
        Presenter.printMessagesInInterval(messages, 1, messages.size());
    }

    protected void messageAllAttendee(){
        Scanner messageScanner = new Scanner(System.in);
        Presenter.printMessagePrompt();
        String message = messageScanner.nextLine();
        MessageAllAttendeeController messageAll = new MessageAllAttendeeController(userController);
        messageAll.messageAllAttendee(message);
    }
}
