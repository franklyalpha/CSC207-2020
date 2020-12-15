package Controllers;

import java.util.ArrayList;

public class ActivityController extends AbstractController{


    public ActivityController(UserController userController) {
        super(userController);
    }

    /**
     * Helper function for getting the UUIDs of available activities.
     * @param available ArrayList of Strings representing all available activities.
     * @return Returns an ArrayList of Strings representing the UUIDs of all available activities.
     */
    protected ArrayList<String> extractActIDHelper (ArrayList<String[]> available){
        ArrayList<String> actIDs = new ArrayList<>();
        for (String[] schedule: available){
            actIDs.add(schedule[0]);
        }
        return actIDs;
    }


}
