package menuPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ModifyEventPresenter.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class ModifyEventPresenter extends UserPresenter{

    public ModifyEventPresenter(){
        super();
    }

    /**
     * @param upcomingActivities all upcoming activities info
     * @return the String
     */
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

    /**
     * @param suggestedList suggested list of rooms
     * @return the String list containing the recommendation
     */
    public String printSuggestedRoomPrompt(List<String[]> suggestedList){
        String output = "This is the suggested room list that match all your requirements: \n";
        output = getString(suggestedList, output);
        return output;
    }


    private String getString(List<String[]> suggestedList, String output) {
        int i = 0;
        StringBuilder outputBuilder = new StringBuilder(output);
        for (String[] roomInfo: suggestedList){
            String newInfo = "Room No. " + i + ": \n" + roomInfo[1] + "\n" + "RoomID: "+ roomInfo[0];
            outputBuilder.append(newInfo).append("\n");
            i += 1;
        }
        output = outputBuilder.toString();
        return output;
    }
}
