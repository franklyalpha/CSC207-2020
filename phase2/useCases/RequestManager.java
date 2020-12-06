package useCases;

import entities.MessageRoom;
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
     * ArrayList of all instances of <code>Request</code>s.
     */
    private ArrayList<Request> requestList;

    /**
     * Creates <code>RequestManager</code> with a blank list of pending requests, a blank list of handled requests, and a list of all requests.
     */
    public RequestManager(){
        pendingRequests = new ArrayList<>();
        doneRequests = new ArrayList<>();
        requestList = new ArrayList<>();
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
        this.requestList.add(newRequest);
        return newRequest.getId();
    }

    /**
     * Handles the specified <code>Request</code>, setting it's status to <code>true</code>, moving it out of the list
     * of pending requests and into the list of handled requests.
     * @param requested UUID of the <code>Request</code> to be handled.
     */
    public void handleRequest(UUID requested){
        ArrayList<Request> tmp = new ArrayList<>(pendingRequests);
        int i = 0;  // index counter
        for (Request request1 : tmp){
            if (request1.getId().equals(requested)){           // check the UUID to make sure we have the right entities.Request
                request1.setStatus(true);
                this.doneRequests.add(request1);
                this.pendingRequests.remove(i);
            }
            i = i + 1;
        }
    }

    /**
     * Gets the username of the sender of the specified <code>Request</code>.
     * @param requested UUID of the <code>Request</code> to get the sender's username.
     * @return String representing the username of the user who sent this <code>Request</code>.
     */
    public String getSender(UUID requested){
        String result = "";
        ArrayList<Request> tmp = new ArrayList<>(requestList);
        for (Request request1 : tmp){
            if (request1.getId().equals(requested)){           // check the UUID to make sure we have the right entities.Request
               result = request1.getSender();
               break;
            }
        }
        return result;
    }

    /**
     * Modifies the details of the <code>Request</code> specified by UUID to be the given updated description.
     * @param request UUID of the <code>Request</code> for which the description will be updated.
     * @param newDetails String representing the new updated description to be used for replacement.
     */
    public void modifyDetails(UUID request, String newDetails){
        ArrayList<Request> tmp = new ArrayList<>(requestList);
        for (Request request1 : tmp){
            if (request1.getId().equals(request)){           // check the UUID to make sure we have the right entities.Request
               request1.setDetails(newDetails);
                break;
            }
        }
    }

    public void removeRequest(UUID request){
        int i = 0;  // index counter
        ArrayList<Request> tmp = new ArrayList<>(requestList);
        for (Request request1 : tmp) {
            if (request1.getId().equals(request)) {           // check the UUID to make sure we have the right entities.Request
                this.requestList.remove(i);
                break;
            }
            i = i + 1;
        }
        i = 0;
        tmp = new ArrayList<>(doneRequests);
        for (Request request1 : tmp) {
            if (request1.getId().equals(request)) {
                this.doneRequests.remove(i);
                break;
            }
            i = i + 1;
        }
        i = 0;
        tmp = new ArrayList<>(pendingRequests);
        for (Request request1 : tmp) {
            if (request1.getId().equals(request)) {
                this.pendingRequests.remove(i);
                break;
            }
            i = i + 1;
        }
    }

}



