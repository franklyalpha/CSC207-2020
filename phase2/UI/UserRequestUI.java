package UI;

import Controllers.RequestController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.RequestNotFoundException;
import java.util.*;

public class UserRequestUI extends RequestUI {
    /**
     * Instance of <code>CreateRequestController</code>
     */
    private final RequestController requestController;

    /**
     * Creates an instance of <code>UserRequestUI</code>.
     * @param userController Instance of <code>UserController</code>.
     */
    public UserRequestUI(UserController userController) {
        super(userController);
        requestController = new RequestController(userController);
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
        if (!requestController.getUserRequests().isEmpty()) {
            System.out.println(requestPresenter.strRequestHeader());
        }
        requestController.viewUserRequests();
    }

    /**
     * Gets user input regarding which request they would like to modify and which part (subject or detail).
     * @param requestController An instance of <code>requestController</code>.
     */
    private void modifyRequest(RequestController requestController) {
        int i = 0;
        while(i < 3){
            try{
                i = i + 1;
                UUID selection = chooseRequest(requestController);
                Scanner chooseSubjectDetail = new Scanner(System.in);
                System.out.println(requestPresenter.strModifyMenu());
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
            }catch(RequestNotFoundException | ExceedingMaxAttemptException e){
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
    private UUID chooseRequest(RequestController requestController) throws RequestNotFoundException, ExceedingMaxAttemptException {
        return chooseRequest(requestController, "modify");
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
        int x = 0;
        while(x < 3){
            try{
                x = x + 1;
                Scanner chooseToRemove = new Scanner(System.in);
                requestController.viewUserRequests();
                System.out.println(requestPresenter.strRequestRemovePrompt());
                String choice = chooseToRemove.nextLine();
                int i = 0;
                ArrayList<UUID> toRemove = new ArrayList<>();
                for (UUID reqID : requestController.getUserRequests()){
                    if (i == Integer.parseInt(choice)){
                        toRemove.add(reqID);
                    }
                    i = i + 1;
                }
                for (UUID id: toRemove){    // to avoid concurrent mod exception
                    requestController.deleteRequest(id);
                    ArrayList<UUID> tmp = new ArrayList<>(requestController.getUserRequests());
                    tmp.remove(id);
                    requestController.attendeeManager.setUserRequests(tmp);
                }
                break;
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
