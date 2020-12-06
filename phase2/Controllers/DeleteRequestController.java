package Controllers;

import globallyAccessible.RequestNotFoundException;
import useCases.RequestManager;

import java.util.UUID;

/**
 * Controller handling calls to delete an existing <code>Request</code>.
 */
public class DeleteRequestController extends RequestController {

    /**
     * An instance of <code>RequestManager</code>.
     */
    private RequestManager requestManager;

    /**
     * Creates an instance of <code>DeleteRequestController</code>.
     * @param userController Instance of <code>UserController</code> for superconstructor use.
     */
    public DeleteRequestController(UserController userController) {
        super(userController);
    }

    /**
     * Removes the specified <code>Request</code> from the system.
     * @param request UUID of the <code>Request</code> that is to be removed.
     */
    public void deleteRequest(UUID request) throws RequestNotFoundException {
        if (requestManager.isExistingRequest(request)) {
            requestManager.removeRequest(request);
        }
        else{
            throw new RequestNotFoundException("Request not found.");
        }
    }
}
