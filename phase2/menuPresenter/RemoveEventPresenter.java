package menuPresenter;

import java.util.ArrayList;

/**
 * Represents a RemoveEventPresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class RemoveEventPresenter extends UserPresenter {

    public RemoveEventPresenter() {
        super();
    }

    /**
     * @param allActivities all activity infos
     * @return the String prompt of id to cancel
     */
    public String printIDForTheEventToBeCancelled(ArrayList<String[]> allActivities) {
        return strSchedule(allActivities) + "Please input the ID of the event that you want to cancel:";
    }

    public String printNoEvent() {
        return "No events available to be cancelled now";
    }
}