package Controllers;

import gateways.GatewayUser;
import globallyAccessible.UserNotFoundException;
import globallyAccessible.UserType;
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
     * userManager: an instance of UserManager being instantiated and used for checking
     *                   login and sign up.
     */
    private final UserManager userManager = new GatewayUser().deserialize();

    /**
     * This method allow people to do actions corresponding to allowed actions.
     * Will print out a list of actions people can implement, ask for choice of action the people
     * want to do and call corresponding method, such as sign up as a user, or log in or just quit.
     */


    /**
     * Check whether the user account is correct to login in this <code>LoginController</code>.
     * @param username the name of user currently logging in.
     * @param password the password user has input to login.
     * @return A strings corresponding to type of user if password is correct, "invalid" otherwise.
     */
    private UserType checkLoginCondition(String username, String password) throws UserNotFoundException {
        return userManager.loginCheck(username, password);
    }
    /**
     * Determine whether the input is wrong.
     * @return <CODE>true</CODE> if the input is right:
     * otherwise
     */

    /**
     * Handle the sign up system for users to sign up.
     * Information include: usertype want to signup, name, and password.
     * Note that username is different from the name you put in.
     * Specific format will be determined in Presenter.
     */

    private UserType UserTypeDistributor(int type) {
        switch (type) {
            case 0: {
                return ORGANIZER;
            }
            case 1: {
                return UserType.ATTENDEE;
            }
            default: return null;
        }
    }

    /**
     * handle the login system for user to log in.
     * Information include: username to login, and password to log in.
     * Note that username is different from the name you put in.
     * Specific format will be determined in Presenter.
     */
    public Object[] handleLogin(String userName, String password) throws UserNotFoundException {
        UserType loginCondition = checkLoginCondition(userName, password);
        return userControlDistributor(loginCondition);
    }

    private Object[] userControlDistributor(UserType loginCondition) {
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
            default:
                return new Object[]{};
        }
    }

    /**
     * handle create new user.
     * Information include: username to login, and usertype also created.
     * Note that username is different from the name you put in.
     * Specific format will be determined in Presenter.
     */
    public String handleCreateNewUser(String username, String password, int type) {
        UserType userType = UserTypeDistributor(type);
        assert userType != null; // CHANGE
        String name = userManager.createUser(username, password, userType);
        new GatewayUser().ser(userManager);
        return name;
    }

}
