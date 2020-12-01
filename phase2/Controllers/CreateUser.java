package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;
import Presenters.Presenter;

import java.util.Scanner;
public interface CreateUser {
    void ValidateName(String name) throws SpeakerAlreadyExistException;
    String createUser(String name, String password);
}
