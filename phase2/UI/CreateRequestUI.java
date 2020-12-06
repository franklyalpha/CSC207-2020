package UI;

import Controllers.CreateRequestController;
import Controllers.UserController;

import java.util.Scanner;

public class CreateRequestUI extends AbstractUI {
    /**
     * Instance of <code>CreateRequestController</code>
     */
    private final CreateRequestController createRequestController;

    /**
     * Creates an instance of <code>CreateRequestUI</code>.
     * @param userController Instance of <code>UserController</code>.
     */
    public CreateRequestUI(UserController userController) {
        super(userController);
        createRequestController = new CreateRequestController(userController);
    }

    @Override
    public void run() {
                inputRequestInfo(createRequestController);
    }

    private void inputRequestInfo(CreateRequestController createRequestController) {
        Scanner subjectScanner = new Scanner(System.in);
        System.out.println(userPresenter.strRequestSubjectPrompt());
        String subject = subjectScanner.nextLine();
        Scanner detailScan = new Scanner(System.in);
        System.out.println(userPresenter.strRequestDetailsPrompt());
        String detail = detailScan.nextLine();
        createRequestController.newRequestCreator(subject, detail);
    }

}
