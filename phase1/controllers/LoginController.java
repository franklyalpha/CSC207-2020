package controllers;

import useCases.UserManager;
import gateways.*;
import java.util.Scanner;

public class LoginController {
    private UserManager usermanage = new GatewayUser().deser();

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
        b. check with usernamager on whether the inputted user name is in the
            usertype arraylist;
        c. check whether the password matches with corresponding password (call method in use-case);
        d. will distribute the user to corresponding usercontroller;
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

            Scanner type = new Scanner(System.in);
            System.out.println("please enter your usertype (organizer, speaker, attendant) at first line,\n" +
                    " your username at second line \n and passcode at third line: \n");
            String typeName = type.nextLine();
            String userName = type.nextLine();
            String password = type.nextLine();
            if (usermanage.typeChoice(typeName) == -1){
                System.out.println("Wrong user type!!!\n");
            }
            else{
                if (checkLoginCondition(typeName, userName, password)){
                    //note that switch can be used here, for implementing
                    //factory design pattern (see more on code-smell website)
                    switch(typeName){
                        case "organizer":
                            OrganizerController org = new OrganizerController(usermanage);
                            org.run();
                            break;
                        case "speaker":
                            SpeakerController spe = new SpeakerController(usermanage);
                            spe.run();
                            break;
                        case "attendant":
                            AttendantController att = new AttendantController(usermanage);
                            att.run();
                            break;
                    }
                }
            }
            // following code determine whether the program will continue running;
            // may consider putting into a separate private method;
            boolean validinput = false;
            while(!validinput){
                System.out.println("Continue running or not? (type \"true\" or \"false\")");
                Scanner nextChoice = new Scanner(System.in);
                if (nextChoice.hasNextBoolean()){
                    notStop = nextChoice.nextBoolean();
                    validinput = true;
                }
                else{
                    System.out.println("invalid input! try again; \n");
                }
            }
        }
    }

    private boolean checkLoginCondition(String type, String username, String password){
        int userindex = usermanage.isUser(username, type);
        if (userindex != 0){
            if(usermanage.loginCheck(userindex, type, password)){
                return true;
            }
            System.out.println("Wrong password!!!");
            return false;
        }
        System.out.println("Username not found!!!");
        return false;
    }
}
