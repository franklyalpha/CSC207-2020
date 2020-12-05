package useCases;

import entities.*;
import globallyAccessible.InvalidUserTypeException;
import globallyAccessible.UserType;

public class UserFactory {
    private UserManager userManager;

    public UserFactory(UserManager userManager){
        this.userManager = userManager;
    }

    public void construct(String username, String password, UserType userType){
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
    }
}
