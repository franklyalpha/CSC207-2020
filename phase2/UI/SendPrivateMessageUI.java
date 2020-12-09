package UI;

import Controllers.SendPrivateMessageController;
import Controllers.UserController;
import globallyAccessible.UserNotFoundException;

import java.util.Scanner;

public class SendPrivateMessageUI extends AbstractUI {
    private SendPrivateMessageController sendPrivateController;

    public SendPrivateMessageUI(UserController userController) {
        super(userController);
        sendPrivateController = new SendPrivateMessageController(userController);
    }

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
