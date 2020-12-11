package menuPresenter;

import java.util.HashMap;

/**
 * Represents a LoginPresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class LoginPresenter extends GeneralMenuPresenter {

    public LoginPresenter(){super();}

    public String strNamePrompt(){
        return strPromptHelper("your name");
    }

    public String strUsernamePrompt(){
        return strPromptHelper("your username");
    }

    public String strLoginMenu(){
        HashMap<Character, String> items = new HashMap<Character, String>() {{
            put('0', " Sign Up");
            put('1', " Log in");
            put('Q', " Quit");
        }};
        return strItemizeMenuOption("do", items);
    }

    public String strSighUpMenu(){
        HashMap<Character, String> items = new HashMap<Character, String>() {{
            put('0', "Organizer");
            put('1', "Attendee");
            put('2', "Administrator");
        }};
        return strItemizeMenuOption("sign up as", items);
    }

    public String strInvalidLogin(){
        return super.strInvalidHelper("username / password combination.");
    }

    public String strWrongInputMenu(){
        HashMap<Character, String> items = new HashMap<Character, String>() {{
            put('Y', "Continue");
            put('N', "No");
        }};
        return strItemizeMenuOption("do", items);
    }
}
