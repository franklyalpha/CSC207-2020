package UI;

import Controllers.UserController;
import functionalityPresenters.OrganizerSpeakerMessagePresenter;
import menuPresenter.OrganizerPresenter;

import java.util.ArrayList;

public class ViewManagersMessagesUI extends AbstractUI {
    public ViewManagersMessagesUI(UserController userController) {
        super(userController);
    }

    @Override
    public void run() {
        OrganizerPresenter organizerPresenter = new OrganizerPresenter();
        OrganizerSpeakerMessagePresenter orgSpeMessager =
                new OrganizerSpeakerMessagePresenter(userController);
        ArrayList<String> messages = orgSpeMessager.viewCoopChat();
        System.out.println(organizerPresenter.strMessagesInInterval(messages, 1, messages.size()));
    }
}
