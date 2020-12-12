package menuPresenter;

/**
 * Represents a OrganizerRescheduleSpeakerPresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class OrganizerRescheduleSpeakerPresenter extends UserPresenter {

    public OrganizerRescheduleSpeakerPresenter(){super();}

    public String strEventChangeSpeakerPrompt(){
        return strPromptHelper("the ID of the event for which you wish to change the speaker of: ");
    }

    /**
     * @param action the action to fill in the prompt
     * @return the prompt String
     */
    public String strSpeakerAssignPrompt(String action){
        return "Please input the speaker you wish to " + action + ": ";
    }

    public String strActivityTypePrompt(){
        return "please input the type of event (input the corresponding number): \n" +
                "[0]: talk; [1]: panel.";
    }

    public String strChooseAddDeleteSpeakerPrompt(){
        return "Please choose whether to: '[0] add' or '[1]: delete' speaker of a panel";
    }

}
