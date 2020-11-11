package useCases;

import entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.time.*;

//public class use_cases.UserManager implements java.io.Serializable

public class UserManager implements java.io.Serializable{
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
    private ArrayList<User>[] typeArray = new ArrayList[3];
//    private ArrayList<entities.User>[] typearray = [organi]

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

    public int typeChoice(String usertype){
        return switch (usertype) {
            case "organizer" -> 0;
            case "speaker" -> 1;
            case "attendee" -> 2;
            default -> -1;
        };
    }

    public void createUser(String username, String password, String type) {
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
        if (types == -1){
            return 0;
        }
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
        HashMap<String, UUID> contacts = userOnAir.getChatroom();
        return contacts.containsKey(username);
    }

    public HashMap<String, UUID> contacts(){
        return userOnAir.getChatroom();
    }

    public HashMap<LocalDateTime[], UUID> schedules(){
        return userOnAir.getActivities();
    }

    public String currentUsername(){
        return userOnAir.getUsername();
    }

    public boolean isFree(LocalDateTime[] actinterv){
        HashMap<LocalDateTime[], UUID> userSchedule = userOnAir.getActivities();
        for(LocalDateTime[] interv: userSchedule.keySet()){
            LocalDateTime start = interv[0];
            LocalDateTime end = interv[1];
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

    private boolean isFree(User speaker, LocalDateTime[] actinterv){
        HashMap<LocalDateTime[], UUID> userSchedule = speaker.getActivities();
        for(LocalDateTime[] interv: userSchedule.keySet()){
            LocalDateTime start = interv[0];
            LocalDateTime end = interv[1];
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

    public void selfAddSchedule(LocalDateTime[] time, UUID actID){ ;
        userOnAir.getActivities().put(time, actID);
    }

    public void otherAddSchedule(String username, LocalDateTime[] time, UUID actID){
        User targetUser =findUser(username);
        assert targetUser != null;
        targetUser.getActivities().put(time, actID);
    }

    public void selfAddChatroom(String userName, UUID chatID){
        userOnAir.getChatroom().put(userName, chatID);
    }

    public void otherAddChatroom(String userName, UUID chatID){
        User targetedUser = findUser(userName);
        //require further modification;
        assert targetedUser != null;
        targetedUser.getChatroom().put(userOnAir.getUsername(), chatID);
    }

    private User findUser(String userName){
        ArrayList<User> allUser = new ArrayList<User>(organizers);
        allUser.addAll(speakers);
        allUser.addAll(attendee);
        for (User users: allUser){
            if (users.getUsername().equals(userName)){
                return users;
            }
        }
        return null;
    }

    public boolean deleteActivity(LocalDateTime[] time){
        if (userOnAir.getActivities().containsKey(time)){
            userOnAir.getActivities().remove(time);
            return true;
        }
        return false;
    }

    public HashMap<LocalDateTime[], UUID> getActivities() {
        return userOnAir.getActivities();
    }

    public ArrayList<String> availableSpeakers(LocalDateTime[] targetTime){
        ArrayList<String> freeSpeaker = new ArrayList<String>();
        for (User speaker : speakers){
            if (isFree(speaker, targetTime)){
                freeSpeaker.add(speaker.getUsername());
            }
        }
        return freeSpeaker;
    }

    public void setpassword(String newpassword){userOnAir.setPassword(newpassword);}

}