package controllers;

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
     * @param userManager: an instance of UserManager being instantiated and used for checking
     *                   login and sign up.
     */
    private final UserManager userManager = new GatewayUser().deserialize();

    /**
     * The method responsible for printing out instructions to guide user to log in or sign up,
     * as well as calling appropriate helper methods to determine whether conditions of login are satisfied.
     * When a user logs out, will return to this controller and determine to continue or to quit program.
     */
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
    private String checkLoginCondition(String username, String password){
        return userManager.loginCheck(username, password);
    }
    /**
     * Determine whether the input is wrong.
     * @return <CODE>true</CODE> if the input is right:
     * otherwise
     */
    private boolean handleWrongInput() {
        // following code determine whether the program will continue running;
        // may consider putting into a separate private method;
        boolean notStop = false;
        boolean validInput = false;
        while(!validInput){
            //System.out.println("Wrong input!");
            //System.out.println("Continue running or not? (type \"(Y)es\" or \"(N)o\")");
            Presenter.printWrongInputMenu();
            Scanner nextChoice = new Scanner(System.in);
            String choice = nextChoice.nextLine();
            if (choice.equals("Y") || choice.equals("Yes")){
                notStop = true;
                validInput = true;
            }
            else if (choice.equals("N") || choice.equals("No")){
                validInput = true;
            }
            else{
                //System.out.println("invalid input! try again; \n");
                Presenter.printInvalid("input");
            }
        }
        return notStop;
    }

    /**
     * handle the sigh up system for user to sign up.
     * Information include: usertype want to signup, name, and password.
     * Note that username is different from the name you put in.
     * Specific format will be determined in Presenter.
     */
    private void handleSignUp() {
        Scanner signUpScanner = new Scanner(System.in);
        //System.out.println("Enter the usertype you want to sign up: [0] Organizer [1] Attendant");
        Presenter.printSighUpMenu();
        if(! signUpScanner.hasNextInt()){
            Presenter.printInvalid("input");
            return;
        }
        int type = signUpScanner.nextInt();
        switch (type) {
            case 0: {
                handleCreateNewUser(type);
                //System.out.println("New Organizer Created!");
                Presenter.printNewUserCreated("Organizer");
                break;
            }
            case 1: {
                handleCreateNewUser(type);
                //System.out.println("New Attendant Created!");
                Presenter.printNewUserCreated("Attendant");
                break;
            }
            default: Presenter.printInvalid("input"); //System.out.println("Wrong input!!! Try again later. ");
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
        //System.out.println("Please enter your usertype [0] Organizer [1] Speaker [2] Attendant");
//        Presenter.printHandleLoginMenu();
//        String typeName = type.nextLine();
        //System.out.println("Please enter your username (NOTE: Your username is different from your signup name):");
        Presenter.printUsernamePrompt();
        String userName = type.nextLine();
        //System.out.println("Please enter your password:");
        Presenter.printPasswordPrompt();
        String password = type.nextLine();
//        if (userManager.typeChoice(typeName) == -1){
//            //System.out.println("Wrong user type!!!\n");
//            Presenter.printInvalid("usertype");
//        }

        String loginCondition = checkLoginCondition(userName, password);

        if (!loginCondition.equals("invalid")){
            //note that switch can be used here, for implementing
            //factory design pattern (see more on code-smell website)
            switch (loginCondition) {
                case "organizer": {
                    // Organizer
                    OrganizerController org = new OrganizerController(userManager);
                    org.run();
                    break;
                }
                case "speaker": {
                    // Speaker
                    SpeakerController spe = new SpeakerController(userManager);
                    spe.run();
                    break;
                }
                case "attendant": {
                    // Attendant
                    AttendantController att = new AttendantController(userManager);
                    att.run();
                    break;
                }
                default:
                    break;
            }
        }
        else{
            //System.out.println("Invalid password or Username");
            Presenter.printInvalid("password or username");
        }
    }

    /**
     * handle create new user.
     * Information include: username to login, and usertype also created.
     * Note that username is different from the name you put in.
     * Specific format will be determined in Presenter.
     */
    private void handleCreateNewUser(int type) {
        String[] typeArray = new String[]{"organizer", "attendant"};
        Scanner newUser = new Scanner(System.in);
        //System.out.println("Enter your name:");
        Presenter.printEnterName();
        String username = newUser.nextLine();
        //System.out.println("Enter Password:");
        Presenter.printPasswordPrompt();
        String password = newUser.nextLine();
        //System.out.println("Your username is " + userManager.createUser(username, password, type));
        Presenter.printUsernameIs(userManager.createUser(username, password, typeArray[type]));
    }

}
