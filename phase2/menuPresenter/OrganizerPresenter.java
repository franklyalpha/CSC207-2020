package menuPresenter;

public class OrganizerPresenter extends UserPresenter {

    public OrganizerPresenter(){super();}

    public String strInvalidCapacity(){
        return strInvalidHelper("capacity");
    }

    public String strSpeakerPrompt(){
        return strPromptHelper("the name of the speaker");
    }

}
