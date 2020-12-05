package menuPresenter;

public class SpeakerPresenter extends OrganizerPresenter {

    public SpeakerPresenter(){super();}

    public String strEventMessagePrompt(){
        return strPromptHelper("the index of the event you wish to message: ");
    }

}
