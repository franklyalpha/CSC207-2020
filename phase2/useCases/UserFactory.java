package useCases;

import entities.*;
import globallyAccessible.UserType;

/**
 * Represents a <code>UserFactory</code> that is responsible for creating and sorting users
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class UserFactory {
    private UserManager userManager;

    /**
     * Creating the UserFactory
     * @param userManager the manager this is based on
     */
    public UserFactory(UserManager userManager){
        this.userManager = userManager;
    }

    /**
     * Constructing the user and storing it in the userManager
     * @param username username of the user
     * @param password password of the user
     * @param userType type of the user
     * @return the username
     */
    public String construct(String username, String password, UserType userType){
        int numUser = userManager.getNumUsers();
        username = username + numUser;
        switch (userType) {
            case ATTENDEE:
                Attendee newAtt = new Attendee(username, password);
                userManager.addUser(newAtt, UserType.ATTENDEE);
                break;
            case ORGANIZER:
                Organizer newOrg = new Organizer(username, password);
                userManager.addUser(newOrg, UserType.ORGANIZER);
                break;
            case SPEAKER:
                Speaker newSpe = new Speaker(username, password);
                userManager.addUser(newSpe, UserType.SPEAKER);
                break;
            case ADMINISTRATOR:
                Administrator newAdmin = new Administrator(username, password);
                userManager.addUser(newAdmin, UserType.ADMINISTRATOR);
                break;
        }
        return username;
    }
}
