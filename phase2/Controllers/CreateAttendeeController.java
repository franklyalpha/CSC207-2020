package Controllers;

import globallyAccessible.UserAlreadyExistException;
import globallyAccessible.UserType;

public class CreateAttendeeController extends AbstractController implements ICreateUser {
    public CreateAttendeeController(UserController userController) {
        super(userController);
    }

    public void ValidateName(String name) throws UserAlreadyExistException {
        if(userManager.isUser(name) != 0){
            throw new UserAlreadyExistException("Attendee already exist!");
        }
    }



    public String createUser(String name, String password) {
        String username = userManager.createUser(name, password, UserType.ATTENDEE);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
        return username;

    }
}
