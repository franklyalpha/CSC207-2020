package entities;

import java.util.UUID;

/**
 * Represents a user's request to conference Organizers. All organizers see the same list of requests and can either mark them as 'Pending' or 'Done'
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 2.0
 */

public class Request implements java.io.Serializable{

    /**
     * Unique identifier for each <code>Request</code>.
     */
    private final UUID id;  // unique identifier for each entities.Request

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
     * String representing short summary of the nature of this <code>Request</code>. (i.e. 'Lactose-intolerant',
     * 'Requires visual language interpreter', etc.).
     */
    private String subject;

    /**
     * String representing longer, more specific details about this particular <code>Request</code>
     * (i.e. 'I am allergic to peanuts, spinach, crab, and whole wheat bread.').
     */
    private String details;

    /**
     * Creates <code>Request</code> with a randomly generated UUID, the username of the user making this request,
     * details regarding this request, and a default status value of <code>false</code>, indicating it has not yet
     * been addressed.
     */
    public Request(String username, String subject, String description){
        id = UUID.randomUUID();
        status = false;
        sender = username;
        this.subject = subject;
        details = description;
    }

    /**
     * Gets the unique identifier for this <code>Request</code>.
     * @return The UUID corresponding to this <code>Request</code>.
     */
    public UUID getId(){
        return this.id;
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
    public String getDetails(){
        return this.details;
    }

    /**
     * Gets this request's subject.
     * @return String corresponding to this request's subject.
     */
    public String getSubject(){
        return this.subject;
    }

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

    /**
     * Modifies this <code>Request</code>'s subject to be the new given <code>String</code>.
     */
    public void setSubject(String newSubject){
        this.subject = newSubject;
    }

    @Override
    public String toString() {
        String stat = "PENDING";
        if (this.status) {
             stat = "COMPLETE";
        }
        return "Submitted by: [ " + sender + " ]    Status: [ " + stat + " ]\n------------------------------------------------------\n" +
                "Subject: " + subject + "\nDetails: " + details + "\n------------------------------------------------------";
    }


}
