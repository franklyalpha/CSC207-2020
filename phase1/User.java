import java.sql.Array;
import java.util.*;
import java.time.*;

public class User {
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

    public User(String name, String password, String type) {
        username = name;
        this.password = password;
        userType = type;
        chatroom = new HashMap<String, UUID>();
        activities = new HashMap<LocalDateTime[], UUID>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getUserType(){
        return userType;
    }

    public HashMap<String, UUID> getChatroom() {
        return chatroom;
    }

    public HashMap<LocalDateTime[], UUID> getActivities() {
        return activities;
    }

    public void setUsername(String newname) {
        username = newname;
    }
    /**
     * will set arraylists and hashmaps in use-case class. however, when returning something outside use-case,
     * it would be safer to make a DEEP copy of variable so that any possible modifications won't affect original data.
     */

}
