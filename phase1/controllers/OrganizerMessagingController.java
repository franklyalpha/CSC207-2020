package controllers;

import globalConstants.UserNotFoundException;
import presenter.Presenter;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class OrganizerMessagingController extends AdministrativeMessagingController
        implements IOrganizerMessaging {

    public OrganizerMessagingController(Object[] managers){
        super(managers);
    }



    @Override
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
