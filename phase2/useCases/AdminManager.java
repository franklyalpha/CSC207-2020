package useCases;

import entities.User;

/**
 * Represents a <code>AdminManager</code> that is responsible for what the admin can do.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class AdminManager extends UserManager {

    /**
     * Creates an <code>AdminManager</code>
     * @param userManager used to create this manager
     */
    public AdminManager(UserManager userManager){
        super(userManager);
    }

    /**
     * Delete the contact to another user from a user
     * @param deleter the String representing the person that is doing the deletion
     * @param deleted the String representing the person getting deleted
     */
    public void deleteContact(String deleter, String deleted){
        User targetedUser = findUser(deleter);
        assert targetedUser != null;
        targetedUser.getMessageRoom().remove(deleted);
    }
}
