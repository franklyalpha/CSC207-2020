package MessagingControllers;

import presenter.Presenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class SpeakerMessagingController extends AdministrativeMessagingController{

    public SpeakerMessagingController(Object[] managers) {
        super(managers);
    }

    public void sendActivityMessage() {
        ArrayList<String[]> info = showEnrolledSchedule();
        if (info.size() == 0){
            Presenter.printNotEnrolled();
            return;
        }
        UUID chatID = findRightChat(info);
        Scanner messageScanner = new Scanner(System.in);
        Presenter.printMessagePrompt();
        String message = messageScanner.nextLine();
        messageRoomManager.sendMessage(message, chatID);
    }

    private UUID findRightChat(ArrayList<String[]> info){
        int actID;
        actID = chatIDInput(info);
        return activityManager.getConferenceChat(
                UUID.fromString(info.get(actID - 1)[0]));
    }

    private int chatIDInput(ArrayList<String[]> info) {
        int actID;
        while(true){
            try{
                actID = determineChatIDValidity(info);
                break;
            }catch(IndexOutOfBoundsException e){
                Presenter.printInvalid("index of chat list");
            }
        }
        return actID;
    }

    private int determineChatIDValidity(ArrayList<String[]> info)
            throws IndexOutOfBoundsException{
        Scanner actIDScanner = new Scanner(System.in);
        Presenter.printActivityMessagePrompt();
        int actID = actIDScanner.nextInt();
        if (actID < 1 || actID > info.size()){
            throw new IndexOutOfBoundsException("invalid index for chat");
        }
        return actID;
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
}
