package Controllers;

import globallyAccessible.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * the controller being extended by other controllers dealing with messaging and updates regardng messaging.
 */
public class MessagingController extends AbstractController{
    /**
     * the constructor of this controller.
     * @param userController an instance of <>UserController</>.
     */
    public MessagingController(UserController userController) {
        super(userController);
    }

    /**
     * Sends a private message to the user specified by <code>userName</code>. If there is no pre-existing conversation,
     * a new <code>MessageRoom</code> is created. If the specified user does not exist, an invalid input message appears.
     * @param userName String representing the username of the user we wish to send the message to.
     * @param message String representing the message we wish to send.
     */
    public void send(String userName, String message) throws UserNotFoundException {
        message = userManager.currentUsername() + ": " + message;
        if (userManager.contactable(userName)){
            contactsSend(userName, message);
        }
        else{
            newContactSend(userName, message);
        }
    }

    private void contactsSend(String userName, String message) {
        HashMap<String, UUID> contacts = userManager.contacts();
        UUID chatID = contacts.get(userName);
        messageRoomManager.sendPrivateMessage(message, chatID);
    }

    private void newContactSend(String userName, String message) throws UserNotFoundException {
        if (userManager.isUser(userName) != 0){
            UUID newChatroom = newPrivateChatroomCreator(userName);
            messageRoomManager.sendPrivateMessage(message, newChatroom);
        }
        else {
            throw new UserNotFoundException("User not found.");
        }
    }

    /**
     * Creates a new conversation between the current user and the user specified by <code>userName</code>
     * as the participants.
     * @param userName String representing the username of the other participant of this new <code>Chatroom</code>.
     * @return Returns the <>UUID</> of newly created conversation.
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

}
