package entities;

import globallyAccessible.UserType;

import java.io.Serializable;

public class Administrator extends User implements Serializable {
    /**
     * Creates a new entities.User with given name, password, and type.
     *
     * @param name     the valid name of this user, which help to identify.
     * @param password the valid password of this user.
     * @param type     the type of this user: Attendee, Organizer, Speaker.
     */
    public Administrator(String name, String password, UserType type) {
        super(name, password, type);
    }
}
