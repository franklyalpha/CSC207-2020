package UI;

import Controllers.HandleRequestController;
import entities.Request;
import Controllers.RequestController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.RequestPresenter;
import globallyAccessible.RequestNotFoundException;
import java.util.*;

public class RequestUI extends AbstractUI {
    /**
     * Instance of <code>RequestPresenter</code>
     */
    protected final RequestPresenter requestPresenter;

    /**
     * Creates an instance of <code>UserRequestUI</code>.
     *
     * @param userController Instance of <code>UserController</code>.
     */
    public RequestUI(UserController userController) {
        super(userController);
        requestPresenter = new RequestPresenter();
    }

    /**
     * Gets info from the user regarding which <code>Request</code> they wish to perform the given action on.
     * @param requestController An instance of <code>requestController</code> or <code>handleRequestController</code> if
     *                         the current user is an attendee or organizer respectively.
     * @return The UUID of the <code>Request</code> they wish to perform the given action on.
     * @throws RequestNotFoundException if the input UUID does not belong to any existing <code>Request</code>.
     * @throws ExceedingMaxAttemptException if the user attempts to input invalid inputs three or more times.
     */
    public UUID chooseRequest(RequestController requestController, String action) throws RequestNotFoundException, ExceedingMaxAttemptException {
        ArrayList<UUID> reqList;
        if (requestController instanceof HandleRequestController){
            reqList = getListHelper((HandleRequestController) requestController);
        } else {
            reqList = getListHelper(requestController);
        }
        int x = 0;
        System.out.println(requestPresenter.strRequestPromptHelper(action));
        if(reqList.size() == 0){
            throw new RequestNotFoundException("No requests found.");
        }
       getPrintReqList(requestController, reqList);
        while (x < 3) {
            try {
                x = x + 1;
                Scanner requestIDScanner = new Scanner(System.in);
                int selection = 0;
                if (requestIDScanner.hasNextInt()) {
                    selection = Integer.parseInt(requestIDScanner.nextLine());
                }
                if ( selection > reqList.size() || selection < 0) {
                    System.out.println(requestPresenter.strInvalidRequest() + requestPresenter.strPleaseTryAgain());
                }
                else {
                    int i = 1;
                    for (Request req : requestController.getAllRequest()) {
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
    
    private ArrayList<UUID> getListHelper(RequestController requestController){
        return requestController.getUserRequests();
    }

    private ArrayList<UUID> getListHelper(HandleRequestController handleRequestController){
        return handleRequestController.getAllPending();
    }

    private void getPrintReqList(RequestController requestController, ArrayList<UUID> reqList) throws RequestNotFoundException {
        ArrayList<String> userReqs = new ArrayList<>();
        for (UUID req : reqList) {
            userReqs.add(requestPresenter.strRequestObjArrToStr(requestController.findRequest(req)));
        }
        System.out.println(userPresenter.strList(userReqs.toArray()));
        System.out.println(requestPresenter.strQuitPrompt());
    }

}