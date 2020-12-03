package UI;

import Controllers.SendActivityMessageController;
import Controllers.UserController;
import menuPresenter.SpeakerPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class SpeSendActivityMessagesUI extends AbstractUI {
    private SpeakerPresenter speakerPresenter = new SpeakerPresenter();
    private SendActivityMessageController activityMessager;

    public SpeSendActivityMessagesUI(UserController userController) {
        super(userController);
        activityMessager = new SendActivityMessageController(userController);
    }

    @Override
    public void run() {
        ArrayList<String[]> info = presentEnrolledActivities(activityMessager);
        if (info == null) return;
        majorProcessor(info);
    }

    private void majorProcessor(ArrayList<String[]> info) {
        while(true){
            try{
                findAndSendMessage(activityMessager, info);
                break;
            }catch(IndexOutOfBoundsException e){
                System.out.println(speakerPresenter.strInvalidIndex());
            }
        }
    }

    private ArrayList<String[]> presentEnrolledActivities(SendActivityMessageController activityMessager) {
        ArrayList<String[]> info = activityMessager.showEnrolledSchedule();
        if (info.size() == 0){
            return null;
        }
        System.out.println(speakerPresenter.strEnrolledMenuDes());
        System.out.println(speakerPresenter.strSchedule(info));
        return info;
    }

    private void findAndSendMessage(SendActivityMessageController activityMessager, ArrayList<String[]> info) {
        int actID = determineChatIDValidity(info);
        Scanner messageScanner = new Scanner(System.in);
        System.out.println(speakerPresenter.strMessagePrompt());
        String message = messageScanner.nextLine();
        activityMessager.sendActivityMessage(actID, message);
    }

    private int determineChatIDValidity(ArrayList<String[]> info)
            throws IndexOutOfBoundsException{
        Scanner actIDScanner = new Scanner(System.in);
        System.out.println(speakerPresenter.strActivityMessagePrompt());
        int actID = actIDScanner.nextInt();
        if (actID < 1 || actID > info.size()){
            throw new IndexOutOfBoundsException("invalid index for chat");
        }
        return actID;
    }
}
