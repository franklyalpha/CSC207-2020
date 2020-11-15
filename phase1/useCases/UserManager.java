package useCases;

import entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.time.*;

//public class use_cases.UserManager implements java.io.Serializable

public class UserManager implements java.io.Serializable{
    /**
     * Represents a <code>USerManager</code> that is responsible for any interactions with <code>User</code> entities.
     * its own constructor
     * store different list of users;(will return getter of organizer and speakers)
     * store the user who is currently using the system;
     * being able to instantiate a user and store in proper list;
     * being able to check whether someone is a user (i.e, is in one of the lists);
     * being able to check whether the password is correct(return true)
     * able to check whether a given user/activity is in another user's contact list;
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


    /**
     * A list contains all Users with all types <code>User</code>
     * */
    private ArrayList<User> allUsers;

    /**
     * A integer representing the number of users who are currently using the system.
     * */
    private int num_user;

    /**
     *
     * */
    private User userOnAir;
//    private ArrayList<entities.User>[] typearray = [organi]

    // may consider simple factory design pattern in controller layer;
    // make sure organizers don't access speaker's controllers  !!!!!


    /**
     * Creates a <code>UserManager</code> with blank list of organizers, speakers, attendee.
     */
    public UserManager() {
        allUsers = new ArrayList<>();
        num_user = 1;
        userOnAir = null;
    }

    /**
     * should consider using DEPENDENCY INJECTION PATTERN in phase two, in which
     * an independent account creator class would be implemented, and only the function
     * 'add user would be kept, and being made public'
     */

    public int typeChoice(String usertype){
        return switch (usertype) {
            case "0" -> 0;
            case "1" -> 1;
            case "2" -> 2;
            default -> -1;
        };
    }

    public String createUser(String username, String password, String type) {
        String name = username + num_user;
        User org = new User(name, password, type);
        addUser(org);
        return name;
        // return name: just in case to notify users about their exact username;
    }

    private void addUser(User users){
        allUsers.add(users);
        num_user += 1;
    }

    public int isUser(String username, String type) {
        int return_index = 0;
        int types = typeChoice(type);
        if (types == -1){
            return 0;
        }
        return_index = checkUserIndex(username);
        return return_index;
    }

    private int checkUserIndex(String username){
        for (User users : allUsers){
            if (users.getUsername().equals(username)){
                return allUsers.indexOf(users) + 1;
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
        if(allUsers.get(index - 1).getPassword().equals(passcode)) {
            userOnAir = allUsers.get(index - 1);
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
            if (start.isEqual(actinterv[0]) && end.isEqual(actinterv[1])){
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
            if (start.isEqual(actinterv[0]) && end.isEqual(actinterv[1])){
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
        for (User users: allUsers){
            if (users.getUsername().equals(userName)){
                return users;
            }
        }
        return null;
    }

    public boolean deleteActivity(LocalDateTime[] time){
        Set<LocalDateTime[]> period = userOnAir.getActivities().keySet();
        for (LocalDateTime[] target : period){
            if (target[0].equals(time[0]) && target[1].equals(time[1])){
                userOnAir.getActivities().remove(target);
                return true;
            }
        }
        return false;
    }

    public boolean deleteActivity(String username, LocalDateTime[] time){
        User target = findUser(username);
        assert target != null;
        Set<LocalDateTime[]> period = target.getActivities().keySet();
        for (LocalDateTime[] enrolled : period){
            if (enrolled[0].equals(time[0]) && enrolled[1].equals(time[1])){
                target.getActivities().remove(enrolled);
                return true;
            }
        }
        return false;
    }

    public HashMap<LocalDateTime[], UUID> getActivities() {
        return userOnAir.getActivities();
    }

    public ArrayList<String> availableSpeakers(LocalDateTime[] targetTime){
        ArrayList<String> freeSpeaker = new ArrayList<String>();
        for (User users: allUsers){
            if (isFree(users, targetTime) && users.getUserType().equals("speaker")){
                freeSpeaker.add(users.getUsername());
            }
        }
        return freeSpeaker;
    }

    public void setpassword(String newpassword){userOnAir.setPassword(newpassword);}

    public void logout(){
        userOnAir = null;
    }

    public ArrayList<String> allAttendee(){
        ArrayList<String> attendees = new ArrayList<String>();
        for (User users : allUsers){
            if (users.getUserType().equals("attendant")){
                attendees.add(users.getUsername());
            }
        }
        return attendees;
    }

}