package menuPresenter;

import java.util.ArrayList;

public class ModifyEventPresenter extends UserPresenter{

    public ModifyEventPresenter(){
        super();
    }

    public void printMaxNumEventPrompt_1(ArrayList<String[]> upcomingActivities){
        System.out.println("Here are the information of all the available activities: " + upcomingActivities);
        System.out.println("Please input the Activity Id you want to modify:");
    }

    public void printMaxNumEventPrompt_2(){
        System.out.println("Please input the new Maximum number of people for the activity :");
    }

    public void printNoEvent(){
        System.out.println("No activities created yet");
    }

}
