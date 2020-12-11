package menuPresenter;

/**
 * Represents a AttendeePresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class AttendeePresenter extends UserPresenter {

    public AttendeePresenter(){super();}

    public String strEnrollPrompt(){
        return strPromptHelper("the ID of the activity you want to enroll in");
    }

    public String strCancelPrompt(){
        return strPromptHelper("the ID of the activity you want to cancel enrollment for");
    }

    public String strEnrollMenuDes(){
        return "Here are activities available for enrollment: ";
    }
}
