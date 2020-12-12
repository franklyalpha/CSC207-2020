package UI;

import Controllers.CancelEventController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.UserAlreadyExistException;
import menuPresenter.AdminPresenter;

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
    private AdminPresenter adminPresenter;

    /**
     * Instantiates new <code>AdminCancelEventUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public AdminCancelEventUI(UserController userController) {
        super(userController);
        deleteEvent = new CancelEventController(userController);
        adminPresenter = new AdminPresenter();
    }

    /**
     * Does the action delete event. Run the method in this UI
     * Gives instructions for invalid inputs.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        ArrayList<String[]> emptyEvents = deleteEvent.findEmptyEvents();
        if (emptyEvents.size() == 0){
            System.out.println(adminPresenter.strNoEventAdded());
        }
        String cancelEventID = selectEventToCancel(emptyEvents);
        deleteEvent.cancelAndUpdate(cancelEventID);
    }


    private String selectEventToCancel(ArrayList<String[]> emptyEvents) throws ExceedingMaxAttemptException {
        ArrayList<String> eventsID = printEventsCanCancel(emptyEvents);
        System.out.println(adminPresenter.strCancelEventIDPrompt());
        for(int i=0; i<3; i++){
            Scanner eventScan = new Scanner(System.in);
            String eventID = eventScan.nextLine();
            if (eventsID.contains(eventID)){
                return eventID;
            }
            System.out.println(userPresenter.strInvalidInput() + userPresenter.strPleaseTryAgain());
        }
        throw new ExceedingMaxAttemptException("Exceeding maximum attempt times");
    }


    private ArrayList<String> printEventsCanCancel(ArrayList<String[]> emptyEvents) {
        System.out.println(adminPresenter.strEventCancelHeader());
        ArrayList<String> eventsID = new ArrayList<>();
        for(String[] events: emptyEvents){
            System.out.println(adminPresenter.strPrintEventInfo(events));
            eventsID.add(events[0]);
        }
        return eventsID;
    }
}
