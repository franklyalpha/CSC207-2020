package useCases;

import entities.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Represents a <code>RequestManager</code> that is responsible for any interactions with <code>Request</code> entities.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 2.0
 */

public class RequestManager implements java.io.Serializable{

    /**
     * ArrayList of instances of <code>Request</code>s that are still pending (i.e. <code>request.status == false</code>).
     */
    private ArrayList<Request> pendingRequests;

    /**
     * ArrayList of instances of <code>Request</code>s that have been handled (i.e. <code>request.status == true</code>).
     */
    private ArrayList<Request> doneRequests;

    /**
     * Creates <code>RequestManager</code> with a blank list of pending requests and a blank list of handled requests.
     */
    public RequestManager(){
        pendingRequests = new ArrayList<>();
        doneRequests = new ArrayList<>();
    }

    /**
     * Creates a new <code>Request</code> instance, storing the specified username and description, and returns the UUID
     * of this new <code>Request</code>.
     *
     * @param username String representing username of user who made this <code>Request</code>.
     * @param subject String representing a short concise description of the nature of this <code>Request</code>.
     * @param description String representing a more detailed description of this <code>Request</code>.
     * @return the UUID of the newly created <code>Request</code>.
     */
    public UUID createRequest(String username, String subject, String description){
        Request newRequest = new Request(username, subject, description);
        this.pendingRequests.add(newRequest);
        return newRequest.getId();
    }

}

