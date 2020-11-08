public class LoginController {
    private UserManager usermanage = new UserManager();
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

    public void run(){
        // will call file reading methods here, if not implementing serializable
        // will call serialized file reading, if being serialized


    }
}
