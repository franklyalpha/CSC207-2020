package UI;

import Controllers.LoginController;
import Controllers.UserController;
import globallyAccessible.UserNotFoundException;
import globallyAccessible.UserType;
import menuPresenter.LoginPresenter;

import java.util.Scanner;


/**
 *  this is the very beginning controller, for user login and sign up, and
 *  distribute the correct user-controller to corresponding user type.
 *  When a user logs out, will return to this controller.
 *
 *  functions include:
 *  run: the main method for printing out instructions to guide user for login and sign up.
 *
 */
public class LoginUI {

    final private LoginController loginController = new LoginController();
    private LoginPresenter loginPresenter = new LoginPresenter();

    public void run(){
        boolean notStop = true;
        while(notStop){
            Scanner singUPORLogin = new Scanner(System.in);
            System.out.println(loginPresenter.strLoginMenu());
            String choice = singUPORLogin.nextLine();
            switch (choice){
                case "0":
                    handleSignUp();
                    break;
                case "1":
                    handleLogIn();
                    break;
                case "Q":
                case "q":
                    return;
                default:
                    System.out.println(loginPresenter.strInvalidInput());
                    break;
            }
            notStop = handleWrongInput();
        }
    }

    private void handleSignUp(){
        //for all presenter words, should be placed in one sign-up presenter
        Scanner signUpScanner = new Scanner(System.in);
        System.out.println(loginPresenter.strSighUpMenu());
        if(! signUpScanner.hasNextInt()){
            System.out.println(loginPresenter.strInvalidInput());
            return;
        }
        int type = signUpScanner.nextInt();
        signUpScanner.nextLine();
        System.out.println(loginPresenter.strNamePrompt());

        // should consider combine above two presenter method
        String username = signUpScanner.nextLine();
        System.out.println(loginPresenter.strPasswordPrompt());
        String password = signUpScanner.nextLine();
        String newName = loginController.handleCreateNewUser(username, password, type);
        System.out.println(loginPresenter.strUsernameConfirmation(newName));
    }

    private void handleLogIn(){
        for (int i = 0; i < 3; i++) {
            try {
                Scanner type = new Scanner(System.in);
                System.out.println(loginPresenter.strUsernamePrompt());
                //should consider combining above two as one presenter method
                String userName = type.nextLine();
                System.out.println(loginPresenter.strPasswordPrompt());
                String password = type.nextLine();
                Object[] result = loginController.handleLogin(userName, password);
                runUserUIs(result);
                break;
            } catch (UserNotFoundException e) {
                System.out.println(loginPresenter.strInvalidLogin());
            }
        }
    }

    private void runUserUIs(Object[] result){
        UserController userController = (UserController) result[1];
        switch((UserType) result[0]){
            case ORGANIZER:
                OrganizerUI2 orgUI = new OrganizerUI2(userController);
                orgUI.run();
                break;
            case SPEAKER:
                SpeakerUI speUI = new SpeakerUI(userController);
                speUI.run();
                break;
            case ATTENDEE:
                AttendeeUI attUI = new AttendeeUI(userController);
                attUI.run();
                break;
        }
    }

    private boolean handleWrongInput() {
        boolean notStop = false;
        boolean validInput = false;
        while(!validInput){
            System.out.println(loginPresenter.strWrongInputMenu());
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
                System.out.println(loginPresenter.strInvalidInput());
            }
        }
        return notStop;
    }
}