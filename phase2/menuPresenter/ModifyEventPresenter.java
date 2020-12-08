package menuPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModifyEventPresenter extends UserPresenter{

    public ModifyEventPresenter(){
        super();
    }

    public String printMaxNumEventPrompt_1(ArrayList<String[]> upcomingActivities){
        return "Here are the information of all the available activities: " + upcomingActivities + "\n" +
                "Please input the Activity Id you want to modify:";
    }

    public String printMaxNumEventPrompt_2(){
        return "Please input the new Maximum number of people for the activity :";
    }

    public String printNoEvent(){
        return "No activities created yet";
    }

    public String askForRequirementPrompt(){
        return "Please input the number of Projector, Microphone, DJ equipment and Party Audio System" +
                " \n this event needs, separate by space: \n(if input less than or equal to 0, will default to 0)";
    }

    public String printSuggestedRoomPrompt(List<UUID> suggestedList){
        return "This is the suggested room list that match all your requirement:" + suggestedList;
    }
}
