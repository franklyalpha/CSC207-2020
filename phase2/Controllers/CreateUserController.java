package Controllers;

import com.sun.jdi.InvalidTypeException;
import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;
import Presenters.Presenter;

import java.util.Scanner;
public class CreateUserController extends AbstractController{
    private CreateUser createAttendee;
    private CreateUser createSpeaker;
    public CreateUserController(UserController userController) {
        super(userController);
        CreateUser createSpeaker  = new CreateSpeakerController(userController);
        CreateUser createAttendee = new CreateAttendeeController(userController);
    }
    public void ValidateName(String name) throws SpeakerAlreadyExistException {
        if(userManager.isUser(name) == 0){
        }
        else{
            throw new SpeakerAlreadyExistException("Attendee already exist!");
        }
    }

    public String createUser(UserType type,String name, String password){
        String userName = null;
        switch (type) {
            case ATTENDEE: {
                userName = createAttendee.createUser(name, password);
                break;
            }
            case SPEAKER: {
                userName = createSpeaker.createUser(name, password);
                break;
            }
        }
        return userName;
    }
}
