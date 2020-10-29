import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String userType;
    private ArrayList<Integer> chatroom;
    private ArrayList<String> activities;

    public User(String name, String password, String type) {
        username = name;
        this.password = password;
        userType = type;
        chatroom = new ArrayList<Integer>();
        activities = new ArrayList<String>();
    }

    public String getUsername() {
        return username;
    }

    public boolean setUsername(String newname) {
        username = newname;
        return true;
    }
}
