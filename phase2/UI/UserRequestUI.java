package UI;

import entities.Request;
import Controllers.RequestController;
import Controllers.UserController;
import menuPresenter.RequestPresenter;
import globallyAccessible.RequestNotFoundException;
import java.util.*;

public class UserRequestUI extends AbstractUI {
    /**
     * Instance of <code>CreateRequestController</code>
     */
    private final RequestController requestController;

    /**
     * Instance of <code>UserPresenter</code>
     */
    private final RequestPresenter requestPresenter;

    /**
     * Creates an instance of <code>UserRequestUI</code>.
     * @param userController Instance of <code>UserController</code>.
     */
    public UserRequestUI(UserController userController) {
        super(userController);
        requestController = new RequestController(userController);
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
                    inputRequestInfo(requestController);
                    break;
                case "1":
                    viewRequests(requestController);
                    break;
                case "2":
                    modifyRequest(requestController);
                    break;
                case "3":
                    removeRequest(requestController);
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
     * Takes user input for information required to create a new <code>Request</code>: subject, details.
     * @param requestController An instance of <code>requestController</code>.
     */
    private void inputRequestInfo(RequestController requestController) {
            Scanner subjectScanner = new Scanner(System.in);
            System.out.println(requestPresenter.strRequestSubjectPrompt());
            String subject = subjectScanner.nextLine();
            Scanner detailScan = new Scanner(System.in);
            System.out.println(requestPresenter.strRequestDetailsPrompt());
            String detail = detailScan.nextLine();
            requestController.newRequestCreator(subject, detail);
        }

    /**
     * Outputs all requests made by the user, if any. If the user has not made any requests yet, a message telling them
     * so will be given instead.
     * @param requestController An instance of <code>requestController</code>.
     */
    private void viewRequests(RequestController requestController){
        if (requestController.getUserRequests().isEmpty()) {
            System.out.println("You have not submitted any requests.");
        } else {
            System.out.println("Here are your requests: ");
            requestController.viewUserRequests();
        }
    }

    /**
     * Gets user input regarding which request they would like to modify and which part (subject or detail).
     * @param requestController An instance of <code>requestController</code>.
     */
    private void modifyRequest(RequestController requestController) {
        while(true){
            try{
                UUID selection = chooseRequest(requestController);
                Scanner chooseSubjectDetail = new Scanner(System.in);
                System.out.println("What part would you like to modify? (Please enter the corresponding number):\n[1]  " +
                        "- Subject\n[2] - Description");
                    String choice = chooseSubjectDetail.nextLine();
                    switch (choice){
                        case "0":  // modify subject
                            modifySubject(selection);
                            break;
                        case "1":  // modify details
                            modifyDetails(selection);
                            break;
                        case "Q":
                        case "q":
                            return;
                        default:
                            System.out.println(userPresenter.strInvalidInput());
                            break;
                    }
            }catch(RequestNotFoundException e){
                requestPresenter.strInvalidRequest();
            }
        }
    }


    /**
     * Gets info from the user regarding which <code>Request</code> they wish to modify.
     * @param requestController An instance of <code>requestController</code>.
     * @return The UUID of the <code>Request</code> they wish to modify.
     * @throws RequestNotFoundException if the input UUID does not belong to any existing <code>Request</code>
     */
    private UUID chooseRequest(RequestController requestController) throws RequestNotFoundException{
        Scanner requestIDScanner = new Scanner(System.in);
        System.out.println(requestPresenter.strRequestPromptHelper("modify"));
        ArrayList<UUID> tmp = requestController.getUserRequests();
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
     * Gets user to input the new subject to be used to replace the subject of the specified <code>Request</code> and
     * performs this replacement.
     * @param request <code>UUID</code> corresponding to the <code>Request</code> to modify.
     * @throws RequestNotFoundException if the input UUID does not belong to any existing <code>Request</code>
     */
    private void modifySubject(UUID request) throws RequestNotFoundException {
        Scanner subjectScanner = new Scanner(System.in);
        System.out.println(requestPresenter.strInputNewSubject());
        String newSubject = subjectScanner.nextLine();
        requestController.modifyRequestSubject(request, newSubject);
    }

    /**
     * Gets user to input the new details to be used to replace the description of the specified <code>Request</code>
     * and performs this replacement.
     * @param request <code>UUID</code> corresponding to the <code>Request</code> to modify.
     * @throws RequestNotFoundException if the input UUID does not belong to any existing <code>Request</code>
     */
    private void modifyDetails(UUID request) throws RequestNotFoundException {
        Scanner detailScanner = new Scanner(System.in);
        System.out.println(requestPresenter.strInputNewDetails());
        String newDetail = detailScanner.nextLine();
        requestController.modifyRequestDetails(request, newDetail);
    }

    /**
     * Gets user to input which request they wish to remove, and performs the removal.
     */
    private void removeRequest(RequestController requestController) {
        while(true){
            try{
                Scanner chooseToRemove = new Scanner(System.in);
                requestController.viewUserRequests();
                System.out.println("Which request would you like to remove? (Please enter the corresponding number):");
                String choice = chooseToRemove.nextLine();
                int i = 0;
                for (UUID reqID : requestController.getUserRequests()){
                    if (i == Integer.parseInt(choice)){
                        requestController.deleteRequest(reqID);
                        ArrayList<UUID> tmp = new ArrayList<>(requestController.getUserRequests());
                        tmp.remove(reqID);
                        requestController.attendeeManager.setUserRequests(tmp);
                    }
                    i = i + 1;
                }
            }catch(RequestNotFoundException e){
                requestPresenter.strInvalidRequest();
            }
        }
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
