package Controllers;

import globallyAccessible.UserAlreadyExistException;
import globallyAccessible.UserType;
import useCases.UserFactory;

/**
 * This class is responsible for creating all types of users this program is allowed.
 */
public class CreateUserController extends AbstractController{

    /**
     * The constructor of this class.
     * @param userController: an instance of UserController.
     */
    public CreateUserController(UserController userController) {
        super(userController);
    }

    /**
     * Responsible for checking whether the given username is exist in the system.
     * @param name: the username of potential new user of this system.
     * @throws UserAlreadyExistException: is thrown when <>name</> is the same as another existing user's username.
     */
    public void ValidateName(String name) throws UserAlreadyExistException {
        if (userManager.isUser(name) !=0){
            throw new UserAlreadyExistException("User already exist!!");
        }
    }

    /**
     * Responsible for creating a new user based on given inputs.
     * @param type: the type of this user.
     * @param name: the username of this user.
     * @param password: the password of this user.
     * @return will return the newly modified username from the program.
     */
    public String createUser(UserType type, String name, String password){
        return new UserFactory(userManager).construct(name, password, type);
    }
}
