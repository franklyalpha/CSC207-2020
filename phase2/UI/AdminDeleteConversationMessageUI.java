package UI;

import Controllers.DeletePrivateConversationController;
import Controllers.DeleteSelectedMessagesController;
import Controllers.UserController;
import globallyAccessible.UserNotFoundException;
import menuPresenter.AdminDeleteMessagePresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminDeleteConversationMessageUI extends UserUI {

    final private AdminDeleteMessagePresenter adminDeleteMessagePresenter = new AdminDeleteMessagePresenter();

    public AdminDeleteConversationMessageUI(UserController userController) {
        super(userController);
    }

    @Override
    public void run() {
        super.run();
    }

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

    private void choosePrivateOrGroup(Scanner choice) {
        switch(choice.nextInt()){
            case 1: deletePrivateConversation();
            case 2: deleteSelectedMessages();
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
    private void deleteSelectedMessages(){
        new AdminDeleteMessageUI(userController).run();
    }


}
