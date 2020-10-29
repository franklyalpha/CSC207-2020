import java.util.*;

public class User {
    private String username;
    private String password;
    private String userType;
    private HashMap<String, Integer> chatroom;
    private ArrayList<String> activities;

    public User(String name, String password, String type) {
        username = name;
        this.password = password;
        userType = type;
        chatroom = new HashMap<String, Integer>();
        activities = new ArrayList<String>();
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

    public HashMap<String, Integer> getChatroom() {
        return chatroom;
    }

    public ArrayList<String> getActivities() {
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
