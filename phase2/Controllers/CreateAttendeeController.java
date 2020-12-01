package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;

public class CreateAttendeeController extends AbstractController implements CreateUser {
    public CreateAttendeeController(UserController userController) {
        super(userController);
    }

    public void ValidateName(String name) throws SpeakerAlreadyExistException {
        if(userManager.isUser(name) == 0){
        }
        else{
            throw new SpeakerAlreadyExistException("Attendee already exist!");
        }
    }



    public String createUser(String name, String password) {
        String username = userManager.createUser(name, password, UserType.ATTENDEE);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
        return username;

    }
}
