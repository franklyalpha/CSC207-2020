import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.time.*;

//public class UserManager implements java.io.Serializable

public class UserManager{
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
     * able to dissociate the specific chatroom the user is in;--done to this step
     * a method to store data(either implements serializable or use a gateway interface for fileIO)
     * (being able to reset userOnAir, in case the user wants to log out - phase 2)
     */

    /**
     * will consider adding another type-indicator String[], stores all possible type names.
     * therefore modify below all strings of types as: TypeString[0], TypeString[1],TypeString[2].
     */

    // call modification and reset

    private ArrayList<User> organizers;
    private ArrayList<User> speakers;
    private ArrayList<User> attendee;
    // regex
    private int num_user;
    private User userOnAir;
    ArrayList<User>[] typeArray = new ArrayList[3];
//    private ArrayList<User>[] typearray = [organi]

    // may consider simple factory design pattern in controller layer;
    // make sure organizers don't access speaker's controllers  !!!!! 

    public UserManager() {
        organizers = new ArrayList<>();
        speakers = new ArrayList<>();
        attendee = new ArrayList<>();
        num_user = 1;
        userOnAir = null;
        typeArray[0] = organizers;
        typeArray[1] = speakers;
        typeArray[2] = attendee;
    }

    /**
     * should consider using DEPENDENCY INJECTION PATTERN in phase two, in which
     * an independent account creator class would be implemented, and only the function
     * 'add user would be kept, and being made public'
     */

    private int typeChoice(String usertype){
        return switch (usertype) {
            case "organizer" -> 0;
            case "speaker" -> 1;
            case "attendee" -> 2;
            default -> -1;
        };
    }

    public void CreateUser(String username, String password, String type) {
        String name = username + num_user;
        int types = typeChoice(type);
        User org = new User(name, password, type);
        addUser(org, typeArray[types]);
        // return name: just in case to notify users about their exact username;
    }

    private void addUser(User users, ArrayList<User> type){
        type.add(users);
        num_user += 1;
    }

    public int isUser(String username, String type) {
        int returnindex = 0;
        int types = typeChoice(type);
        returnindex = checkUserIndex(username, typeArray[types]);
        return returnindex;
    }

    private int checkUserIndex(String username, ArrayList<User> type){
        for (User users : type){
            if (users.getUsername().equals(username)){
                return type.indexOf(users) + 1;
            }
        }
        return 0;
    }

    /** check whether the password is correct
     @param index >= 0.
     @return true if password is correct, false otherwise.
     */

    public boolean loginCheck(int index, String type, String passcode){
        int types = typeChoice(type);
        if(typeArray[types].get(index - 1).getPassword().equals(passcode)) {
            userOnAir = typeArray[types].get(index - 1);
            return true;
        }
        return false;
    }

    public boolean contactable(String username){
        HashMap<String, String> contacts = userOnAir.getChatroom();
        return contacts.containsKey(username);
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
            if (start.isAfter(actinterv[0]) && start.isBefore(actinterv[1])){
                return false;
            }
            if (end.isAfter(actinterv[0]) && end.isBefore(actinterv[1])){
                return false;
            }
        }
        return true;
    }

    public void addSchedule(Activity act){
        LocalDateTime[] time = timeProcessing(act);
        userOnAir.getActivities().put(time, act.getIdentity().toString());
    }

    public boolean addChatroom(Chatroom privateRoom){
        if (privateRoom.getUsersInvolved().size() > 2){
            return false;
        }
        for (String name: privateRoom.getUsersInvolved()){
            if (!name.equals(userOnAir.getUsername())){
                userOnAir.getChatroom().put(name,
                        privateRoom.getId().toString());
            }
        }
        return true;
    }

    public boolean addChatroom(Activity act){
        userOnAir.getChatroom().put(act.getIdentity().toString(),
                act.getChatID().toString());
        return true;
    }

    private LocalDateTime[] timeProcessing(Activity act){
        LocalDateTime[] time = new LocalDateTime[2];
        time[0] = act.getStartTime();;
        time[1] = act.getEndTime();
        return time;
    }

    public boolean deleteActivity(Activity act){
        LocalDateTime[] time = timeProcessing(act);
        if (userOnAir.getActivities().containsKey(time)){
            userOnAir.getActivities().remove(time);
            return true;
        }
        return false;
    }

    public boolean deleteChat(Activity act){
        String roomID = act.getChatID().toString();
        if (userOnAir.getChatroom().containsKey(roomID)){
            userOnAir.getChatroom().remove(roomID);
            return true;
        }
        return false;
    }

}