package UI;

import Controllers.HandleRequestController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.RequestNotFoundException;
import java.util.*;

public class OrganizerRequestUI extends RequestUI {
    /**
     * Instance of <code>CreateRequestController</code>
     */
    private final HandleRequestController handleRequestController;

    /**
     * Creates an instance of <code>UserRequestUI</code>.
     * @param userController Instance of <code>UserController</code>.
     */
    public OrganizerRequestUI(UserController userController) {
        super(userController);
        handleRequestController = new HandleRequestController(userController);

    }

    @Override
    public void run() {
        boolean notStop = true;
        while(notStop){
            Scanner doWithRequest = new Scanner(System.in);
            System.out.println(requestPresenter.strOrgBeginRequestMenu());
            String choice = doWithRequest.nextLine();
            switch (choice){
                case "0":
                    viewPendingRequests(handleRequestController);
                    break;
                case "1":
                    viewAllRequests(handleRequestController);
                    break;
                case "2":
                    handleRequest(handleRequestController);
                    break;
                case "Q":
                case "q":
                    return;
                default:
                    System.out.println(userPresenter.strInvalidInput());
                    break;
            }
            notStop = continuing();
        }
    }

    /**
     * Outputs all submitted requests, if any. If there are no requests yet, a message telling them
     * so will be given instead.
     * @param requestController An instance of <code>requestController</code>.
     */
    private void viewPendingRequests(HandleRequestController requestController){
        if (requestController.getAllRequest().isEmpty()) {
            System.out.println(requestPresenter.strNoPendingRequests());
        } else {
            System.out.println(requestPresenter.strAllPendingRequestsHeader());
            requestController.viewPendingRequests();
        }
    }

    /**
     * Outputs all submitted requests, if any. If there are no requests yet, a message telling them
     * so will be given instead.
     * @param requestController An instance of <code>requestController</code>.
     */
    private void viewAllRequests(HandleRequestController requestController){
        if (requestController.getAllRequest().isEmpty()) {
            System.out.println(requestPresenter.strNoRequestSubmitted());
        } else {
            System.out.println(requestPresenter.strAllRequestSubmittedHeader());
            requestController.viewAllRequests();
        }
    }

    /**
     * Gets user to input which request they wish to handle, and handles it.
     */
    private void handleRequest(HandleRequestController handleRequestController) {
        int i = 0;
        while(i < 3){
            try{
                i = i + 1;
                UUID selection = chooseRequest(handleRequestController);
                handleRequestController.handleRequest(selection);
                break;
            }catch(RequestNotFoundException | ExceedingMaxAttemptException e){
                requestPresenter.strInvalidRequest();
            }
        }
    }

    /**
     * Gets info from the user regarding which <code>Request</code> they wish to handle.
     * @param handleRequestController An instance of <code>requestController</code>.
     * @return The UUID of the <code>Request</code> they wish to handle.
     * @throws RequestNotFoundException if the input UUID does not belong to any existing <code>Request</code>
     */
    private UUID chooseRequest(HandleRequestController handleRequestController) throws RequestNotFoundException, ExceedingMaxAttemptException {
       return chooseRequest(handleRequestController, "handle");
    }

    protected boolean continuing(){
        boolean enterAction = false;
        System.out.println(userPresenter.strContinueServicePrompt());
        Scanner scan2 = new Scanner(System.in);
        String choice = scan2.nextLine();
        if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")){
            enterAction = true;
        }
        return enterAction;
    }

}
