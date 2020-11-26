package MessagingControllers;

import presenter.Presenter;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class AdministrativeMessagingController extends UserMessagingController{
    public AdministrativeMessagingController(Object[] managers) {
        super(managers);
    }


    public void sendCoopMessage() {
        UUID coopChatID = messageRoomManager.getCoopId();
        Scanner messenger = new Scanner(System.in);
        Presenter.printMessagePrompt();
        String message = messenger.nextLine();
        messageRoomManager.sendMessage(message, coopChatID);
    }


    public void viewCoopChat() {
        UUID coopChatID = messageRoomManager.getCoopId();
        ArrayList<String> message = messageRoomManager.getHistoricalChats(coopChatID);
        Presenter.printMessagesInInterval(message, 1, message.size());
    }
}
