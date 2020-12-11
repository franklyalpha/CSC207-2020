package Controllers;

import globallyAccessible.UserAlreadyExistException;

/**
 * An interface responsible for creating new <>User</>.
 */
public interface ICreateUser {
    /**
     * Responsible for checking whether the <>name</> given is already used by another existing <>User</> of this system.
     * @param name the username of potential User awaiting to be checked.
     * @throws UserAlreadyExistException is thrown when the username is already used by another <>User</>.
     */
    void ValidateName(String name) throws UserAlreadyExistException;

    /**
     * Responsible for creating the new <>User</>.
     * @param name the username of new <>User</>.
     * @param password the password of new <>User</>.
     * @return the new username this system assigns.
     */
    String createUser(String name, String password);
}
