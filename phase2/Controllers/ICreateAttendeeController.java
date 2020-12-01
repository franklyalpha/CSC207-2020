package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;

public class ICreateAttendeeController extends AbstractController implements ICreateUser {
    public ICreateAttendeeController(UserController userController) {
        super(userController);
    }

    public void ValidateName(String name) throws SpeakerAlreadyExistException {
        if(userManager.isUser(name) != 0){
            throw new SpeakerAlreadyExistException("Attendee already exist!");
        }
    }



    public String createUser(String name, String password) {
        String username = userManager.createUser(name, password, UserType.ATTENDEE);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
        return username;

    }
}
