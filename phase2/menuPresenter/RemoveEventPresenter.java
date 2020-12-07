package menuPresenter;

import java.util.ArrayList;

public class RemoveEventPresenter extends UserPresenter {

    public RemoveEventPresenter() {
        super();
    }

    public void printMaxNumEventPrompt_1(ArrayList<String[]> upcomingActivities) {
        System.out.println("Here are the information of all the available activities: " + upcomingActivities);
        System.out.println("Please input the Event Id you want to cancel:");
    }

    public void printIDForTheEventToBeCancelled(ArrayList<String[]> allActivities) {
        System.out.println("Please input the ID of the event that you want to cancel:");
    }

    public void printNoEvent() {
        System.out.println("No activities available to be cancelled now");
    }
}