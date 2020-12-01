package menuPresenter;

public class OrgRescheduleSpeakerPresenter extends UserPresenter {

    public OrgRescheduleSpeakerPresenter(){super();}

    public String strActivityChangeSpeakerPrompt(){
        return strPromptHelper("the ID of the activity to change the speaker");
    }

    public String strSpeakerAssignPrompt(){
        return "Please input the speaker you wish to assign: ";
    }

}
