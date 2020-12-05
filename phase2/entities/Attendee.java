package entities;

import globallyAccessible.UserType;

import java.io.Serializable;

public class Attendee extends User implements Serializable {
    /**
     * Creates a new entities.User with given name, password, and type.
     *
     * @param name     the valid name of this user, which help to identify.
     * @param password the valid password of this user.
     */
    public Attendee(String name, String password) {
        super(name, password, UserType.ATTENDEE);
    }
}