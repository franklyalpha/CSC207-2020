package menuPresenter;

import java.util.ArrayList;

public class AdminPresenter extends UserPresenter {
    public ArrayList<String> optionMenu(){
        ArrayList<String> availableAction = new ArrayList<>();
        availableAction.add(" - Send a private message");
        availableAction.add(" - View private messages");
        availableAction.add(" - Send messages to an Organizer-Speaker chat");
        availableAction.add(" - View messages from an Organizer-Speaker chat");
        availableAction.add(" - Delete conversations or specific messages");
        availableAction.add(" - Delete empty events");
        return availableAction;
    }
}
