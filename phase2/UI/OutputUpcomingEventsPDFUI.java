package UI;

import Controllers.OutputEventInfoController;
import Controllers.UserController;

import java.io.IOException;
/**
 * UI for producing pdf of upcoming event .
 */

public class OutputUpcomingEventsPDFUI extends AbstractUI {
    /**
     * An instance of <code>OutputConferenceInfoController</code> being instantiated.
     */
    private OutputEventInfoController pdf;
    /**
     * Instantiates new <code>OutputUpcomingEventsPDFUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OutputUpcomingEventsPDFUI(UserController userController) {
        super(userController);
        pdf = new OutputEventInfoController(userController);
    }

    /**
     * Outputs PDF that showing all upcoming events.
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
