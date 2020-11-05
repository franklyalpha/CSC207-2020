import java.util.ArrayList;
import java.util.UUID;

//work in progress

public class ChatroomManager {
    /**
     * Contains instance of Chatroom: coopRoom, including all organizers and speakers;
     * Arraylist<Chatroom> conferenceChats, contains all chatrooms from all activities, each has speakers and attendees;
     * Arraylist<Chatroom> privateChats, list of private Chatrooms between 2 Users
     *
     * Own constructor
     * addUser: takes a list of Users and add them to <chat>
     * (?) modifyChatHistory: takes a chat specified by UUID to access historicalChats and do whatever
     *          ^^ not sure how necessary this is ^^
     *
     */

    private ArrayList<Chatroom> coopRoom = new ArrayList<>();
    private ArrayList<Chatroom> conferenceChats;
    private ArrayList<Chatroom> privateChats;

    public ChatroomManager() {
        coopRoom = new ArrayList<>(); //one ChatroomManager/conference, can consider putting all org/speakers here at start
        conferenceChats = new ArrayList<>();
        privateChats = new ArrayList<>();
    }

    public void createChatroom(ArrayList<String> users){

        //distinguish between coopRoom, conferenceChat, privateChat and construct

    }

    public void addUser(ArrayList<String> users, UUID chat) {

        //work in progress
    }


}
