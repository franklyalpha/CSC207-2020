package functionalityPresenters;

import Controllers.UserController;
import menuPresenter.ModifyEventPresenter;

import java.util.*;

/**
 * StatisticPresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class StatisticPresenter extends AbstractPresenter{

    /**
     * Creates a StatisticPresenter
     * @param userController contains managers
     */
    public StatisticPresenter(UserController userController) {
        super(userController);
    }

    /**
     * Returns the info of popular events
     * @return a string that contains the info of the events
     */
    public String popularEvent() {
        HashMap<String, Integer> result = new HashMap<>();
        ArrayList<String[]> eventList = eventManager.viewUpcomingEvents();
        for (String[] c : eventList) {
            result.put(c[0], eventManager.numAttendee(UUID.fromString(c[0])));
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(result.entrySet());
        list.sort((o1, o2) -> o2.getValue() - o1.getValue());
        ArrayList<String> topFive = new ArrayList<>();
        int i = 0;
        while(i < list.size() && i < 5){
            topFive.add(list.get(i).getKey());
            i = i + 1;
        }
        return topFiveInfo(topFive);
    }

    /**
     * Helper function to extract the info of the events
     * @param topFive contains the top five events
     * @return the information after formatting
     */
    private String topFiveInfo(ArrayList<String> topFive){
        ArrayList<String[]> info = new ArrayList<>();
        for (String actID: topFive){
            info.add(eventManager.searchEventByUUID(actID));
        }
        return new ModifyEventPresenter().strSchedule(info);
    }
}
