package UI;

import Controllers.SendPrivateMessageController;
import Controllers.UserController;
import globallyAccessible.UserNotFoundException;

import java.util.Scanner;
/**
 * UI for sending private messages.
 */
public class SendPrivateMessageUI extends AbstractUI {
    /**
     * An instance of <code>SendPrivateMessageController</code> being instantiated.
     */
    private SendPrivateMessageController sendPrivateController;

    /**
     * Instantiates new <code>SendPrivateMessageUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public SendPrivateMessageUI(UserController userController) {
        super(userController);
        sendPrivateController = new SendPrivateMessageController(userController);
    }

    /**
     * Gets the message and send it.
     */
    @Override
    public void run() {
        int i = 3;
        while(i > 0){
            try{
                i = i - 1;
                inputContactAndMessage(sendPrivateController);
                break;
            }
            catch(UserNotFoundException e){
                System.out.println(userPresenter.strInvalidInput());
            }
        }
    }

    private void inputContactAndMessage(SendPrivateMessageController sendPrivateController)
            throws UserNotFoundException {
        Scanner userScanner = new Scanner(System.in);
        System.out.println(userPresenter.strUserToContactPrompt());
        String userName = userScanner.nextLine();
        Scanner messageScan = new Scanner(System.in);
        System.out.println(userPresenter.strMessagePrompt());
        String message = messageScan.nextLine();
        sendPrivateController.send(userName, message);
    }
}
