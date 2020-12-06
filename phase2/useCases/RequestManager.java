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

public class RequestManager {

    /**
     * Creates <code>ChatroomManager</code> with a blank list of conference chatrooms, blank list of private chatrooms, and an empty <code>Chatroom</code> (i.e. no organizers or speakers yet).
     */
    public RequestManager(){

    }

    /**
     * Creates a new <code>Request</code> instance, storing the specified username and description, and returns the UUID
     * of this new <code>Request</code>.
     *
     * @param username String representing username of user who made this <code>Request</code>.
     * @param subject String representing a short concise description of the nature of this <code>Request</code>.
     * @param description String representing a more detailed description of this <code>Request</code>.
     * @return the UUID of the newly created <code>Chatroom</code>.
     */
    public UUID createRequest(String username, String subject, String description){
        Request newRequest = new Request(username, subject, description);

        return newRequest.getId();
    }

}

