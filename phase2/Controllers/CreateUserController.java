package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;
import useCases.UserFactory;

public class CreateUserController extends AbstractController{
    public CreateUserController(UserController userController) {
        super(userController);
    }
    public void ValidateName(String name) throws SpeakerAlreadyExistException {
        if (userManager.isUser(name) !=0){
            throw new SpeakerAlreadyExistException("User already exist!!");
        }
    }

    public String createUser(UserType type,String name, String password){
        new UserFactory(userManager).construct(name, password, type);
        return name;
    }
}
