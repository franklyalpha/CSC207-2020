package globallyAccessible;

public class RequestNotFoundException extends Exception {
    public RequestNotFoundException(String message){
        super(message);
    }
}
