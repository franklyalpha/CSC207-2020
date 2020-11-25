package UserControllers;

import globalConstants.UserNotFoundException;
import globalConstants.UserType;
import useCases.UserManager;
import gateways.*;

import java.util.Scanner;
import presenter.*;


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
    public void run(){
        boolean notStop = true;
        while(notStop){
            // will call file reading methods here, if not implementing serializable
            // will call serialized file reading, if being serialized
            Scanner singUPORLogin = new Scanner(System.in);
            //System.out.println("Enter your choice:\n[0] SignUp\n[1] Login\n[Q] uit");
            Presenter.printLoginMenu();
            String choice = singUPORLogin.nextLine();
            switch (choice){
                case "0":
                    handleSignUp();
                    break;
                case "1":
                    handleLogin();
                    break;
                case "Q":
                case "q":
                    return;
                default:
                    Presenter.printInvalid("input");
                    break;
            }
            notStop = handleWrongInput();
        }
    }

    /**
     * Check whether the user account is correct to login in this <code>LoginController</code>.
     * @param username the name of user currently logging in.
     * @param password the password user has input to login.
     * @return A strings corresponding to type of user if password is correct, "invalid" otherwise.
     */
    private UserType checkLoginCondition(String username, String password){
        try{
            return userManager.loginCheck(username, password);
        }catch(UserNotFoundException e){
            Presenter.printInvalid("password or username");
        }
        return null;
    }
    /**
     * Determine whether the input is wrong.
     * @return <CODE>true</CODE> if the input is right:
     * otherwise
     */
    private boolean handleWrongInput() {
        boolean notStop = false;
        boolean validInput = false;
        while(!validInput){
            Presenter.printWrongInputMenu();
            Scanner nextChoice = new Scanner(System.in);
            String choice = nextChoice.nextLine();
            if (choice.equals("Y") || choice.equals("Yes") || choice.equals("y") || choice.equals("yes")){
                notStop = true;
                validInput = true;
            }
            else if (choice.equals("N") || choice.equals("No") || choice.equals("n") || choice.equals("no")){
                validInput = true;
            }
            else{
                Presenter.printInvalid("input");
            }
        }
        return notStop;
    }

    /**
     * Handle the sign up system for users to sign up.
     * Information include: usertype want to signup, name, and password.
     * Note that username is different from the name you put in.
     * Specific format will be determined in Presenter.
     */
    private void handleSignUp() {
        Scanner signUpScanner = new Scanner(System.in);
        Presenter.printSighUpMenu();
        if(! signUpScanner.hasNextInt()){
            Presenter.printInvalid("input");
            return;
        }
        int type = signUpScanner.nextInt();
        signUpDistributor(type);
        GatewayUser saver = new GatewayUser();
        saver.ser(userManager);
    }

    private void signUpDistributor(int type) {
        switch (type) {
            case 0: {
                handleCreateNewUser(UserType.ORGANIZER);
                Presenter.printNewUserCreated("Organizer");
                break;
            }
            case 1: {
                handleCreateNewUser(UserType.ATTENDEE);
                Presenter.printNewUserCreated("Attendee");
                break;
            }
            default: Presenter.printInvalid("input");
        }
    }

    /**
     * handle the login system for user to log in.
     * Information include: username to login, and password to log in.
     * Note that username is different from the name you put in.
     * Specific format will be determined in Presenter.
     */
    private void handleLogin(){
        Scanner type = new Scanner(System.in);
        Presenter.printUsernamePrompt();
        String userName = type.nextLine();
        Presenter.printPasswordPrompt();
        String password = type.nextLine();
        UserType loginCondition = checkLoginCondition(userName, password);
        if (loginCondition == null){
            return;
        }
        userControlDistributor(loginCondition);
    }

    private void userControlDistributor(UserType loginCondition) {
        switch (loginCondition) {
            case ORGANIZER: {
                OrganizerController org = new OrganizerController(userManager);
                org.run();
                break;
            }
            case SPEAKER: {
                SpeakerController spe = new SpeakerController(userManager);
                spe.run();
                break;
            }
            case ATTENDEE: {
                AttendeeController att = new AttendeeController(userManager);
                att.run();
                break;
            }
            default:
                break;
        }
    }

    /**
     * handle create new user.
     * Information include: username to login, and usertype also created.
     * Note that username is different from the name you put in.
     * Specific format will be determined in Presenter.
     */
    private void handleCreateNewUser(UserType type) {
        Scanner newUser = new Scanner(System.in);
        Presenter.printEnterName();
        String username = newUser.nextLine();
        Presenter.printPasswordPrompt();
        String password = newUser.nextLine();
        Presenter.printUsernameIs(userManager.createUser(username, password, type));
    }

}