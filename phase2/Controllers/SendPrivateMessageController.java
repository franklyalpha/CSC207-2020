package Controllers;

/**
 * Dealing with sending a private messages among all users.
 */
public class SendPrivateMessageController extends MessagingController {


    /**
     * The constructor of this controller;
     * @param userController An instance of <>UserController</>
     */
    public SendPrivateMessageController(UserController userController) {
        super(userController);
    }
}
