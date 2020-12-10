package menuPresenter;

import java.util.HashMap;

public class AdminDeleteMessagePresenter extends UserPresenter {

    public AdminDeleteMessagePresenter(){super();}

    public String strMessageActionMenu(){
        HashMap<Character, String> items = new HashMap<Character, String>() {{
            put('1', "Delete Whole Conversation");
            put('2', "Go to Messages");
        }};
        return strItemizeMenuOption("do next", items);
    }

    public String strUsersToAddPrompt(){
        return "Please enter username of users involved in this PRIVATE chat, one user for each line \n" +
                "(Don't worry if they are not real contacts):";
    }

    public String strGroupActionMenu(){
        HashMap<Character, String> items = new HashMap<Character, String>() {{
            put('0', "Delete Message in Organizer-Speaker Conversation");
            put('1', "Go to Activity-Group Messages");
        }};
        return strItemizeMenuOption("do next", items);
    }

    public String strIndexToDeletePrompt(){
        return "Please enter the indices of all messages to be deleted, each separated by a space (i.e. 1 3 4) and 'end' when finished:";
    }

    public String strChatToDeletePrompt(){
        return "Please enter the ID of the conversation from which you want message(s) cleared:";
    }

    public String strChatDisplayHeader(){
        return "Listed below are conversation IDs paired with the corresponding events' topic:";
    }
}
