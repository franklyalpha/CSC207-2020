package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;

public class CreateAdministratorController extends AbstractController implements ICreateUser {
    public CreateAdministratorController(UserController userController) {
        super(userController);
    }

    @Override
    public void ValidateName(String name) throws SpeakerAlreadyExistException {
        if(userManager.isUser(name) != 0){
            throw new SpeakerAlreadyExistException("Organizer already exist!");
        }
    }

    @Override
    public String createUser(String name, String password) {
        String username = userManager.createUser(name, password, UserType.ADMINISTRATOR);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
        return username;
    }
}
