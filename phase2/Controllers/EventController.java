package Controllers;

import globallyAccessible.NoEventsException;

import java.util.ArrayList;

/**
 * This controller is extended by all controllers modifying events-associated tasks.
 */
public class EventController extends AbstractController{

    /**
     * The constructor of this controller.
     * @param userController An instance of <>UserController</>.
     */
    public EventController(UserController userController) {
        super(userController);
    }

    /**
     * Helper function for getting the UUIDs of available activities.
     * @param available ArrayList of Strings representing all available activities.
     * @return Returns an ArrayList of Strings representing the UUIDs of all available activities.
     */
    protected ArrayList<String> extractActIDHelper (ArrayList<String[]> available){
        ArrayList<String> actIDs = new ArrayList<>();
        for (String[] schedule: available){
            actIDs.add(schedule[0]);
        }
        return actIDs;
    }

    /**
     * returns information of all existing events of this system.
     * @return An instance of <>ArrayList</> containing <>String[]</> of ebent's info, each <>String[]</> includes:
     * UUID of this activity, topic, start time, end time, UUID of assigned room, the event's description,
     * the type of event and name of speaker(s).
     * @throws NoEventsException is thrown when no events has yet being stored in the system.
     */
    public ArrayList<String[]> getAllEvents() throws NoEventsException {
        ArrayList<String[]> allEvents = eventManager.viewUpcomingEvents();
        if (allEvents.size() == 0) {
            throw new NoEventsException("No events created yet");
        }
        return allEvents;
    }
}
