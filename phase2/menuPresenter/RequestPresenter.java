package menuPresenter;

import java.util.HashMap;

public class RequestPresenter extends GeneralMenuPresenter {

    public RequestPresenter(){super();}

    public String strRequestSubjectPrompt(){
        return strPromptHelper("the subject of this request. Please try to be concise");
    }

    public String strRequestDetailsPrompt(){
        return strPromptHelper("details regarding this request. Include as much detail as you can so the " +
                "organizers can better accommodate you");
    }

    public String strChooseRequest(String item){
        return strRequestPromptHelper(item);
    }

    public String strInputNewSubject(){
        return strPromptHelper("the new subject of this request. Remember to be concise");
    }

    public String strInputNewDetails(){
        return strPromptHelper("the new description of this request. Be as detailed as possible so the organizers" +
                " can better accommodate you.");
    }

    public String strBeginRequestMenu(){
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
