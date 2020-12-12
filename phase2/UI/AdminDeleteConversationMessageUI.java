package UI;

import Controllers.DeletePrivateConversationController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
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
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        chooseChatOrMessage();
    }


    private void chooseChatOrMessage() throws ExceedingMaxAttemptException {
        System.out.println(adminDeleteMessagePresenter.strMessageActionMenu());
        for(int i=0; i<3; i++){
            Scanner choice = new Scanner(System.in);
            if(choice.hasNextInt()){
                choosePrivateOrGroup(choice);
                return;
            }
            System.out.println(adminDeleteMessagePresenter.strInvalidInput());
        }
        throw new ExceedingMaxAttemptException("Maximum number of attempts exceeded");

    }


    private void choosePrivateOrGroup(Scanner choice) throws ExceedingMaxAttemptException {
        switch(choice.nextInt()){
            case 1: deletePrivateConversation(); break;
            case 2: deleteSelectedMessages(); break;
        }
    }


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


    private void inputAndDeletePrivate(DeletePrivateConversationController deletePrivate) throws UserNotFoundException {
        Scanner users = new Scanner(System.in);
        String username1 = users.nextLine();
        String username2 = users.nextLine();
        deletePrivate.checkIsUser(new String[]{username1, username2});
        deletePrivate.deletePrivateConversation();
    }


    // followings can be placed in another UI
    private void deleteSelectedMessages() throws ExceedingMaxAttemptException {
        new AdminDeleteMessageUI(userController).run();
    }


}
