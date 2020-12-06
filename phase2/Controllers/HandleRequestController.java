package Controllers;

import globallyAccessible.RequestNotFoundException;
import useCases.RequestManager;

import java.util.UUID;

/**
 * Controller managing calls to handle a <code>Request</code>.
 */
public class HandleRequestController extends RequestController {

    /**
     * An instance of <code>RequestManager</code>.
     */
    private RequestManager requestManager;

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

}
