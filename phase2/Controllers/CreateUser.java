package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;

public interface CreateUser {
    void ValidateName(String name) throws SpeakerAlreadyExistException;
    String createUser(String name, String password);
}
