package Controllers;

import globallyAccessible.UserNotFoundException;
import Presenters.Presenter;

import java.util.Scanner;

public class SendPrivateMessageController extends MessagingController {


    public SendPrivateMessageController(UserController userController) {
        super(userController);
    }
}
