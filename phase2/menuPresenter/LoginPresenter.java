package menuPresenter;

import java.util.HashMap;

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
            put('1', "Sign Up");
            put('2', "Log in");
            put('Q', "Quit");
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
