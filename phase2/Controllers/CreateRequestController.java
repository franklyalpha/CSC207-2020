package Controllers;

import globallyAccessible.RequestNotFoundException;
import useCases.RequestManager;

import java.util.UUID;

/**
 * Controller handling calls to create a new <code>Request</code>.
 */
public class CreateRequestController extends RequestController {

    /**
     * An instance of <code>RequestManager</code>.
     */
    private RequestManager requestManager;

    /**
     * Creates an instance of <code>CreateRequestController</code>.
     * @param userController Instance of <code>UserController</code> for superconstructor use.
     */
    public CreateRequestController(UserController userController) {
        super(userController);
    }

    /**
     * Creates a new <code>Request</code> with the current user and the user specified by <code>userName</code>
     * as the participants.
     * @param subject String representing a short concise description of the nature of this <code>Request</code>.
     * @param description String representing a more detailed description of this <code>Request</code>.
     * @return the UUID of the newly created <code>Request</code>.
     */
    public UUID newRequestCreator(String subject, String description){
        String username = userManager.getUsername();
        return requestManager.createRequest(username, subject, description);
    }
}
