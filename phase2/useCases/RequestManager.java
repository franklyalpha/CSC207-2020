package useCases;

import entities.Request;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a <code>RequestManager</code> that is responsible for any interactions with <code>Request</code> entities.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 2.0
 */

public class RequestManager extends AbstractSerializableManager implements java.io.Serializable{

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
     * ArrayList of all IDs of existing <code>Request</code>s.
     */
    private ArrayList<UUID> requestIDList;

    /**
     * Creates <code>RequestManager</code> with a blank list of pending requests, a blank list of handled requests,
     * a list of all request instances, and a list of all request IDs.
     */
    public RequestManager(){
        pendingRequests = new ArrayList<>();
        doneRequests = new ArrayList<>();
        requestList = new ArrayList<>();
        requestIDList = new ArrayList<>();
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
        this.requestIDList.add(newRequest.getId());
        return newRequest.getId();
    }

    /**
     * Handles the specified <code>Request</code>.
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

    /**
     * Modifies the subject of the <code>Request</code> specified by UUID to be the given updated subject.
     * @param request UUID of the <code>Request</code> for which the description will be updated.
     * @param newSubject String representing the new updated subject to be used for replacement.
     */
    public void modifySubject(UUID request, String newSubject){
        ArrayList<Request> tmp = new ArrayList<>(requestList);
        for (Request request1 : tmp){
            if (request1.getId().equals(request)){           // check the UUID to make sure we have the right entities.Request
                request1.setSubject(newSubject);
                break;
            }
        }
    }

    /**
     * Removes the specified <code>Request</code> from all locations (i.e. pending/handled requests, master request list, master ID list).
     * @param request UUID of the <code>Request</code> to be removed.
     */
    public void removeRequest(UUID request){
        requestIDList.remove(request);
        removeFromListHelper(requestList, request);
        removeFromListHelper(doneRequests, request);
        removeFromListHelper(pendingRequests, request);
    }

    /**
     * Helper function for removeRequest(). Removes the specified <code>Request</code> from the specified ArrayList.
     * @param removeFrom ArrayList from which the item is to be removed.
     * @param toRemove <code>Request</code> to be removed from the specified ArrayList.
     */
    private void removeFromListHelper(ArrayList<Request> removeFrom, UUID toRemove) {
        int i = 0;  // index counter
        ArrayList<Request> tmp = new ArrayList<>(removeFrom);
        for (Request request1 : tmp) {
            if (request1.getId().equals(toRemove)) {           // check the UUID to make sure we have the right entities.Request
                removeFrom.remove(i);
                break;
            }
            i = i + 1;
        }
    }

    /**
     * Checks whether or not the specified <code>Request</code> exists.
     * @param request UUID of the <code>Request</code> to check the validity of.
     * @return <code>true</code> if the <code>Request</code> exists, <code>false</code> if not.
     */
    public boolean isExistingRequest(UUID request){
        return requestIDList.contains(request);
    }

    /**
     * Retrieves the instance of <code>Request</code> corresponding to the specified UUID.
     * @param requestID UUID of the <code>Request</code> to be retrieved.
     * @return Returns the instance of <code>Request</code> that has the input UUID.
     */
    public Object[] findRequest(UUID requestID) {
        ArrayList<Request> tmp = new ArrayList<>(requestList);
        for (Request request1 : tmp) {
            String status = "PENDING";
            if (request1.getStatus()){
                status = "DONE";
            }
            if (request1.getId().equals(requestID)) {   // check the UUID to make sure we have the right entities.Request
                return new Object[]{request1.getId(), request1.getDetails(), request1.getSubject(), status};
            }
        }
        return new Object[]{};
    }

    /**
     * Outputs all requests made by any user in a list-like format.
     */
    public void viewAllRequests() {
        ArrayList<Request> tmp = new ArrayList<>(getRequestList());
        int i = 0;
        for (Request req : tmp){
            System.out.println("[" + i + "]\n " + req.toString());
            i = i + 1;
        }
    }

    /**
     * Outputs all pending requests made by any user in a list-like format.
     */
    public void viewPendingRequests() {
        ArrayList<Request> tmp = new ArrayList<>(getPendingRequests());
        int i = 0;
        for (Request req : tmp){
            System.out.println("[" + i + "]\n " + req.toString());
            i = i + 1;
        }
    }

    /**
     * Returns list of all existing <code>Request</code>s
     * @return ArrayList of existing instances of <code>Request</code>s
     */
    public ArrayList<Request> getRequestList(){
        return this.requestList;
    }

    /**
     * Returns list of all pending <code>Request</code>s
     * @return ArrayList of instances of <code>Request</code>s that are pending.
     */
    public ArrayList<Request> getPendingRequests(){
        return this.pendingRequests;
    }

    /**
     * Returns list of all handled <code>Request</code>s
     * @return ArrayList of instances of <code>Request</code>s that have been handled.
     */
    public ArrayList<Request> getDoneRequests(){
        return this.doneRequests;
    }

}



