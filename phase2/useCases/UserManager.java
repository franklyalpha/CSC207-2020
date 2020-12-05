package useCases;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.time.*;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;


import globallyAccessible.UserNotFoundException;
import globallyAccessible.UserType;

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
public class UserManager extends LoginStatusManager implements java.io.Serializable{

//    /**
//     * will consider adding another type-indicator String[], stores all possible type names.
//     * therefore modify below all strings of types as: TypeString[0], TypeString[1],TypeString[2].
//     */
    // call modification and reset

    /**
     * Creates a <code>UserManager</code> with blank list of organizers, speakers, attendee.
     */
    public UserManager() {
        super();
    }

    public UserManager(UserManager userManager) {
        this.allUsers = userManager.allUsers;
        this.num_user = userManager.num_user;
        this.userOnAir = userManager.userOnAir;
    }

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

    public HashMap<String, UUID> otherContacts(String username){
        User targetedUser = findUser(username);
        assert targetedUser != null;
        return targetedUser.getChatroom();
    }

    /**
     * Creates a <code>schedules</code> and get the users' schedule.
     * @return the schedule that contains the users' all activities.
     */
    public HashMap<LocalDateTime[], UUID> schedules(){
        return userOnAir.getActivities();
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
     * Creates a <code>getActivities</code> and get the activity that user have added.
     * @return returns the activity that user have added.
     */
    public HashMap<LocalDateTime[], UUID> getActivities() {
        return userOnAir.getActivities();
    }
}