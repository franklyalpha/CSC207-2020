import java.sql.Array;
import java.util.*;
import java.time.*;

public class User {
    /**
     * A representation of User.
     *
     * @author
     * @version
     */

    private String username;
    private String password;
    private String userType;
    private HashMap<String, UUID> chatroom;
    private HashMap<LocalDateTime[], UUID> activities;
    // need setter;
    // including ArrayList.remove()

    /**
     * may need to consider associating other account types, by creating another hashmap.
     */
    /**
     * may need to consider associating other account types, by creating another hashmap.
     */

    /**
     * Creates a new User with given name, password, and type.
     *
     * @param name the valid (?unique) name of this user, which help to identify.
     * @param password the valid password of this user.
     * @param type the type of this user: Attendee, Organizer, Speakers.
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
     *
     * @return the user's name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of this user.
     *
     * @return the user's password.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Gets the type of this user.
     *
     * @return the user's type as organizer, speaker, or attendee.
     */
    public String getUserType(){
        return userType;
    }

    /**
     * Gets the chatroom which this user belong to.
     *
     * @return the Map of a description of the chatroom and the name of the chatroom.
     */
    public HashMap<String, UUID> getChatroom() {
        return chatroom;
    }


    /**
     * Gets the chatroom which this user belong to.
     *
     * @return the Map of a description of the chatroom and the name of the chatroom.
     */
    public HashMap<LocalDateTime[], UUID> getActivities() {
        return activities;
    }

    /**
     * Changes the name of this user with the given new name.
     *
     * @param  newname the new password of this user.
     */
    public void setUsername(String newname) {
        username = newname;
    }

    /**
     * Changes the password of this user with the given new password.
     *
     * @param  newpassword the new password of this user.
     */
    public void setPassword(String newpassword){ password = newpassword;}


    /**
     * will set arraylists and hashmaps in use-case class. however, when returning something outside use-case,
     * it would be safer to make a DEEP copy of variable so that any possible modifications won't affect original data.
     */

}
