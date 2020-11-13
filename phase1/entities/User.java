package entities;

import java.sql.Array;
import java.util.*;
import java.time.*;

/**
 * A representation of entities.User.
 * @author Group 0168
 * @version 1.0
 */

public class User implements java.io.Serializable {

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
    private String userType;

    /**
     * a HashMap which holds the id of the <code>Chatroom</code> this user attended and their description.
     */
    private HashMap<String, UUID> chatroom;

    /**
     * a HashMap which holds the id of the <code>Activity</code> this user signed in and their description.
     */
    private HashMap<LocalDateTime[], UUID> activities;
    // in phase 2, will create a clone corresponding activities for
    // new modified schedules;  will be a shallow copy

    // need setter;
    // including ArrayList.remove()

    /**
     * Creates a new entities.User with given name, password, and type.
     * @param name the valid name of this user, which help to identify.
     * @param password the valid password of this user.
     * @param type the type of this user: Attendee, Organizer, Speaker.
     */
    public User(String name, String password, String type) {
        username = name;
        this.password = password;
        userType = type;
        chatroom = new HashMap<String, UUID>();
        activities = new HashMap<LocalDateTime[], UUID>();
    }

    /**
     * Gets the name of this user.
     * @return the user's name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of this user。
     * @return the user's password.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Gets the type of this user。
     * @return the user's type as organizer, speaker, or attendee.
     */
    public String getUserType(){
        return userType;
    }

    /**
     * Gets the chatroom which this user belong to.
     * @return the Map of a description of the chatroom and the name of the chatroom.
     */
    public HashMap<String, UUID> getChatroom() {
        return chatroom;
    }


    /**
     * Gets the Activity which this user belong to.
     * @return the Map of a description of the Activity and the name of the chatroom.
     */
    public HashMap<LocalDateTime[], UUID> getActivities() {
        return activities;
    }

    /**
     * Changes the name of this user with the given new name.
     * @param  newname the new password of this user.
     */
    public void setUsername(String newname) {
        username = newname;
    }

    /**
     * Changes the password of this user with the given new password.
     * @param  newpassword the new password of this user.
     */
    public void setPassword(String newpassword){ password = newpassword;}


    /**
     * will set arraylists and hashmaps in use-case class. however, when returning something outside use-case,
     * it would be safer to make a DEEP copy of variable so that any possible modifications won't affect original data.
     */

}
