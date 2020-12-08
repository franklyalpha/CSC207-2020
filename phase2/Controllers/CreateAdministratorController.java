package Controllers;

import globallyAccessible.UserAlreadyExistException;
import globallyAccessible.UserType;

public class CreateAdministratorController extends AbstractController implements ICreateUser {
    public CreateAdministratorController(UserController userController) {
        super(userController);
    }

    @Override
    public void ValidateName(String name) throws UserAlreadyExistException {
        if(userManager.isUser(name) != 0){
            throw new UserAlreadyExistException("Organizer already exist!");
        }
    }

    @Override
    public String createUser(String name, String password) {
        String username = userManager.createUser(name, password, UserType.ADMINISTRATOR);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
        return username;
    }
}
