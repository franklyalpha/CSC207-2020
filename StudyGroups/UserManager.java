import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserManager {
    /**
     * features should include:
     * its own constructor
     * store different list of users;(will return getter of organizer and speakers)
     * being able to instantiate a user and store in proper list(with unique id, using UUID);
     * being able to check whether someone is a user (i.e, is in one of the lists);
     * being able to check whether the password is correct(return true)
     * able to check whether a given user is in another's contact list;
     * being able to return scheduled activities of the user
     * being able to add a new activity to schedule
     * being able to add a new chatroom the person is in
     * being able to delete an activity participated, and being
     * able to dissociate the specific chatroom the user is in;
     * a method to store data(either implements serializable or use a gateway interface)
     */
    private ArrayList<User> organizers;
    private ArrayList<User> speakers;
    private ArrayList<User> attendee;
    private int num_user;
    private User userOnAir;

    public UserManager() {
        organizers = new ArrayList<>();
        speakers = new ArrayList<>();
        attendee = new ArrayList<>();
        num_user = 1;
        userOnAir = null;
    }

    public void CreateUser(String username, String password, String type) {
        String name = username + num_user;
        switch (type) {
            case "organizer":
                User org = new User(name, password, type);
                organizers.add(org);
                num_user += 1;
                break;
            case "attendee":
                User att = new User(name, password, type);
                attendee.add(att);
                num_user += 1;
                break;
            case "speaker":
                User spe = new User(name, password, type);
                speakers.add(spe);
                num_user += 1;
                break;
        }
        // return name: just in case to notify users about their exact username;
    }

    public int isUser(String username, String type) {
        switch (type) {
            case "organizer":
                for (User org : organizers) {
                    if (org.getUsername() == username) {
                        return organizers.indexOf(org) + 1;
                    }
                }
                break;
            case "attendee":
                for (User att : attendee) {
                    if (att.getUsername() == username) {
                        return attendee.indexOf(att) + 1;
                    }
                }
                break;
            case "speaker":
                for (User spe : speakers) {
                    if (spe.getUsername() == username) {
                        return speakers.indexOf(spe) + 1;
                    }
                }
                break;
        }
        return 0;
    }

    public boolean loginCheck(int index, String type, String passcode){
        switch (type) {
            case "organizer":
                if(organizers.get(index - 1).getPassword() == passcode){
                    userOnAir = organizers.get(index - 1);
                    return true;
                }
                break;
            case "attendee":
                if(attendee.get(index - 1).getPassword() == passcode){
                    userOnAir = attendee.get(index - 1);
                    return true;
                }
                break;
            case "speaker":
                if(speakers.get(index - 1).getPassword() == passcode){
                    userOnAir = speakers.get(index - 1);
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean contactable(String username){
        HashMap<String, Integer> contacts = userOnAir.getChatroom();
        if (contacts.containsKey(username)){
            return true;
        }
        return false;
    }


}