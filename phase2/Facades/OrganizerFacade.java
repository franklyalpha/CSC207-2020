package Facades;

import Controllers.UserController;
import UI.MessageAllAttendeeUI;
import UI.ModifyActivityUI;
import UI.SendManagersMessageUI;
import UI.ViewManagersMessagesUI;

import java.util.Scanner;

public class OrganizerFacade extends ExtendedOrganizerFacade {
    public OrganizerFacade(UserController userController) {
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
        new SendManagersMessageUI(userController).run();
    }

    protected void viewCoopChat(){
        new ViewManagersMessagesUI(userController).run();
    }

    protected void messageAllAttendee(){
        new MessageAllAttendeeUI(userController).run();
    }

    private void modifyActivity() {
        new ModifyActivityUI(userController).run();
    }
}
