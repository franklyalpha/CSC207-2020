package menuPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Represents a GeneralMenuPresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class GeneralMenuPresenter {

    public GeneralMenuPresenter(){}

    /**
     * @param obj the thing that is invalid
     * @return the String proclaiming that obj is invalid
     */
    String strInvalidHelper(String obj){
        return "Invalid " + obj + "!!!";
    }

    public String strPleaseTryAgain(){
        return " Please try again!";
    }

    /**
     * Used to itemize a Hashmap into menu.
     * The Character is the "index" and itemsList is the option
     * @param topic topic for the prompt
     * @param itemsList Hashmap of the options
     * @return the Menu in String form
     */
    String strItemizeMenuOption(String topic, HashMap<Character, String> itemsList){
        StringBuilder finalList = new StringBuilder("What would you like to " + topic + "?\n");
        for(Character item: itemsList.keySet()){
            finalList.append("[").append(item.toString()).append("]").append(itemsList.get(item)).append("\n");
        }
        return finalList.toString();
    }

    /**
     * @param item the thing that is being prompted
     * @return the Prompt for item in String form
     */
    public String strPromptHelper(String item){
        return "Please enter " + item + ": ";
    }

    public String strPasswordPrompt(){
        return strPromptHelper("the password");
    }

    public String strInvalidInput(){
        return strInvalidHelper("input");
    }

    public String strInvalidUsername(){
        return strInvalidHelper("username");
    }

    public String strInvalidMaxNum(){
        return strInvalidHelper("max number");
    }

    /**
     * @param username the name being confirmed
     * @return the confirmation in String form
     */
    public String strUsernameConfirmation(String username){
        return "Your username is: " + username + ". You will use this to log in, so remember it!";
    }

    public String strSpeakerExistWarning(){
        return "This speaker already exists.";
    }
}
