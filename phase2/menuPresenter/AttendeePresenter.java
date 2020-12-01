package menuPresenter;

public class AttendeePresenter extends UserPresenter {

    public AttendeePresenter(){super();}

    public String strEnrollPrompt(){
        return strPromptHelper("the ID of the activity you want to enroll");
    }

    public String strCancelPrompt(){
        return strPromptHelper("the ID of the activity you want to cancel");
    }

    public String strEnrollMenuDes(){
        return "Here are available activities you can enroll: ";
    }
}
