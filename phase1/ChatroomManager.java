import java.util.ArrayList;
import java.util.UUID;

//work in progress

public class ChatroomManager {
    /**
     * Contains instance of Chatroom: coopRoom, a Chatroom between all organizers and speakers;
     * Arraylist<Chatroom> conferenceChats, a list of all Chatrooms associated with activities happening;
     * Arraylist<Chatroom> privateChats, list of private Chatrooms between 2 Users
     *
     * Own constructor
     * createChatroom: creates a new chatroom with specified list of Users, adds to privateChats if necessary
     * addUser: takes a list of Users and add them to chat
     * removeUser: takes a list of Users and removes them from a chat
     * (?) modifyChatHistory: takes a chat specified by UUID to access historicalChats and do whatever
     *          ^^ not sure how necessary this is ^^
     *
     * sendMessage: takes message string and UUID to specify which chat to put it in.  >>>>>>>>>>>>> How will we keep track of who sent what message?<<<<<<<<<
     *
     */


    private ArrayList<Chatroom> conferenceChats;
    private ArrayList<Chatroom> privateChats;


    public ChatroomManager(ArrayList<String> organizerSpeakers) {
        Chatroom coopRoom = new Chatroom(organizerSpeakers); //initialize instance of coopRoom with all Speakers and Organizers
        conferenceChats = new ArrayList<>();
        privateChats = new ArrayList<>();
    }

    public void createChatroom(ArrayList<String> users){
        // precondition: length(users) > 1

        Chatroom newRoom= new Chatroom(users); // create new room with given list of users
        if (length(users) = 2){
            privateChats.add(newRoom);  // add to privateChats list if there's only 2 users in the room (i.e. its private)
        }
        else{
            conferenceChats.add(newRoom); // add chatroom to conferenceChats !!!!!!!      This is NOT how conferenceChats should be used but this is until we can guarantee we won't try to create private groupchats
        }
    }

    public void addUser(ArrayList<String> users, UUID chat) {
        for (Chatroom room : conferenceChats){
            if (room.getId() = chat){               // check the UUID to make sure we have the right Chatroom
                for(String userID : users){
                    room.getUsersInvolved().add(userID);  // add users to the usersInvolved in this chat
                }
            }
        }
    }

    public void removeUser(ArrayList<String> users, UUID chat) {
        for (Chatroom room : conferenceChats) {
            if (room.getId() = chat) {               // check the UUID to make sure we have the right Chatroom
                for (String userID : users) {
                    room.getUsersInvolved().add(userID);  // remove users to the usersInvolved in this chat
                }
            }
        }
    }

    public void sendMessage(String message, UUID chat){
        for (Chatroom room : conferenceChats) {
            if (room.getId() = chat) {               // check the UUID to make sure we have the right Chatroom
                room.getHistoricalChats().add(message);  // add message to historicalChats of room
                }
        }
    }


}
