package UI;

import Controllers.UserController;
import functionalityPresenters.GroupMessagePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ViewGroupMessageUI extends AbstractUI {
    private GroupMessagePresenter groupMessagePresenter;

    public ViewGroupMessageUI(UserController userController) {
        super(userController);
        groupMessagePresenter = new GroupMessagePresenter(userController);
    }

    @Override
    public void run() {
        HashMap<String, ArrayList<String>> historyChat = groupMessagePresenter.viewGroupMessage();
        if (historyChat == null) {
            System.out.println(userPresenter.strHistoryChatNull());
        } else {
            System.out.println(userPresenter.strEventContactPrompt());
            System.out.println(userPresenter.strList(historyChat.keySet().toArray()));
            Scanner scan = new Scanner(System.in);
            String selectedEvent = scan.nextLine();
            if (!historyChat.containsKey(selectedEvent)) {
                System.out.println(userPresenter.strInvalidEventID());
                return;
            }
            System.out.println(userPresenter.strMessagesInInterval(historyChat.get(selectedEvent), 1,
                    historyChat.get(selectedEvent).size()));
        }
    }
}
