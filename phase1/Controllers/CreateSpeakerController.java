package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;

public class CreateSpeakerController extends AbstractController {


    public CreateSpeakerController(UserController userController) {
        super(userController);
    }

    public void ValidateName(String name) throws SpeakerAlreadyExistException {
        if(userManager.isUser(name) == 0){
        }
        else{
            throw new SpeakerAlreadyExistException("Speaker already exist!");
        }
    }

    public String createNewSpeaker(String name, String password){
        String username = userManager.createUser(name, password, UserType.SPEAKER);
        messageRoomManager.addUser(name, messageRoomManager.getCoopId());
        return username;
    }
}
