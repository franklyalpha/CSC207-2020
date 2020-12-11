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
        return "Please specify which request you wish to " + action + ": \n ";
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
            put('Q', " - Go back");
        }};
        return strItemizeMenuOption("do", items);
    }

    public String strOrgBeginRequestMenu(){
        HashMap<Character, String> items = new HashMap<Character, String>() {{
            put('0', " - View pending requests");
            put('1', " - View all requests");
            put('2', " - Handle a request");
            put('Q', " - Go back");
        }};
        return strItemizeMenuOption("do", items);
    }

    public String strInvalidRequest(){
        return strInvalidHelper("request");
    }

    public String strRequestHeader(){return "Here are your requests: ";}

    public String strQuitPrompt(){return "[Q] - Go back";}

    public String strRequestRemovePrompt(){return "Which request would you like to remove? (Please enter the corresponding number):";}

    /**
     * Helper method to format object array into readable text.
     * @param req1 Instance of the <code>Object[]</code> to be formatted.
     * @return String representing the <code>Object[]</code>.
     */
    public String strRequestObjArrToStr(Object req1[]){
        return  "\n------------------------------------------------------\nSubject: " + req1[2] + "\nStatus: [ " + req1[3]
            + " ]\n------------------------------------------------------";
    }

    public String strModifyMenu(){
        return "What part would you like to modify? (Please enter the corresponding number):\n[0] " +
                "- Subject\n[1] - Description\n[Q] - Back";
    }

    public String strNoPendingRequests(){return "There are currently no pending requests";}

    public String strAllPendingRequestsHeader(){return "Here are all pending requests: ";}

    public String strNoRequestSubmitted(){return "No requests have been submitted yet.";}

    public String strAllRequestSubmittedHeader(){return "Here are all submitted requests: ";}
}
