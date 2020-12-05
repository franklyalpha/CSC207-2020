package menuPresenter;

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
