package Controllers;

import globallyAccessible.UserNotFoundException;
import useCases.AdminManager;

import java.util.HashMap;
import java.util.UUID;

/**
 * This class is responsible for deleting any private conversation between two users in this program.
 */
public class DeletePrivateConversationController extends AbstractController {

    /**
     * The array containing usernames of Users.
     */
    private String[] usernames;

    /**
     * an instance of <>AdminManager</>.
     */
    private final AdminManager adminManager;

    /**
     * The constructor of this class.
     * @param userController: an instance of UserController.
     */
    public DeletePrivateConversationController(UserController userController) {
        super(userController);
        usernames = new String[]{};
        adminManager = new AdminManager(userManager);
    }

    /**
     * Checks whether the program has two <>User</> having same username as given inputted usernames.
     * @param usernames: an array containing usernames of two potential <>Users</>.
     * @throws UserNotFoundException: is thrown when any one username doesn't match with any existing user's username.
     */
    public void checkIsUser(String[] usernames) throws UserNotFoundException {
        for (String users : usernames) {
            if (userManager.isUser(users) == 0) {
                throw new UserNotFoundException("user" + users + " is not found");
            }
        }
        this.usernames = usernames;
    }

    /**
     * Deletes the private conversation between two users from <>usernames</> and updates all relevant info.
     */
    public void deletePrivateConversation(){
        HashMap<String, UUID> contacts = userManager.otherContacts(usernames[0]);
        if(contacts.containsKey(usernames[1])){
            messageRoomManager.deletePrivateConversation(contacts.get(usernames[1]));
            adminManager.deleteContact(usernames[0], usernames[1]);
            adminManager.deleteContact(usernames[1], usernames[0]);
        }

    }
}
