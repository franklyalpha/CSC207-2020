package Controllers;

import globallyAccessible.UserNotFoundException;

import java.util.HashMap;
import java.util.UUID;

public class DeletePrivateConversationController extends AbstractController {
    private String[] usernames;

    public DeletePrivateConversationController(UserController userController) {
        super(userController);
        usernames = new String[]{};
    }

    public void checkIsUser(String[] usernames) throws UserNotFoundException {
        for (String users : usernames) {
            if (userManager.isUser(users) == 0) {
                throw new UserNotFoundException("user" + users + " is not found");
            }
        }
        this.usernames = usernames;
    }

    public void deletePrivateConversation(){
        HashMap<String, UUID> contacts = userManager.otherContacts(usernames[0]);
        if(contacts.containsKey(usernames[1])){
            messageRoomManager.deletePrivateConversation(contacts.get(usernames[1]));
            userManager.deleteContact(usernames[0], usernames[1]);
            userManager.deleteContact(usernames[1], usernames[0]);
        }

    }
}
