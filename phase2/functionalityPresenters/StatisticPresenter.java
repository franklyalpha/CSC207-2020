package functionalityPresenters;

import Controllers.UserController;

import java.util.*;

public class StatisticPresenter extends AbstractPresenter{
    public StatisticPresenter(UserController userController) {
        super(userController);
    }

    public ArrayList<String> popularEvent() {
        HashMap<String, Integer> result = new HashMap<>();
        ArrayList<String[]> eventList = eventManager.viewUpcomingActivities();
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
        return topFive;
    }
}
