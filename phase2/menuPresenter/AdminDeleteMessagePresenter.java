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
        return "Please enter all the index of the messages to be deleted (separatedpre by space):";
    }

    public String strChatToDeletePrompt(){
        return "Please enter the ID of the chat you want message cleared:";
    }

    public String strChatDisplayHeader(){
        return "Below are conversation IDs paired with corresponding chats:";
    }
}
