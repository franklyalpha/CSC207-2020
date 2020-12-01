package menuPresenter;

import java.util.ArrayList;

public class ModifyActivityPresenter extends UserPresenter{

    public ModifyActivityPresenter(){
        super();
    }

    public static void printMaxNumActivityPrompt_1(ArrayList<String[]> upcomingActivities){
        System.out.println("Here are the information of all the available activities: " + upcomingActivities);
        System.out.println("Please input the Activity Id you want to modify:");
    }

    public static void printMaxNumActivityPrompt_2(){
        System.out.println("Please input the new Maximum number of people for the activity :");
    }

    public static void printNoActivity(){
        System.out.println("No activities created yet");
    }

}
