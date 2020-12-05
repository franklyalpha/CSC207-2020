package Controllers;

import globallyAccessible.SpeakerAlreadyExistException;
import globallyAccessible.UserType;

public class CreateUserController extends AbstractController{
    private ICreateUser createAttendee;
    private ICreateUser createSpeaker;
    private ICreateUser createOrganizer;
    private ICreateUser createAdministrator;
    public CreateUserController(UserController userController) {
        super(userController);
        createSpeaker  = new CreateSpeakerController(userController);
        createAttendee = new CreateAttendeeController(userController);
        createOrganizer = new CreateOrganizerController(userController);
        createAdministrator = new CreateAdministratorController(userController);
    }
    public void ValidateName(String name) throws SpeakerAlreadyExistException {
        if (userManager.isUser(name) !=0){
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
            case ORGANIZER: {
                userName = createOrganizer.createUser(name, password);
                break;
            }
            case ADMINISTRATOR:{
                userName = createAdministrator.createUser(name, password);
                break;
            }
        }
        return userName;
    }
}
