package UI;

import Controllers.CancelEventController;
import Controllers.UserController;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminCancelEventUI extends AbstractUI {
    private CancelEventController deleteEvent;

    public AdminCancelEventUI(UserController userController) {
        super(userController);
        deleteEvent = new CancelEventController(userController);
    }

    @Override
    public void run() {
        ArrayList<String[]> emptyEvents = deleteEvent.findEmptyEvents();
        if (emptyEvents.size() == 0){
            System.out.println("There are no events being added");
            return;
        }
        String cancelEventID = selectEventToCancel(emptyEvents);
        deleteEvent.cancelAndUpdate(cancelEventID);
    }

    private String selectEventToCancel(ArrayList<String[]> emptyEvents){
        ArrayList<String> eventsID = printEventsCanCancel(emptyEvents);
        System.out.println("Please enter the ID of event you want to cancel: ");
        while(true){
            Scanner eventScan = new Scanner(System.in);
            String eventID = eventScan.nextLine();
            if (eventsID.contains(eventID)){
                return eventID;
            }
            System.out.println("Invalid input! Try again; ");
        }
    }

    private ArrayList<String> printEventsCanCancel(ArrayList<String[]> emptyEvents) {
        System.out.println("Below are events you can cancel: \n");
        ArrayList<String> eventsID = new ArrayList<>();
        for(String[] events: emptyEvents){
            System.out.println(events[0] + ": " + events[1] + "\n");
            eventsID.add(events[0]);
        }
        return eventsID;
    }
}
