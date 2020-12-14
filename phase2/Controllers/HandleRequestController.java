package Controllers;

import globallyAccessible.RequestNotFoundException;


import java.util.ArrayList;
import java.util.UUID;

/**
 * Controller managing calls to handle a <code>Request</code>.
 */
public class HandleRequestController extends RequestController {

    /**
     * Creates an instance of <code>HandleRequestController</code>.
     * @param userController Instance of <code>UserController</code> for superconstructor use.
     */
    public HandleRequestController(UserController userController) {
        super(userController);
    }

    /**
     * Handles the specified request.
     * @param request UUID of the <code>Request</code> that is to be handled.
     */
    public void handleRequest(UUID request) throws RequestNotFoundException {
        if (requestManager.isExistingRequest(request)) {
            requestManager.handleRequest(request);
        }
        else{
            throw new RequestNotFoundException("Request not found.");
        }
    }

    /**
     * Gets the IDs of any pending requests.
     * @return ArrayList of <code>UUID</code>s of any pending requests.
     */
    public ArrayList<UUID> getAllPending(){
        return requestManager.getPendingIDs();
    }
}
