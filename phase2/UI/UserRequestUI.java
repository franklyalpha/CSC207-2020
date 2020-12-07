package UI;

import entities.Request;
import Controllers.RequestController;
import Controllers.UserController;
import menuPresenter.UserPresenter;

import java.lang.reflect.Array;
import java.util.UUID;

import java.util.Scanner;

public class UserRequestUI extends AbstractUI {
    /**
     * Instance of <code>CreateRequestController</code>
     */
    private final RequestController requestController;

    /**
     * Instance of <code>UserPresenter</code>
     */
    private UserPresenter userPresenter;

    /**
     * Creates an instance of <code>UserRequestUI</code>.
     * @param userController Instance of <code>UserController</code>.
     */
    public UserRequestUI(UserController userController) {
        super(userController);
        requestController = new RequestController(userController);
        userPresenter = new UserPresenter();
    }

    @Override
    public void run(){
        boolean notStop = true;
        while(notStop){
            Scanner doWithRequest = new Scanner(System.in);
            System.out.println(userPresenter.strRequestSubjectPrompt());
            String choice = doWithRequest.nextLine();
            switch (choice){
                case "0":
                    inputRequestInfo(requestController);
                    break;
                case "1":
                    modifyRequest(requestController);
                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "Q":
                case "q":
                    return;
                default:
                    System.out.println(userPresenter.strInvalidInput());
                    break;
            }
            notStop = handleWrongInput();
        }
    }


    /**
     * Takes user input for information required to create a new <code>Request</code>: subject, details.
     * @param requestController An instance of <code>requestController</code>.
     */
    private void inputRequestInfo(RequestController requestController) {
            Scanner subjectScanner = new Scanner(System.in);
            System.out.println(userPresenter.strRequestSubjectPrompt());
            String subject = subjectScanner.nextLine();
            Scanner detailScan = new Scanner(System.in);
            System.out.println(userPresenter.strRequestDetailsPrompt());
            String detail = detailScan.nextLine();
            requestController.newRequestCreator(subject, detail);
        }

    private String modifyRequest(RequestController requestController) {
        UUID selection = chooseRequest(requestController);
        return "test";
    }

    /**
     * Gets info from the user regarding which <code>Request</code> they wish to modify.
     * @param requestController An instance of <code>requestController</code>.
     * @return The UUID of the <code>Request</code> they wish to modify.
     */
    private UUID chooseRequest(RequestController requestController){
        Scanner requestIDScanner = new Scanner(System.in);
        System.out.println(userPresenter.strChooseRequest("modify"));
        System.out.println(userPresenter.strList(requestController.getAllRequest().toArray()));
        String selection = requestIDScanner.nextLine();
        int i = 0;
        for (Request req : requestController.getAllRequest()){
            if (i == Integer.parseInt(selection)){
                return req.getId();
            }
            i = i+1;
        }
        return userPresenter.strInvalidInput();
    }

    private void modifyRequestSubject(RequestController requestController) {
        Scanner requestIDScanner = new Scanner(System.in);
        System.out.println(userPresenter.strChooseRequest("modify"));
    }

    /**
     * Asks the program user whether or not they wish to continue using the program when an invalid input occurs.
     * @return Boolean value representing whether or not the program is to continue.
     */
    private boolean handleWrongInput() {
        boolean notStop = false;
        boolean validInput = false;
        while(!validInput){
            System.out.println(userPresenter.strInvalidInput());
            Scanner nextChoice = new Scanner(System.in);
            String choice = nextChoice.nextLine();
            if (choice.equals("Y") || choice.equals("Yes") || choice.equals("y") || choice.equals("yes")){
                notStop = true;
                validInput = true;
            }
            else if (choice.equals("N") || choice.equals("No") || choice.equals("n") || choice.equals("no")){
                validInput = true;
            }
            else{
                System.out.println(userPresenter.strInvalidInput());
            }
        }
        return notStop;
    }

}
