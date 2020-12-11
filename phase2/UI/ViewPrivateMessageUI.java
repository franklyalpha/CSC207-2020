package UI;

import Controllers.UserController;
import functionalityPresenters.Presenter;
import functionalityPresenters.PrivateMessagePresenter;
import globallyAccessible.UserNotFoundException;
import menuPresenter.UserPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ViewPrivateMessageUI extends AbstractUI {
    private PrivateMessagePresenter messagePresenter;

    public ViewPrivateMessageUI(UserController userController) {
        super(userController);
        messagePresenter = new PrivateMessagePresenter(userController);
    }

    @Override
    public void run() {
        HashMap<String, ArrayList<String>> historyChat = messagePresenter.viewPrivateMessage();
        if(historyChat == null){
            return;
        }
        String contactUser = chooseUserToView(historyChat);
        System.out.println(userPresenter.strMessagesInInterval(historyChat.get(contactUser), 1, historyChat.get(contactUser).size()));

    }

    private String chooseUserToView(HashMap<String, ArrayList<String>> historyChat) {
        while(true){
            try{
                Presenter.printContactPrompt("contact"); //TODO Really should change this to an index system
                System.out.println(userPresenter.strList(historyChat.keySet().toArray()));
                return checkContactValid(historyChat);
            }catch(UserNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    private String checkContactValid(HashMap<String, ArrayList<String>> historyChat)
            throws UserNotFoundException {
        Scanner scan = new Scanner(System.in);
        String contactUser = scan.nextLine();
        if (!historyChat.containsKey(contactUser)){
            throw new UserNotFoundException("Wrong user!!");
        }
        return contactUser;
    }
}
