import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.time.*;

public class UserManager {
    /**
     * features should include:
     * its own constructor
     * store different list of users;(will return getter of organizer and speakers)
     * store the user who is currently using the system;
     * being able to instantiate a user and store in proper list(with unique id, using UUID);
     * being able to check whether someone is a user (i.e, is in one of the lists);
     * being able to check whether the password is correct(return true)
     * able to check whether a given user/activity is in another's contact list;
     * being able to return scheduled activities of the user
     * being able to add a new activity to schedule
     * being able to add a new chatroom the person is in
     * being able to delete an activity participated, and being
     * able to dissociate the specific chatroom the user is in;
     * a method to store data(either implements serializable or use a gateway interface)
     * (being able to reset userOnAir, in case the user wants to log out)
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
                addUser(org, organizers);
                break;
            case "attendee":
                User att = new User(name, password, type);
                addUser(att, attendee);
                break;
            case "speaker":
                User spe = new User(name, password, type);
                addUser(spe, speakers);
                break;
        }
        // return name: just in case to notify users about their exact username;
    }

    private void addUser(User users, ArrayList<User> type){
        type.add(users);
        num_user += 1;
    }

    public int isUser(String username, String type) {
        int returnindex = 0;
        switch (type) {
            case "organizer":
                returnindex = checkUserIndex(username, organizers);
                break;
            case "attendee":
                returnindex = checkUserIndex(username, attendee);
                break;
            case "speaker":
                returnindex = checkUserIndex(username, speakers);
                break;
        }
        return returnindex;
    }

    private int checkUserIndex(String username, ArrayList<User> type){
        for (User users : type){
            if (users.getUsername() == username){
                return type.indexOf(users) + 1;
            }
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

    public HashMap<LocalDateTime[], String> schedules(){
        return userOnAir.getActivities();
    }

    public boolean isFree(LocalDateTime[] actinterv){
        HashMap<LocalDateTime[], String> userSchedule = userOnAir.getActivities();
        for(LocalDateTime[] interv: userSchedule.keySet()){
            LocalDateTime start = interv[0].minusSeconds(150);
            LocalDateTime end = interv[1].plusSeconds(150);
            if (start.isBefore(actinterv[0]) && end.isAfter(actinterv[1])){
                return false;
            }
        }
        return true;
    }

    public void addSchedule(){
        return;
    }


}