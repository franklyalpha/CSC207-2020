package menuPresenter;

public class OrgRescheduleSpeakerPresenter extends UserPresenter {

    public OrgRescheduleSpeakerPresenter(){super();}

    public String strEventChangeSpeakerPrompt(){
        return strPromptHelper("the ID of the event for which you wish to change the speaker of: ");
    }

    public String strSpeakerAssignPrompt(){
        return "Please input the speaker you wish to assign: ";
    }

}
