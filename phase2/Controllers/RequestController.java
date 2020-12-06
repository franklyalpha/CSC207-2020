package Controllers;

import globallyAccessible.RequestNotFoundException;
import useCases.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RequestController extends AbstractController {
    public RequestController(UserController userController) {
        super(userController);
    }

    /**
     * Creates a new <code>Request</code> with the current user and the user specified by <code>userName</code>
     * as the participants.
     * @param username String representing username of user who made this <code>Request</code>.
     * @param subject String representing a short concise description of the nature of this <code>Request</code>.
     * @param description String representing a more detailed description of this <code>Request</code>.
     * @return the UUID of the newly created <code>Request</code>.
     */
    public UUID newRequestCreator(String username, String subject, String description){
        return RequestManager.createRequest(username, subject, description);
    }


}

