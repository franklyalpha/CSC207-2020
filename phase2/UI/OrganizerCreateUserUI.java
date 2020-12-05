package UI;

import Controllers.CreateUserController;
import Controllers.UserController;
import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;
import menuPresenter.OrganizerPresenter;

import java.util.Scanner;

public class OrganizerCreateUserUI extends AbstractUI {
    private OrganizerPresenter presenter;
    private CreateUserController createUser;

    public OrganizerCreateUserUI(UserController userController) {
        super(userController);
        presenter = new OrganizerPresenter();
        createUser = new CreateUserController(userController);
    }

    @Override
    public void run() {
        while(true){
            try{
                int type = acquireUserType();
                Scanner input0 = new Scanner(System.in);
                createUserWithGivenType(createUser, type, input0);
                break;
            }catch (SpeakerAlreadyExistException e){
                System.out.println(presenter.strSpeakerExistWarning());
            }
        }
    }

    private void createUserWithGivenType(CreateUserController createUser, int type, Scanner input0)
            throws SpeakerAlreadyExistException {
        System.out.println(presenter.strUserNamePrompt());
        String name = input0.next();
        createUser.ValidateName(name);
        System.out.println(presenter.strPasswordPrompt());
        String password = input0.next();
        System.out.println(presenter.strUsernameConfirmation
                (createUser.createUser(UserType.values()[type - 1], name, password)));
    }

    private int acquireUserType(){
        while(true){
            Scanner input1 = new Scanner(System.in);
            System.out.println(presenter.strUserTypePrompt());
            int type = input1.nextInt();
            if(type >= 1 && type <= UserType.values().length){
                return type;
            }
            else{
                System.out.println(presenter.strInvalidUserType());
            }
        }
    }


}
