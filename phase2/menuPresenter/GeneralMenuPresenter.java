package menuPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GeneralMenuPresenter {

    public GeneralMenuPresenter(){}

    String strInvalidHelper(String obj){
        return "Invalid " + obj + "!!!";
    }

    String strItemizeMenuOption(String topic, HashMap<Character, String> itemsList){
        StringBuilder finalList = new StringBuilder("What would you like to " + topic + "?\n");
        for(Character item: itemsList.keySet()){
            finalList.append("[" + item.toString() + "]" + itemsList.get(item) + "\n");
        }
        return finalList.toString();
    }

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

    public String strUsernameConfirmation(String username){
        return "Your username is: " + username + ". You will use this to log in, so remember it!";
    }

    public String strSpeakerExistWarning(){
        return "This speaker already exists.";
    }
    //TODO add a warning section (index out of bound, user does not exist, etc.)
}
