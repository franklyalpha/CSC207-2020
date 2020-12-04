package menuPresenter;

public class SpeakerPresenter extends OrganizerPresenter {

    public SpeakerPresenter(){super();}

    public String strEventMessagePrompt(){
        return strPromptHelper("the index of the activity you wish to message");
    }

}
