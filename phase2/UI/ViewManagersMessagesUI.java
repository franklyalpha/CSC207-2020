package UI;

import Controllers.UserController;
import functionalityPresenters.OrganizerSpeakerMessagePresenter;
import menuPresenter.OrganizerPresenter;

import java.util.ArrayList;
/**
 * UI for viewing managers' messages.
 */


public class ViewManagersMessagesUI extends AbstractUI {
    /**
     * Instantiates new <code>ViewManagersMessagesUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public ViewManagersMessagesUI(UserController userController) {
        super(userController);
    }

    /**
     * Shows the messages from managers.
     */
    @Override
    public void run() {
        OrganizerPresenter organizerPresenter = new OrganizerPresenter();
        OrganizerSpeakerMessagePresenter orgSpeMessager =
                new OrganizerSpeakerMessagePresenter(userController);
        ArrayList<String> messages = orgSpeMessager.viewCoopChat();
        System.out.println(organizerPresenter.strMessagesInInterval(messages, 1, messages.size()));
    }
}
