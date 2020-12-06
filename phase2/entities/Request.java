package entities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a user's request to conference Organizers. All organizers see the same list of requests and can either mark them as 'Pending' or 'Done'
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 2.0
 */

public class Request{

    /**
     * Boolean representing whether or not this <code>Request</code> has been handled (<code>false</code> for 'Pending',
     *  <code>true</code> for 'Done').
     */
    private boolean status;

    /**
     * String representing the username of the user who created this <code>Request</code>.
     */
    private final String sender;

    /**
     * String representing user-specific details about this particular <code>Request</code> (i.e. 'Lactose-intolerant', 'Requires visual language interpreter', etc.).
     */
    private String details;

    /**
     * Creates <code>Request</code> with the username of the user making this request, details regarding this request,
     * and a default status value of <code>false</code>, indicating it has not yet been addressed.
     */
    public Request(String username, String description){
        status = false;
        sender = username;
        details = description;
    }

    /**
     * Gets the username of the user who made this request.
     * @return String corresponding to the username of the user who made this request.
     */
    public String getSender(){
        return this.sender;
    }

    /**
     * Gets status of this request; <code>true</code> indicates request has been addressed and <code>false</code>
     * indicates it is pending.
     * @return Boolean corresponding to the request status.
     */
    public boolean getStatus(){
        return this.status;
    }

    /**
     * Gets this request's details.
     * @return String corresponding to this request's details.
     */
    public String getDetails(){ return this.details; }

    /**
     * Sets the status of this <code>Request</code> to the given value.
     */
    public void setStatus(boolean newStatus){
        this.status = newStatus;
    }

    /**
     * Modifies this <code>Request</code>'s details to be the new given <code>String</code>.
     */
    public void setDetails(String newDetails){
        this.details = newDetails;
    }


}
