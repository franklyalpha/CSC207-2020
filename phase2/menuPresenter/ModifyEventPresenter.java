package menuPresenter;

import java.util.ArrayList;
import java.util.List;

public class ModifyEventPresenter extends UserPresenter{

    public ModifyEventPresenter(){
        super();
    }

    public String printMaxNumEventPrompt_1(ArrayList<String[]> upcomingActivities){
        return "Here are the information of all the available activities:\n " + getString(upcomingActivities, "") + "\n" +
                "Please input the Activity Id you want to modify:";
    }

    public String printMaxNumEventPrompt_2(){
        return "Please input the new Maximum number of people for the activity :";
    }

    public String printNoEvent(){
        return "No activities created yet";
    }

    public String askForRequirementPrompt(){
        return "Please input whether Projector, Microphone and Party Audio System" +
                " \n is required for this event in order ('true' if yes, 'false' if no), separate by space: ";
    }

    public String printSuggestedRoomPrompt(List<String[]> suggestedList){
        String output = "This is the suggested room list that match all your requirements: \n";
        output = getString(suggestedList, output);
        return output;
    }

    private String getString(List<String[]> suggestedList, String output) {
        int i = 0;
        for (String[] roomInfo: suggestedList){
            String newInfo = "Room No. " + i + ": \n" + roomInfo[1] + "\n" + "eventID: "+ roomInfo[0];
            output += newInfo + "\n";
            i += 1;
        }
        return output;
    }
}
