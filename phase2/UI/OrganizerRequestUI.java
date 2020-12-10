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
            System.out.println("There are currently no pending requests");
        } else {
            System.out.println("Here are all pending requests: ");
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
            System.out.println("No requests have been submitted yet.");
        } else {
            System.out.println("Here are all submitted requests: ");
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
        return;
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
            userReqs.add(toStringHelper(handleRequestController.findRequest(req)));
        }
        System.out.println(userPresenter.strList(userReqs.toArray()));
        System.out.println("[Q] - Go back");
        while (x < 3) {
            try {
                x = x + 1;
                Scanner requestIDScanner = new Scanner(System.in);
                int selection = 0;
                if (requestIDScanner.hasNextInt()) {
                    selection = Integer.parseInt(requestIDScanner.nextLine());
                }
                if ( selection > handleRequestController.getAllRequest().size() || selection < 0) {
                    System.out.println("Invalid request! Please try again.");
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
                break;
            } catch (IndexOutOfBoundsException e){
                x = x + 1;
                requestPresenter.strInvalidInput();
            }
        }
        throw new ExceedingMaxAttemptException("Maximum attempts exceeded!!");
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

    /**
     * Helper method to format object array into readable text.
     * @param req1 Instance of the <code>Object[]</code> to be formatted.
     * @return String representing the <code>Object[]</code>.
     */
    private String toStringHelper(Object[] req1){
        return  "\n------------------------------------------------------\nSubject: " + req1[2] + "\nStatus: [ " + req1[3]
                + " ]\n------------------------------------------------------";
    }

}
