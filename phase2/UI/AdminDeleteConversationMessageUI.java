package UI;

import Controllers.DeletePrivateConversationController;
import Controllers.UserController;
import globallyAccessible.UserNotFoundException;
import menuPresenter.AdminDeleteMessagePresenter;

import java.util.Scanner;
/**
 * UI for <code>Administrator</code> to delete conversation message.
 */

public class AdminDeleteConversationMessageUI extends AbstractUI {
    /**
     * @param adminDeleteMessagePresenter : an instance of <code>AdminDeleteMessagePresenter</code> being instantiated.
     */

    final private AdminDeleteMessagePresenter adminDeleteMessagePresenter = new AdminDeleteMessagePresenter();
    /**
     * Instantiates new <code>AdminDeleteConversationMessageUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public AdminDeleteConversationMessageUI(UserController userController) {
        super(userController);
    }

    /**
     * Does the action deleting conversation message. Run the method in this UI.
     */
    @Override
    public void run() {
        chooseChatOrMessage();
    }

    /**
     * Lets <code>Administrator</code> choose chat or message they wants to delete.
     * Give instruction for invalid input.
     */
    private void chooseChatOrMessage(){
        System.out.println(adminDeleteMessagePresenter.strMessageActionMenu());
        while(true){
            Scanner choice = new Scanner(System.in);
            if(choice.hasNextInt()){
                choosePrivateOrGroup(choice);
                break;
            }
            System.out.println(adminDeleteMessagePresenter.strInvalidInput());
        }
    }

    /**
     * Lets <code>Administrator</code> choose whether they want to delete private conversation or message.
     */
    private void choosePrivateOrGroup(Scanner choice) {
        switch(choice.nextInt()){
            case 1: deletePrivateConversation(); break;
            case 2: deleteSelectedMessages(); break;
        }
    }

    /**
     * Deletes private conversation <code>Administrator</code> selected.
     */
    private void deletePrivateConversation(){
        while(true){
            try{
                DeletePrivateConversationController deletePrivate = new DeletePrivateConversationController(userController);
                System.out.println(adminDeleteMessagePresenter.strUsersToAddPrompt());
                inputAndDeletePrivate(deletePrivate);
                break;
            }catch(UserNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * @param deletePrivate An controller that response for delete Private Conversation.
     * @throws UserNotFoundException when there is not exist a private conversation between Users.
     */
    private void inputAndDeletePrivate(DeletePrivateConversationController deletePrivate) throws UserNotFoundException {
        Scanner users = new Scanner(System.in);
        String username1 = users.nextLine();
        String username2 = users.nextLine();
        deletePrivate.checkIsUser(new String[]{username1, username2});
        deletePrivate.deletePrivateConversation();
    }

    /**
     * Deletes message <code>Administrator</code> selected.
     */
    // followings can be placed in another UI
    private void deleteSelectedMessages(){
        new AdminDeleteMessageUI(userController).run();
    }


}
