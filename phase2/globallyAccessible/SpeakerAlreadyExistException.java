package globallyAccessible;

public class SpeakerAlreadyExistException extends Exception {
    public SpeakerAlreadyExistException(String message){
        super(message);
    }
}
