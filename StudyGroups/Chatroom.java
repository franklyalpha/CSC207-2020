import java.util.ArrayList;

public class Chatroom {

    static int count = 0;  // used to update and assign room IDs

    private int id;  // unique identifier for each Chatroom
    private ArrayList<String> usersInvolved;  // holds username of all Users in this Chatroom
    private ArrayList<String> historicalChats;  // holds previously sent messages from this Chatroom

    public Chatroom(ArrayList<String> users){
        id = count;  // assign id
        count += 1;   // update static variable so next Chatroom to be created has different ID
        usersInvolved = users;  // specify users in this Chatroom with given parameter
        historicalChats = new ArrayList<String>();  // start with empty chatHistory
    }


}



