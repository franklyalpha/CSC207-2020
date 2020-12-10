package menuPresenter;

import java.util.ArrayList;

public class RemoveEventPresenter extends UserPresenter {

    public RemoveEventPresenter() {
        super();
    }

    public String printMaxNumEventPrompt_1(ArrayList<String[]> upcomingActivities) {
        return "Here are the information of all the available activities: " + upcomingActivities +
        "\n Please input the Event Id you want to cancel:";
    }

    public String printIDForTheEventToBeCancelled(ArrayList<String[]> allActivities) {
        return strSchedule(allActivities) + "Please input the ID of the event that you want to cancel:";
    }

    public String printNoEvent() {
        return "No activities available to be cancelled now";
    }
}