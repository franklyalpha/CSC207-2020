package UI;

import Controllers.AbstractController;
import Controllers.SendOrganizerSpeakerMessageController;
import Controllers.UserController;
import menuPresenter.OrganizerPresenter;

import java.util.Scanner;

public class SendManagersMessageUI extends AbstractUI {
    public SendManagersMessageUI(UserController userController) {
        super(userController);
    }

    public void run(){
        SendOrganizerSpeakerMessageController orgSpeSendMessage = new SendOrganizerSpeakerMessageController(userController);
        OrganizerPresenter organizerPresenter = new OrganizerPresenter();
        Scanner messenger = new Scanner(System.in);
        System.out.println(organizerPresenter.strMessagePrompt());
        String message = messenger.nextLine();
        orgSpeSendMessage.sendCoopMessage(message);
    }
}
