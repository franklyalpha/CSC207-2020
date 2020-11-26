package MessagingControllers;

import globallyAccessible.UserNotFoundException;
import presenter.Presenter;

import java.util.ArrayList;
import java.util.Scanner;

public class OrganizerMessagingController extends AdministrativeMessagingController {

    public OrganizerMessagingController(Object[] managers){
        super(managers);
    }

    public void messageAllAttendee() {
        try{
            ArrayList<String> attendeeName = userManager.allAttendee();
            Scanner messageScanner = new Scanner(System.in);
            Presenter.printMessagePrompt();
            String message = messageScanner.nextLine();
            for (String attendee : attendeeName){
                send(attendee, message);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
