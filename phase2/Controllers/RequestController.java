package Controllers;

import globallyAccessible.RequestNotFoundException;
import useCases.RequestManager;
import java.util.ArrayList;
import java.util.UUID;

public class RequestController extends AbstractController {

    /**
     * Constructor for an instance of <code>RequestController</code>
     * @param userController Instance of <code>UserController</code> to use the superconstructor.
     */
    public RequestController(UserController userController) {
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

