package entities;

import java.util.ArrayList;
import java.util.UUID;

public class Chatroom {

    private UUID id;  // unique identifier for each entities.Chatroom
    private ArrayList<String> usersInvolved;  // holds username of all Users in this entities.Chatroom
    private ArrayList<String> historicalChats;  // holds previously sent messages from this entities.Chatroom

    public Chatroom(){
        id = UUID.randomUUID();  // assign id
        usersInvolved = new ArrayList<>();  // specify users in this entities.Chatroom with given parameter
        historicalChats = new ArrayList<String>();  // start with empty chatHistory
    }

    public Chatroom(ArrayList<String> users){
        id = UUID.randomUUID();  // assign id
        usersInvolved = users;  // specify users in this entities.Chatroom with given parameter
        historicalChats = new ArrayList<String>();  // start with empty chatHistory
    }

    // getter for id
    public UUID getId(){
        return this.id;
    }

    // getter for usersInvolved
    public ArrayList<String> getUsersInvolved(){
        return this.usersInvolved;
    }

    // getter for historicalChats
    public ArrayList<String> getHistoricalChats(){
        return this.historicalChats;
    }

    // no setters: we don't want to be able to modify the entities.Chatroom ID.
    // We will 'get' the lists in the UseCase modify the ArrayLists there.

}



