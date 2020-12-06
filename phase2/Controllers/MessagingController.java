package Controllers;

import globallyAccessible.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MessagingController extends AbstractController{
    public MessagingController(UserController userController) {
        super(userController);
    }

    /**
     * Asks the user to input the username of the person they want to contact. If valid, asks the user to input the message
     * they want to send, then sends it.
     */


    /**
     * Sends a private message to the user specified by <code>userName</code>. If there is no pre-existing conversation,
     * a new <code>Chatroom</code> is created. If the specified user does not exist, an invalid input message appears.
     * @param userName String representing the username of the user we wish to send the message to.
     * @param message String representing the message we wish to send.
     */
    public void send(String userName, String message) throws UserNotFoundException {
        message = userManager.currentUsername() + ": " + message;
        if (userManager.contactable(userName)){
            // may consider putting first two lines in use-case;
            HashMap<String, UUID> contacts = userManager.contacts();
            UUID chatID = contacts.get(userName);
            messageRoomManager.sendPrivateMessage(message, chatID);
        }
        else{
            // may consider putting into another private method;
            if (userManager.isUser(userName) != 0){
                UUID newChatroom = newPrivateChatroomCreator(userName);
                messageRoomManager.sendPrivateMessage(message, newChatroom);
            }
            else {
                throw new UserNotFoundException("User not found.");
            }
        }
    }

    /**
     * Creates a new private <code>Chatroom</code> with the current user and the user specified by <code>userName</code>
     * as the participants.
     * @param userName String representing the username of the other participant of this new <code>Chatroom</code>.
     * @return Returns the newly created <code>Chatroom</code> object.
     */
    private UUID newPrivateChatroomCreator(String userName){
        ArrayList<String> userInvolved = new ArrayList<>();
        userInvolved.add(userManager.currentUsername());
        userInvolved.add(userName);

        UUID newChatroom = messageRoomManager.createChatroom(userInvolved);
        userManager.selfAddChatroom(userName, newChatroom);
        userManager.otherAddChatroom(userName, newChatroom);
        return newChatroom;
    }

    /**
     * Displays the user's private messages.
     */


    /**
     * Prints the private messages between the user and the specified contact, but only if they are connected.
     * @param historyChat ArrayList of Strings representing the user's chats.
     * @param contact UUID representing the contact whose messages we want to retrieve.
     */


    /**
     * Displays the user's group messages.
     */



    /**
     * Prints the group messages from the specified activity, if it is valid and exists.
     * @param historyChat ArrayList of Strings representing the user's group chats.
     */

}
