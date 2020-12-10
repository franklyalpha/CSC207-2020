package globallyAccessible;

public class ExceedingMaxAttemptException extends Exception{
    public ExceedingMaxAttemptException(String message){
        super(message);
    }
}
