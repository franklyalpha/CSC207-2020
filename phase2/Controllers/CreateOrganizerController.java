package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;
public class CreateOrganizerController extends AbstractController implements ICreateUser{
    public CreateOrganizerController(UserController userController) {
        super(userController);
    }
    public void ValidateName(String name) throws SpeakerAlreadyExistException {
        if(userManager.isUser(name) != 0){
            throw new SpeakerAlreadyExistException("Organizer already exist!");
        }
    }

    public String createUser(String name, String password){
        String username = userManager.createUser(name, password, UserType.ORGANIZER);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
        return username;
    }
}
