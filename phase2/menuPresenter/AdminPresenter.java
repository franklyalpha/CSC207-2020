package menuPresenter;

import java.util.ArrayList;

/**
 * Represents a AdminPresenter.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class AdminPresenter extends UserPresenter {
    public AdminPresenter(){super();}

    /**
     * This hold all the menu options
     * @return the menu options in a String ArrayList
     */
    public ArrayList<String> optionMenu(){
        ArrayList<String> availableAction = new ArrayList<>();
        availableAction.add(" - Send a private message");
        availableAction.add(" - View private messages");
        availableAction.add(" - Send messages to an Organizer-Speaker chat");
        availableAction.add(" - View messages from an Organizer-Speaker chat");
        availableAction.add(" - Delete conversations or specific messages");
        availableAction.add(" - Delete empty events");
        return availableAction;
    }

    public String strNoEventAdded(){return "There are no events being added";}

    public String strCancelEventIDPrompt(){return "Please enter the ID of event you want to cancel: ";}

    public String strEventCancelHeader(){return "Below are events you can cancel: \n";}

    /**
     * @param events Info of the event
     * @return the Info in String form
     */
    public String strPrintEventInfo(String[] events){return events[0] + ": " + events[1] + "\n";}
}
