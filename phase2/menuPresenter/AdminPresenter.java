package menuPresenter;

import java.util.ArrayList;

public class AdminPresenter extends UserPresenter {
    public ArrayList<String> optionMenu(){
        ArrayList<String> availableAction = new ArrayList<>();
        availableAction.add("send private message");
        availableAction.add("view private messages");
        availableAction.add("send messages in coopChatroom");
        availableAction.add("view messages from coopChatroom");
        availableAction.add("delete conversations or lines of messages");
        availableAction.add("delete empty activities");
        return availableAction;
    }
}
