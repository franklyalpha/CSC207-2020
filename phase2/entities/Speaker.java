package entities;

import globallyAccessible.UserType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Speaker extends User implements Serializable {

    /**
     * List of <code>UUID</code> corresponding to  requests submitted by this user.
     */
    private ArrayList<UUID> requests;

    /**
     * Creates a new entities.User with given name, password, and type.
     *
     * @param name     the valid name of this user, which help to identify.
     * @param password the valid password of this user.
     */
    public Speaker(String name, String password) {
        super(name, password, UserType.SPEAKER);
    }

    /**
     * Getter for list of <code>UUID</code>s corresponding to requests created by this user.
     * @return ArrayList of <code>UUID</code> of requests created by this user.
     */
    public ArrayList<UUID> getRequests() {
        return requests;
    }

    /**
     * Changes the list of <code>Request</code>s made by this user to be the new input list.
     * @param requests New list with which the existing list of <code>Request</code>s will be replaced with.
     */
    public void setRequests(ArrayList<UUID> requests) {
        this.requests = requests;
    }

}
