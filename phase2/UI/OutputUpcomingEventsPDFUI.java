package UI;

import Controllers.OutputConferenceInfoController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;

import java.io.IOException;
/**
 * UI for producing pdf of upcoming event .
 */

public class OutputUpcomingEventsPDFUI extends AbstractUI {
    /**
     * an instance of <code>OutputConferenceInfoController</code> being instantiated.
     */
    private OutputConferenceInfoController pdf;
    /**
     * Instantiates new <code>OutputUpcomingEventsPDFUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OutputUpcomingEventsPDFUI(UserController userController) {
        super(userController);
        pdf = new OutputConferenceInfoController(userController);
    }

    /**
     * Outputs PDF that showing all upcoming events.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() {
        try{
            pdf.outputAllUpcomingEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
