package UI;

import Controllers.SendEventMessageController;
import Controllers.UserController;
import menuPresenter.SpeakerPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class SpeSendEventMessagesUI extends AbstractUI {
    private SpeakerPresenter speakerPresenter = new SpeakerPresenter();
    private SendEventMessageController eventMessager;

    public SpeSendEventMessagesUI(UserController userController) {
        super(userController);
        eventMessager = new SendEventMessageController(userController);
    }

    @Override
    public void run() {
        ArrayList<String[]> info = presentEnrolledActivities(eventMessager);
        if (info == null) return;
        majorProcessor(info);
    }

    private void majorProcessor(ArrayList<String[]> info) {
        while(true){
            try{
                findAndSendMessage(eventMessager, info);
                break;
            }catch(IndexOutOfBoundsException e){
                System.out.println(speakerPresenter.strInvalidIndex());
            }
        }
    }

    private ArrayList<String[]> presentEnrolledActivities(SendEventMessageController activityMessager) {
        ArrayList<String[]> info = activityMessager.showEnrolledSchedule();
        if (info.size() == 0){
            System.out.println("No events enrolled yet");
            return null;
        }
        System.out.println(speakerPresenter.strEnrolledMenuDes());
        System.out.println(speakerPresenter.strSchedule(info));
        return info;
    }

    private void findAndSendMessage(SendEventMessageController activityMessager, ArrayList<String[]> info) {
        int actID = determineChatIDValidity(info);
        Scanner messageScanner = new Scanner(System.in);
        System.out.println(speakerPresenter.strMessagePrompt());
        String message = messageScanner.nextLine();
        activityMessager.sendEventMessage(actID, message);
    }

    private int determineChatIDValidity(ArrayList<String[]> info)
            throws IndexOutOfBoundsException{
        Scanner actIDScanner = new Scanner(System.in);
        System.out.println(speakerPresenter.strEventMessagePrompt());
        int actID = actIDScanner.nextInt();
        if (actID < 1 || actID > info.size()){
            throw new IndexOutOfBoundsException("invalid index for chat");
        }
        return actID;
    }
}
