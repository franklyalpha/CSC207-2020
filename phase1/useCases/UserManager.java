package useCases;

import entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.time.*;

import globalConstants.UserNotFoundException;
import globalConstants.UserType;

//public class use_cases.UserManager implements java.io.Serializable

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
public class UserManager implements java.io.Serializable{

//    /**
//     * will consider adding another type-indicator String[], stores all possible type names.
//     * therefore modify below all strings of types as: TypeString[0], TypeString[1],TypeString[2].
//     */
    // call modification and reset


    /**
     * A list contains all Users with all types <code>User</code>
     * */
    private HashMap<UserType, ArrayList<User>> allUsers;

    /**
     * A integer representing the number of users who are currently using the system.
     * */
    private int num_user;

    /**
     *
     * */
    private User userOnAir;
//    private ArrayList<entities.User>[] typearray = [organi]


    /**
     * Creates a <code>UserManager</code> with blank list of organizers, speakers, attendee.
     */
    public UserManager() {
        allUsers = new HashMap<>();
        num_user = 1;
        userOnAir = null;
        for (UserType type : UserType.values()){
            allUsers.put(type, new ArrayList<>());
        }
    }

    /**
     * should consider using DEPENDENCY INJECTION PATTERN in phase two, in which
     * an independent account creator class would be implemented, and only the function
     * 'add user would be kept, and being made public'
     *
     *
     * Creates a <code>createUser</code> and add it to the list of Users of the <code>UserManager</code>
     * @param username is the username for the newly constructed <code>User</code>.
     * @param password is the password for the newly constructed <code>User</code>.
     * @param type is the UserType for the newly constructed <code>User</code>.
     * @return the name of the newly constructed <code>User</code>.
     */
    public String createUser(String username, String password, UserType type) {
        String name = username + num_user;
        UserType t;
        switch (type){
            case ORGANIZER:
                t = UserType.ORGANIZER;
                break;
            case ATTENDEE:
                t = UserType.ATTENDEE;
                break;
            case SPEAKER:
                // This default should not be accessed, if accessed check
                t = UserType.SPEAKER;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        User org = new User(name, password, t);
        addUser(org, type);
        return name;
        // return name: just in case to notify users about their exact username;
    }

    /**
     * Creates a <code>assUser</code> and add Users to the corresponding TypeList if the Users are not inside and
     * update the num_user.
     * @param users is the user that needs to be added in the list.
     * @param type is the UserType for the user that needs to be added in the list.
     */
    private void addUser(User users, UserType type){
        if (!allUsers.containsKey(type)){
            allUsers.put(type, new ArrayList<>());
        }

        allUsers.get(type).add(users);
        num_user += 1;
    }

    /**
     * Creates a <code>isUser</code> and check the index of the User, if not all Users contain key, return 0.
     * @param username is the username of user that needs to be checked.
     * @return the index of the User, if not all Users contain key, return 0.
     */
    public int isUser(String username) {
        if (findUser(username) == null){
            return 0;
        }
        return 1;
    }


//    public int isUser(String username){
//        for (User users : allUsers){
//            if(users.getUsername().equals(username)){
//                return allUsers.indexOf(users) + 1;
//            }
//        }
//        return 0;
//    }

    /**
     * Creates a <code>checkUserIndex</code> and return the index of User.
     * @param username is the username of user that needs to be checked.
     * @param type is the UserType for the user that needs to be checked.
     * @return the index of the User, if not all Users contain key, return 0.
     */
    private int checkUserIndex(String username, UserType type){
        for (User users : allUsers.get(type)){
            if (users.getUsername().equals(username)){
                return allUsers.get(type).indexOf(users) + 1;
            }
        }
        return 0;
    }

    /** check whether the password is correct
     @return type of user if password is correct, "invalid" otherwise.
      * @param username the name of user currently logging in.
     * @param passcode the password user has input to login.
     */
    public UserType loginCheck(String username, String passcode) throws UserNotFoundException {
        ArrayList<User> allExistingUser = getAllUsers();
        for (User currUser : allExistingUser) {
            if (currUser.getUsername().equals(username) &&
                    currUser.getPassword().equals(passcode)) {
                userOnAir = currUser;
                return currUser.getUserType();
            }
        }
        throw new UserNotFoundException("Wrong login info");
    }

//    public String loginCheck(int index, String type, String passcode){
//        //int types = typeChoice(type);
//        User currUser = allUsers.get(type).get(index - 1);
//        if(currUser.getPassword().equals(passcode)) {
//            userOnAir = currUser;
//            return currUser.getUserType();
//        }
//        return "invalid";
//    }

    /**
     * Creates a <code>contactable</code> and check whether the user can be contact.
     * @param username is the username of user that needs to be checked.
     * @return returns 1 if the user can be contact and returns 0 otherwise.
     */
    public boolean contactable(String username){
        HashMap<String, UUID> contacts = userOnAir.getChatroom();
        return contacts.containsKey(username);
    }

    /**
     * Creates a <code>contacts</code> to contact to other user.
     * @return returns the chatroom that can contact other users.
     */
    public HashMap<String, UUID> contacts(){
        return userOnAir.getChatroom();
    }

    /**
     * Creates a <code>schedules</code> and get the users' schedule.
     * @return the schedule that contains the users' all activities.
     */
    public HashMap<LocalDateTime[], UUID> schedules(){
        return userOnAir.getActivities();
    }

    /**
     * Creates a <code>currentUsername</code> and get the users' current Username.
     * @return current UserName of the user.
     */
    public String currentUsername(){
        return userOnAir.getUsername();
    }

    /**
     * Creates a <code>isFree</code> and check whether the user is free during some time.
     * @param actinterv is the time that needed to be checked.
     * @return returns 1 if the user is free during the time and returns 0 otherwise.
     */
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

    /**
     * Creates a <code>getAllUsers</code> and get all Users in all the UserType.
     * @return returns all the users in all the UserTypes.
     */
    private ArrayList<User> getAllUsers(){
        ArrayList<User> allUser = new ArrayList<>();
        for (UserType userType : allUsers.keySet()){
            allUser.addAll(allUsers.get(userType));
        }
        return allUser;

    }

    /**
     * Creates a <code>isFree</code> and check if the speaker is free during the time period.
     * @param speaker is the speaker that needed to be checked.
     * @param actinterv is the time that needed to be checked.
     * @return returns 1 if the speaker is free during the time and returns 0 otherwise.
     */
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

    /**
     * Creates a <code>selfAddSchedule</code> and add the time of the activity to the user's schedule.
     * @param time is the time that needed to be added.
     * @param actID is the ID of the user.
     */
    public void selfAddSchedule(LocalDateTime[] time, UUID actID){ ;
        userOnAir.getActivities().put(time, actID);
    }

    /**
     * Creates a <code>otherAddSchedule</code> and add the time of the activity to another user's schedule.
     * @param username is the name of the other user that needed to add.
     * @param time is the time that needed to be added.
     * @param actID is the ID of the user.
     */
    public void otherAddSchedule(String username, LocalDateTime[] time, UUID actID){
        User targetUser =findUser(username);
        assert targetUser != null;
        targetUser.getActivities().put(time, actID);
    }

    /**
     * Creates a <code>selfAddChatroom</code> and add the user in the chatroom.
     * @param userName is the name of the user that needed to be added.
     * @param chatID is the ID of the chat.
     */
    public void selfAddChatroom(String userName, UUID chatID){
        userOnAir.getChatroom().put(userName, chatID);
    }

    /**
     * Creates a <code>otherAddChatroom</code> and add another user to the chatroom.
     * @param userName is the name of the other user that needed to add.
     * @param chatID is the ID of the chat.
     */
    public void otherAddChatroom(String userName, UUID chatID){
        User targetedUser = findUser(userName);
        //require further modification;
        assert targetedUser != null;
        targetedUser.getChatroom().put(userOnAir.getUsername(), chatID);
    }

    /**
     * Creates a <code>findUser</code> and get the user.
     * @param userName is the name of the other user that we want to find.
     * @return the user that we want to find.
     */
    private User findUser(String userName){
        for (User users: getAllUsers()){
            if (users.getUsername().equals(userName)){
                return users;
            }
        }
        return null;
    }

    /**
     * Creates a <code>deleteActivity</code> and delete the activity.
     * @param time is the time of the activity that needed to be delete.
     */
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

    /**
     * Creates a <code>deleteActivity</code> and delete the activity for the user.
     * @param username is the name of user that we want to operate.
     * @param time is the time of the activity that needed to be delete.
     */
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

    /**
     * Creates a <code>getActivities</code> and get the activity that user have added.
     * @return returns the activity that user have added.
     */
    public HashMap<LocalDateTime[], UUID> getActivities() {
        return userOnAir.getActivities();
    }

    /**
     * Creates a <code>availableSpeakers</code> and get the speakers that are available during the time period.
     * @param targetTime is the time period that we want to check.
     * @return returns the ArrayList that contains all the speakers that are available.
     */
    public ArrayList<String> availableSpeakers(LocalDateTime[] targetTime){
        ArrayList<String> freeSpeaker = new ArrayList<String>();
        for (User users: allUsers.get(UserType.SPEAKER)){
            if (isFree(users, targetTime)){
                freeSpeaker.add(users.getUsername());
            }
        }
        return freeSpeaker;
    }
    /**
     * Creates a <code>setpassword</code> and set the password of the user's account.
     * @param newpassword is the new password the user want to set.
     */
    public void setpassword(String newpassword){userOnAir.setPassword(newpassword);}

    /**
     * Creates a <code>logout</code> and set the status of user's account to be logout.
     */
    public void logout(){
        userOnAir = null;
    }

    /**
     * Creates a <code>allAttendee</code> and get all the attendees.
     * @return the ArrayList that contains all the attendees.
     */
    public ArrayList<String> allAttendee(){
        ArrayList<String> attendees = new ArrayList<String>();
        for (User users : allUsers.get(UserType.ATTENDEE)){
            attendees.add(users.getUsername());
        }
        return attendees;
    }

    public void addNewUserType(){

    }

}