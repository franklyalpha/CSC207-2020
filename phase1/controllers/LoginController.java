package controllers;

import useCases.UserManager;
import gateways.*;
import java.util.Scanner;
import presenter.*;

public class LoginController {
    private final UserManager userManager = new GatewayUser().deserialize();

    // will consider instantiating presenters here
    /**
    this is the very beginning controller, for user login, and
     distribute the correct user-controller to corresponding user type.
     When a user logs out, will return to this controller.

     functions should include:
     1: store all instances of presenters and controllers and instantiate when appropriate
     2: will instantiate all use-cases; user-manager will instantiate at beginning, while others
     will only be instantiated when the user has logged in successfully;
     3: will call methods of reading and writing file (if not implement serializable),
     and fill in managers.
     4: will allow users to login. process as follows:
        a. allow user input: usertype, username, passcode;
        b. check with userManager on whether the inputted user name is in the
            usertype arraylist;
        c. check whether the password matches with corresponding password (call method in use-case);
        d. will distribute the user to corresponding userController;
        e. will allow user to log out (need to figure out a way to save file);
     5: will continue running until being terminated (use while loop);
     */


    /*
    require implementation: logout system;
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
                    break;
            }
            notStop = handleWrongInput();
        }
    }


    private boolean checkLoginCondition(String type, String username, String password){
        int userIndex = userManager.isUser(username, type);
        if (userIndex != 0){
            if(userManager.loginCheck(userIndex, type, password)){
                return true;
            }
            //System.out.println("Wrong password!!!");
            Presenter.printInvalid("password");
            return false;
        }
        //System.out.println("Username not found!!!");
        Presenter.printInvalid("username");
        return false;
    }

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

    private void handleSignUp() {
        Scanner signUpScanner = new Scanner(System.in);
        //System.out.println("Enter the usertype you want to sign up: [0] Organizer [1] Attendant");
        Presenter.printSighUpMenu();
        String type = signUpScanner.nextLine();
        switch (type) {
            case "0": {
                handleCreateNewUser(type);
                //System.out.println("New Organizer Created!");
                Presenter.printNewUserCreated("Organizer");
                break;
            }
            case "1": {
                handleCreateNewUser(type);
                //System.out.println("New Attendant Created!");
                Presenter.printNewUserCreated("Attendant");
                break;
            }
            default: Presenter.printInvalid("input"); //System.out.println("Wrong input!!! Try again later. ");
        }
    }

    private void handleLogin(){
        Scanner type = new Scanner(System.in);
        //System.out.println("Please enter your usertype [0] Organizer [1] Speaker [2] Attendant");
        Presenter.printHandleLoginMenu();
        String typeName = type.nextLine();
        //System.out.println("Please enter your username (NOTE: Your username is different from your signup name):");
        Presenter.printUsernamePrompt();
        String userName = type.nextLine();
        //System.out.println("Please enter your password:");
        Presenter.printPasswordPrompt();
        String password = type.nextLine();
        if (userManager.typeChoice(typeName) == -1){
            //System.out.println("Wrong user type!!!\n");
            Presenter.printInvalid("usertype");
        }
        else{
            if (checkLoginCondition(typeName, userName, password)){
                //note that switch can be used here, for implementing
                //factory design pattern (see more on code-smell website)
                switch (typeName) {
                    case "0": {
                        // Organizer
                        OrganizerController org = new OrganizerController(userManager);
                        org.run();
                    }
                    case "1": {
                        // Speaker
                        SpeakerController spe = new SpeakerController(userManager);
                        spe.run();
                    }
                    case "2": {
                        // Attendant
                        AttendantController att = new AttendantController(userManager);
                        att.run();
                    }
                }
            }
            else{
                //System.out.println("Invalid password or Username");
                Presenter.printInvalid("password or username");
            }
        }
    }

    private void handleCreateNewUser(String type) {
        Scanner newUser = new Scanner(System.in);
        //System.out.println("Enter your name:");
        Presenter.printEnterName();
        String username = newUser.nextLine();
        //System.out.println("Enter Password:");
        Presenter.printPasswordPrompt();
        String password = newUser.nextLine();
        //System.out.println("Your username is " + userManager.createUser(username, password, type));
        Presenter.printUsernameIs(userManager.createUser(username, password, type));
    }

}
