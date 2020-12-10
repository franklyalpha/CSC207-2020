package Controllers;

import entities.Request;
import globallyAccessible.RequestNotFoundException;
import useCases.AttendeeManager;
import useCases.RequestManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class RequestController extends AbstractController {

    /**
     * An instance of <code>AttendeeManager</code>.
     */
    public AttendeeManager attendeeManager;


    /**
     * Constructor for an instance of <code>RequestController</code>
     * @param userController Instance of <code>UserController</code> to use the superconstructor.
     */
    public RequestController(UserController userController) {
        super(userController);
        try{
            attendeeManager = new AttendeeManager(userManager);
        } catch (ClassCastException e){ }

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
        UUID result = requestManager.createRequest(username, subject, description);
        ArrayList<UUID> tmp = attendeeManager.getUserRequests();
        tmp.add(result);
        attendeeManager.setUserRequests(tmp);
        return result;
    }

    /**
     * Removes the specified <code>Request</code> from the system.
     * @param request UUID of the <code>Request</code> that is to be removed.
     */
    public void deleteRequest(UUID request) throws RequestNotFoundException {
        if (requestManager.isExistingRequest(request)) {
            requestManager.removeRequest(request);
        } else {
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
        } else {
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
        } else {
            throw new RequestNotFoundException("Request not found.");
        }
    }

    /**
     * Outputs all requests made by the user in a list-like format.
     */
    public void viewUserRequests() {
        ArrayList<Object[]> output = new ArrayList<>();
        for (UUID req : attendeeManager.getUserRequests()){
            try {
                output.add(findRequest(req));
            } catch (RequestNotFoundException ex){
                System.out.println("Invalid request ID: there is no existing request with that ID.");
            }
        }
        int i = 0;
        for (Object[] req1 : output){
            j;ldskajf
            System.out.println("[" + i + "]\n " + req1.toString());
            i = i + 1;
        }
    }

    /**
     * Outputs all pending requests made by any user in a list-like format.
     */
    public void viewPendingRequests() {
        ArrayList<Request> tmp = new ArrayList<>(requestManager.getPendingRequests());
        int i = 0;
        for (Request req : tmp){
            System.out.println("[" + i + "]\n " + req.toString());
            i = i + 1;
        }
    }

    /**
     * Outputs all requests made by any user in a list-like format.
     */
    public void viewAllRequests() {
        ArrayList<Request> tmp = new ArrayList<>(requestManager.getRequestList());
        int i = 0;
        for (Request req : tmp){
            System.out.println("[" + i + "]\n " + req.toString());
            i = i + 1;
        }
    }

    /**
     * Retrieves the instance of <code>Request</code> corresponding to the specified UUID.
     * @param requestID UUID of the <code>Request</code> to be retrieved.
     * @return Returns the instance of <code>Request</code> that has the input UUID.
     */
    public Object[] findRequest(UUID requestID) throws RequestNotFoundException {
        if (requestManager.isExistingRequest(requestID)) {
            return requestManager.findRequest(requestID);
        } else {
            throw new RequestNotFoundException("Request not found.");
        }
    }

    /**
     * Returns list of all existing <code>Requests</code>
     * @return ArrayList of existing instances of <code>Request</code>s
     */
    public ArrayList<Request> getAllRequest(){
        return requestManager.getRequestList();
    }

    /**
     * Returns list of of <code>UUID</code>s of requests submitted by the user.
     * @return ArrayList of <code>UUID</code>s of requests submitted by the user.
     */
    public ArrayList<UUID> getUserRequests(){ return attendeeManager.getUserRequests(); }

    /**
     * Changes the list of <code>UUID</code>s to the new input list.
     * @param requests New list with which the existing list of <code>UUID</code>s will be replaced with.
     */
    public void setUserRequests(ArrayList<UUID> requests){
        attendeeManager.setUserRequests(requests);
    }

}

