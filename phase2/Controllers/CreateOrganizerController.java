package Controllers;

import globallyAccessible.UserAlreadyExistException;
import globallyAccessible.UserType;

public class CreateOrganizerController extends AbstractController implements ICreateUser{
    public CreateOrganizerController(UserController userController) {
        super(userController);
    }
    public void ValidateName(String name) throws UserAlreadyExistException {
        if(userManager.isUser(name) != 0){
            throw new UserAlreadyExistException("Organizer already exist!");
        }
    }

    public String createUser(String name, String password){
        String username = userManager.createUser(name, password, UserType.ORGANIZER);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
        return username;
    }
}
