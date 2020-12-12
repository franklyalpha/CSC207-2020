package UI;

import Controllers.RequestController;
import entities.Request;
import Controllers.HandleRequestController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.RequestPresenter;
import globallyAccessible.RequestNotFoundException;
import java.util.*;

public class OrganizerRequestUI extends AbstractUI {
    /**
     * Instance of <code>CreateRequestController</code>
     */
    private final HandleRequestController handleRequestController;

    /**
     * Instance of <code>UserPresenter</code>
     */
    private final RequestPresenter requestPresenter;

    /**
     * Creates an instance of <code>UserRequestUI</code>.
     * @param userController Instance of <code>UserController</code>.
     */
    public OrganizerRequestUI(UserController userController) {
        super(userController);
        handleRequestController = new HandleRequestController(userController);
        requestPresenter = new RequestPresenter();
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
        int x = 0;
        System.out.println(requestPresenter.strRequestPromptHelper("handle"));
        ArrayList<UUID> pending = handleRequestController.getAllPending();
        if(pending.size() == 0){
            throw new RequestNotFoundException("No requests found.");
        }
        ArrayList<String> userReqs = new ArrayList<>();
        for (UUID req : pending) {
            userReqs.add(requestPresenter.strRequestObjArrToStr(handleRequestController.findRequest(req)));
        }
        System.out.println(userPresenter.strList(userReqs.toArray()));
        System.out.println(requestPresenter.strQuitPrompt());
        while (x < 3) {
            try {
                x = x + 1;
                Scanner requestIDScanner = new Scanner(System.in);
                int selection = 0;
                if (requestIDScanner.hasNextInt()) {
                    selection = Integer.parseInt(requestIDScanner.nextLine());
                }
                if ( selection > handleRequestController.getAllRequest().size() || selection < 0) {
                    System.out.println(requestPresenter.strInvalidRequest() + requestPresenter.strPleaseTryAgain());
                }
                else {
                    int i = 1;
                    for (Request req : handleRequestController.getAllRequest()) {
                        if (i == selection) {
                            return req.getId();
                        }
                        i = i + 1;
                    }
                }
            } catch (IndexOutOfBoundsException e){
                x = x + 1;
                requestPresenter.strInvalidInput();
            }
        }
        throw new ExceedingMaxAttemptException("Maximum number of attempts exceeded");
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
