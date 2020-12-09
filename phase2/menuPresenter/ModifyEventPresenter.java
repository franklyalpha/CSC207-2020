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
        return "Please input the number of Projector, Microphone and Party Audio System" +
                " \n is required for this event in order, separate by space: ";
    }

    public String printSuggestedRoomPrompt(List<String[]> suggestedList){
        String output = "This is the suggested room list that match all your requirements:";
        int i = 0;
        for (String[] roomInfo: suggestedList){
            String newInfo = "Room No." + i + ": \n" + roomInfo[1] + "\n";
            output += newInfo + "\n";
        }
        return output;
    }
}
