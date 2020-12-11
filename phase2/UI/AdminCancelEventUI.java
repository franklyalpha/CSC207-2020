package UI;

import Controllers.CancelEventController;
import Controllers.UserController;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * UI for <code>Administrator</code> to CancelEvent.
 */

public class AdminCancelEventUI extends AbstractUI {
    /**
     * @param deleteEvent: an instance of <code>CancelEventController</code> being instantiated.
     */
    private CancelEventController deleteEvent;

    /**
     * Instantiates new <code>AdminCancelEventUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public AdminCancelEventUI(UserController userController) {
        super(userController);
        deleteEvent = new CancelEventController(userController);
    }

    /**
     * Does the action delete event. Run the method in this UI
     * Gives instructions for invalid inputs.
     */
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

    /**
     * Lets <code>Administrator</code> select the event they want to cancel .
     * @param emptyEvents: events they can select to cancel.
     * @return Event IDs that <code>Administrator</code> cancel.
     */
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

    /**
     * Prints A list of String showing the events <code>Administrator</code> could cancel .
     * @param emptyEvents: events they can cancel
     * @return A list of Event IDs that <code>Administrator</code> could cancel.
     */
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
