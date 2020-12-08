package Controllers;

import globallyAccessible.UserAlreadyExistException;

public interface ICreateUser {
    void ValidateName(String name) throws UserAlreadyExistException;
    String createUser(String name, String password);
}
