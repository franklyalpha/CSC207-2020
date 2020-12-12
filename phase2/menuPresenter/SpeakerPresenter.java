package menuPresenter;

/**
 * Represents a SpeakerPresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class SpeakerPresenter extends OrganizerPresenter {

    public SpeakerPresenter(){super();}

    public String strEventMessagePrompt(){
        return strPromptHelper("the index of the event you wish to message: ");
    }

}
