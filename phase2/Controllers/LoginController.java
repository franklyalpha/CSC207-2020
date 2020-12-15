package Controllers;

import gateways.GatewaySerialize;
import globallyAccessible.InvalidUserTypeException;
import globallyAccessible.UserNotFoundException;
import globallyAccessible.UserType;
import useCases.UserFactory;
import useCases.UserManager;

import static globallyAccessible.UserType.*;


/**
 *  this is the very beginning controller, for user login and sign up, and
 *  distribute the correct user-controller to corresponding user type.
 *  When a user logs out, will return to this controller.
 *
 *  functions include:
 *  run: the main method for printing out instructions to guide user for login and sign up.
 *
 */
public class LoginController {
    /**
     * an instance of UserManager being instantiated and used for checking
     *                   login and sign up.
     */
    private final UserManager userManager = new GatewaySerialize().deserializeUser();


    private UserType checkLoginCondition(String username, String password) throws UserNotFoundException {
        return userManager.loginCheck(username, password);
    }

    /**
     * return the appropriate <>UserType</> given <>Integer</> as input.
     * @param type the <>Integer</> corresponding to a certain <>UserType</>.
     * @return the corresponding <>UserType</> associated with the <>Integer</>.
     * @throws InvalidUserTypeException is thrown when inputted <>Integer</> is not associate with any <>UserType</>.
     */
    private UserType UserTypeDistributor(int type) throws InvalidUserTypeException {
        switch (type) {
            case 0 : {
                return UserType.ORGANIZER;
            }
            case 1 : {
                return UserType.ATTENDEE;
            }
            case 2 : {
                return UserType.ADMINISTRATOR;
            }
            default : throw new InvalidUserTypeException("No such user type!!!");
        }
    }

    /**
     * handle the login system for user to log in.
     * @param userName the username of potential <>User</> to log in to the system.
     * @param password the password entered to login.
     * @return An instance of <>Object</> array containing <>UserType</> of <>User</> who has logged in, and an instance of
     * <>UserController</>.
     * @throws UserNotFoundException: is thrown when login fails.
     */
    public Object[] handleLogin(String userName, String password) throws UserNotFoundException{
        UserType loginCondition = checkLoginCondition(userName, password);
        return userControlDistributor(loginCondition);
    }

    private Object[] userControlDistributor(UserType loginCondition){
        UserController userController = new UserController(userManager);
        switch (loginCondition) {
            case ORGANIZER: {
                return new Object[]{ORGANIZER, userController};
            }
            case SPEAKER: {
                return new Object[]{SPEAKER, userController};
            }
            case ATTENDEE: {
                return new Object[]{ATTENDEE, userController};
            }
            case ADMINISTRATOR:{
                return new Object[]{ADMINISTRATOR, userController};
            }
            default: return new Object[]{};
        }
    }


    /**
     * Responsible for updates of info for the system regarding new <>User</> registration.
     * @param username the username of new potential <>User</>.
     * @param password the password of new potential <>User</>.
     * @param type the type of new potential <>User</>.
     * @return a <>String</> of username of new <>User</> the system assigns.
     * @throws InvalidUserTypeException is thrown when the <>UserType</> is not the one in the system.
     */
    public String handleCreateNewUser(String username, String password, int type) throws InvalidUserTypeException{
        UserType userType = UserTypeDistributor(type);
        String name = new UserFactory(userManager).construct(username, password, userType);
        new GatewaySerialize().serializeUser(userManager);
        return name;
    }

}
