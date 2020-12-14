package entities;

import java.util.*;
import java.time.*;
import globallyAccessible.*;

/**
 * A representation of entities.User.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public abstract class User implements java.io.Serializable {

    /**
     * Name for each <code>User</code>
     */
    private String username;

    /**
     * password for each <code>User</code> to log-in.
     */
    private String password;

    /**
     * a string representing the type of the <code>User</code>.
     */
    private final UserType type;

    /**
     * a HashMap which holds the id of the <code>MessageRoom</code> this user attended and their description.
     */
    private final HashMap<String, UUID> MessageRoom;

    /**
     * a HashMap which holds the id of the <code>Event</code> this user signed in and their time.
     */
    private final HashMap<LocalDateTime[], UUID> events;

    /**
     * Creates a new entities.User with given name, password, and type.
     * @param name the valid name of this user, which helps to identify.
     * @param password the valid password of this user.
     * @param type the type of this user: Attendee, Organizer, Speaker, Administrator.
     */
    public User(String name, String password, UserType type) {
        username = name;
        this.type = type;
        this.password = password;
        MessageRoom = new HashMap<>();
        events = new HashMap<>();
    }

    /**
     * Gets the name of this user.
     * @return the user's name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of this user.
     * @return the user's password.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Gets the type of this user.
     * @return the user's type as organizer, speaker, attendee, or administrator.
     */
    public UserType getUserType(){
        return this.type;
    }

    /**
     * Gets the Message Room which this user belong to.
     * @return the Map of a id of the Message Room and the name of the Message Room.
     */
    public HashMap<String, UUID> getMessageRoom() {
        return MessageRoom;
    }

    /**
     * Gets the Event which this user belong to.
     * @return the Map of a period of the Event and the id of the Message Room.
     */
    public HashMap<LocalDateTime[], UUID> getEvents() {
        return events;
    }

    /**
     * Changes the name of this user with the given new name.
     * @param  newName the new name of this user.
     */
    public void setUsername(String newName) {
        username = newName;
    }

    /**
     * Changes the password of this user with the given new password.
     * @param  newPassword the new password of this user.
     */
    public void setPassword(String newPassword){ password = newPassword;}
}
