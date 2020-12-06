package Controllers;

import globallyAccessible.RequestNotFoundException;
import useCases.RequestManager;

import java.util.UUID;

/**
 * Controller handling calls to modify an existing <code>Request</code>.
 */
public class ModifyRequestController extends RequestController {

    /**
     * An instance of <code>RequestManager</code>.
     */
    private RequestManager requestManager;

    /**
     * Creates an instance of <code>ModifyRequestController</code>.
     * @param userController Instance of <code>UserController</code> for superconstructor use.
     */
    public ModifyRequestController(UserController userController) {
        super(userController);
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
