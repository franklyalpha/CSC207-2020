package useCases;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entities.User;
import globallyAccessible.InvalidUserTypeException;
import globallyAccessible.UserNotFoundException;
import globallyAccessible.UserType;
import org.bson.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginStatusManager implements Serializable {
    /**
     * A list contains all Users with all types <code>User</code>
     * */
    protected HashMap<UserType, ArrayList<User>> allUsers;

    /**
     * A integer representing the number of users who are currently using the system.
     * */
    protected int num_user;

    /**
     *
     * */
    protected User userOnAir;
//    private ArrayList<entities.User>[] typearray = [organi]


    public LoginStatusManager(){
        allUsers = new HashMap<>();
        num_user = 1;
        userOnAir = null;
    }

    /**
     * Creates a <code>assUser</code> and add Users to the corresponding TypeList if the Users are not inside and
     * update the num_user.
     * @param users is the user that needs to be added in the list.
     * @param type is the UserType for the user that needs to be added in the list.
     */
    public void addUser(User users, UserType type){
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

    /**
     * Creates a <code>findUser</code> and get the user.
     * @param userName is the name of the other user that we want to find.
     * @return the user that we want to find.
     */
    protected User findUser(String userName){
        for (User users: getAllUsers()){
            if (users.getUsername().equals(userName)){
                return users;
            }
        }
        return null;
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
        throw new UserNotFoundException("Wrong login info.");
    }

    /**
     * Creates a <code>currentUsername</code> and get the users' current Username.
     * @return current UserName of the user.
     */
    public String currentUsername(){
        return userOnAir.getUsername();
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

}
