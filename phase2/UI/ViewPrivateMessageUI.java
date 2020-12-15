package UI;

import Controllers.UserController;
import functionalityPresenters.PrivateMessagePresenter;
import globallyAccessible.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**
 * UI for viewing private messages.
 */
public class ViewPrivateMessageUI extends AbstractUI {
    /**
     * An instance of <code>PrivateMessagePresenter</code> being instantiated.
     */
    private PrivateMessagePresenter messagePresenter;

    /**
     * Instantiates new <code>ViewPrivateMessageUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public ViewPrivateMessageUI(UserController userController) {
        super(userController);
        messagePresenter = new PrivateMessagePresenter(userController);
    }

    /**
     * Shows the history message from certain user.
     */
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
                System.out.println(userPresenter.strUserContactPrompt());
                System.out.println(userPresenter.strList(historyChat.keySet().toArray()));
                return checkContactValid(historyChat);
            }catch(UserNotFoundException e){
                System.out.println(userPresenter.strInvalidUsername());
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
