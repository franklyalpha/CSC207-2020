package UI;

import Controllers.HandleRequestController;
import Controllers.UserController;

import java.util.Scanner;

public class HandleRequestUI extends AbstractUI {
    /**
     * Instance of <code>HandleRequestController</code>
     */
    private final HandleRequestController handleRequestController;

    /**
     * Creates an instance of <code>CreateRequestUI</code>.
     * @param userController Instance of <code>UserController</code>.
     */
    public HandleRequestUI(UserController userController) {
        super(userController);
        handleRequestController = new HandleRequestController(userController);
    }

    @Override
    public void run() {
        inputRequestToHandle(handleRequestController);
    }

    private void inputRequestToHandle(HandleRequestController handleRequestController) {
        Scanner requestIDScanner = new Scanner(System.in);
        System.out.println(userPresenter.strRequestSubjectPrompt());
        String request = requestIDScanner.nextLine();

        handleRequestController.handleRequest(request);
    }

}
