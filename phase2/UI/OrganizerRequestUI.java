package UI;

import entities.Request;
import Controllers.HandleRequestController;
import Controllers.UserController;
import functionalityPresenters.Presenter;
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
            System.out.println(requestPresenter.strBeginRequestMenu());
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
                case "3":
                    // reply to request?
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
            System.out.println("There are currently no requests.");
        } else {
            System.out.println("Here are all submitted requests: ");
            requestController.viewAllRequests();
        }
    }

    /**
     * Gets user to input which request they wish to handle, and handles it.
     */
    private void handleRequest(HandleRequestController handleRequestController) {
        while(true){
            try{
                Scanner chooseToHandle = new Scanner(System.in);
                handleRequestController.viewAllRequests();
                System.out.println("Which request would you like to handle? (Please enter the corresponding number):");
                String choice = chooseToHandle.nextLine();
                int i = 0;
                for (Request req : handleRequestController.getAllRequest()){
                    if (i == Integer.parseInt(choice)){
                        UUID reqID = req.getId();
                        handleRequestController.handleRequest(reqID);
                        break;
                    }
                    i = i + 1;
                }
            }catch(RequestNotFoundException e){
                System.out.println("That is not a valid request.");
            }
        }
    }


    /**
     * Gets info from the user regarding which <code>Request</code> they wish to modify.
     * @param requestController An instance of <code>requestController</code>.
     * @return The UUID of the <code>Request</code> they wish to modify.
     * @throws RequestNotFoundException if the input UUID does not belong to any existing <code>Request</code>
     */
    private UUID chooseRequest(HandleRequestController requestController) throws RequestNotFoundException{
        Scanner requestIDScanner = new Scanner(System.in);
        System.out.println(requestPresenter.strRequestPromptHelper("modify"));
        ArrayList<UUID> tmp = requestController.attendeeManager.getUserRequests();
        ArrayList<Request> userReqs = new ArrayList<>();
        for (UUID req : tmp){
            userReqs.add(requestController.findRequest(req));
        }
        System.out.println(userPresenter.strList(userReqs.toArray()));
        String selection = requestIDScanner.nextLine();
        int i = 0;
        for (Request req : requestController.getAllRequest()){
            if (i == Integer.parseInt(selection)){
                return req.getId();
            }
            i = i+1;
        }
        throw new RequestNotFoundException("Invalid selection!");
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
