package UI;

import Controllers.CreateUserController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.UserAlreadyExistException;
import globallyAccessible.UserType;
import menuPresenter.OrganizerPresenter;

import java.util.Scanner;

/**
 * UI for <code>Organizer</code> to create different type of <code>User</code>.
 */
public class OrganizerCreateUserUI extends AbstractUI {

    /**
     * @param presenter: an instance of <code>OrganizerPresenter</code> being instantiated.
     * @param createUser;: an instance of <code>CreateUserController</code> being instantiated which contains method
     * to create different type of user.
     */
    private OrganizerPresenter presenter;
    private CreateUserController createUser;

    /**
     * Instantiates new <code>OrganizerCreateUserUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OrganizerCreateUserUI(UserController userController) {
        super(userController);
        presenter = new OrganizerPresenter();
        createUser = new CreateUserController(userController);
    }

    /**
     * Lets <code>Organizer</code> choose the type of user they want to create and create such user.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        for(int i=0; i<3; i++){
            try{
                int type = acquireUserType();
                Scanner input0 = new Scanner(System.in);
                createUserWithGivenType(createUser, type, input0);
                break;
            }catch (UserAlreadyExistException e){
                System.out.println(presenter.strSpeakerExistWarning());
            }
        }
        throw new ExceedingMaxAttemptException("Maximum number of attempts exceeded");
    }

    private void createUserWithGivenType(CreateUserController createUser, int type, Scanner input0)
            throws UserAlreadyExistException {
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
