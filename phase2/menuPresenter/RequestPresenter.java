package menuPresenter;

import java.util.HashMap;

/**
 * Presenter dealing with any prompts or actions with <code>Request</code> interactions.
 */
public class RequestPresenter extends GeneralMenuPresenter {

    /**
     * Constructor for <code>RequestPresenter</code> class.
     */
    public RequestPresenter(){super();}

    /**
     * Prompts user to input a concise subject for the request.
     * @return String prompting the user to input a subject.
     */
    public String strRequestSubjectPrompt(){
        return strPromptHelper("the subject of this request. Please try to be concise");
    }

    /**
     * Prompts user to input a detailed description for the request.
     * @return String prompting the user to input a description.
     */
    public String strRequestDetailsPrompt(){
        return strPromptHelper("details regarding this request. Include as much detail as you can so the " +
                "organizers can better accommodate you");
    }

    /**
     * Prompts user to choose which request they wish to have an action performed on.
     * @param action String representing action to be done to the request.
     * @return String prompting the user to input which request they want to act on.
     */
    public String strRequestPromptHelper(String action){
        return "Please specify which request you wish to " + action + ": ";
    }

    /**
     * Prompts user to input a concise replacement subject.
     * @return String prompting the user to input a replacement subject.
     */
    public String strInputNewSubject(){
        return strPromptHelper("the new subject of this request. Remember to be concise");
    }

    /**
     * Prompts user to input a detailed replacement subject.
     * @return String prompting the user to input a replacement description.
     */
    public String strInputNewDetails(){
        return strPromptHelper("the new description of this request. Be as detailed as possible so the organizers" +
                " can better accommodate you.");
    }

    /**
     * Presenter for menu options available when interacting with requests.
     * @return String in the form of a text menu of available options and their corresponding numbers.
     */
    public String strBeginRequestMenu(){
        HashMap<Character, String> items = new HashMap<Character, String>() {{
            put('0', " - Submit a request");
            put('1', " - View my requests");
            put('2', " - Modify an existing request");
            put('3', " - Remove request");
            put('Q', " - Quit");
        }};
        return strItemizeMenuOption("do", items);
    }

    public String strOrgBeginRequestMenu(){
        HashMap<Character, String> items = new HashMap<Character, String>() {{
            put('0', " - View pending requests");
            put('1', " - View all requests");
            put('2', " - Handle a request");
            put('Q', " - Quit");
        }};
        return strItemizeMenuOption("sign up as", items);
    }

    public String strInvalidLogin(){
        return super.strInvalidHelper("username / password combination.");
    }


}
