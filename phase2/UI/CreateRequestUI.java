package UI;

import Controllers.RequestController;
import Controllers.UserController;

import java.util.Scanner;

public class CreateRequestUI extends AbstractUI {
    /**
     * Instance of <code>CreateRequestController</code>
     */
    private final RequestController requestController;

    /**
     * Creates an instance of <code>CreateRequestUI</code>.
     * @param userController Instance of <code>UserController</code>.
     */
    public CreateRequestUI(UserController userController) {
        super(userController);
        requestController = new RequestController(userController);
    }

    @Override
    public void run() {
                inputRequestInfo(requestController);
    }

    private void inputRequestInfo(RequestController requestController) {
        Scanner subjectScanner = new Scanner(System.in);
        System.out.println(userPresenter.strRequestSubjectPrompt());
        String subject = subjectScanner.nextLine();
        Scanner detailScan = new Scanner(System.in);
        System.out.println(userPresenter.strRequestDetailsPrompt());
        String detail = detailScan.nextLine();
        requestController.newRequestCreator(subject, detail);
    }

}
