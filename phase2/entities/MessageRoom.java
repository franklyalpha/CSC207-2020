package entities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a Message Room where <code>Users</code> can send messages to one another.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class MessageRoom implements java.io.Serializable{

    /**
     * Unique identifier for each <code>MessageRoom</code>.
     */
    private final UUID id;  // unique identifier for each entities.Chatroom

    /**
     * Holds the username of all Users in this <code>MessageRoom</code>.
     */
    private final ArrayList<String> usersInvolved;  // holds username of all Users in this entities.Chatroom

    /**
     * Holds all messages sent in this <code>MessageRoom</code>.
     */
    private final ArrayList<String> historicalChats;  // holds previously sent messages from this entities.Chatroom

    /**
     * Creates <code>MessageRoom</code> with a randomly generated UUID, blank chat history, and empty list of chat participants.
     */
    public MessageRoom(){
        id = UUID.randomUUID();  // assign id
        usersInvolved = new ArrayList<>();  // If no given users, empty by default
        historicalChats = new ArrayList<>();  // start with empty chatHistory
    }

    /**
     * Creates <code>MessageRoom</code> with a randomly generated UUID, blank chat history, and specified chat participants.
     * @param users ArrayList of usernames of <code>Users</code> participating in this <code>MessageRoom</code>.
     */
    public MessageRoom(ArrayList<String> users){
        id = UUID.randomUUID();  // assign id
        usersInvolved = users;  // specify users in this Chatroom by parameter
        historicalChats = new ArrayList<>();  // start with empty chatHistory
    }

    /**
     * Gets the unique identifier for this <code>MessageRoom</code>.
     * @return The UUID corresponding to this <code>MessageRoom</code>.
     */
    public UUID getId(){ return this.id; }

    /**
     * Gets a list of Users in this <code>MessageRoom</code>.
     * @return A list of strings corresponding to the usernames of <code>Users</code> in this <code>MessageRoom</code>.
     */
    public ArrayList<String> getUsersInvolved(){
        return this.usersInvolved;
    }

    /**
     * Gets a list of messages sent in this <code>MessageRoom</code>.
     * @return A list of strings corresponding to messages sent in this <code>MessageRoom</code>.
     */
    public ArrayList<String> getHistoricalChats(){
        return this.historicalChats;
    }



}



