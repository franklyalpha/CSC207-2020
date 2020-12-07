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

    /**
     * Changes the details of the specified <code>Request</code> to the new specified description.
     * @param request UUID of the <code>Request</code> for which the description is to be modified.
     * @param newDetails String representing the new description.
     */
    public void modifyRequestDetails(UUID request, String newDetails) throws RequestNotFoundException {
        if (requestManager.isExistingRequest(request)) {
            requestManager.modifyDetails(request, newDetails);
        }
        else{
            throw new RequestNotFoundException("Request not found.");
        }
    }

    /**
     * Changes the subject of the specified <code>Request</code> to the new specified subject.
     * @param request UUID of the <code>Request</code> for which the subject is to be modified.
     * @param newSubject String representing the new subject.
     */
    public void modifyRequestSubject(UUID request, String newSubject) throws RequestNotFoundException {
        if (requestManager.isExistingRequest(request)) {
            requestManager.modifySubject(request, newSubject);
        }
        else{
            throw new RequestNotFoundException("Request not found.");
        }
    }

}

