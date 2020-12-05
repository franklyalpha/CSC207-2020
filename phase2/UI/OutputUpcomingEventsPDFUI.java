package UI;

import Controllers.OutputConferenceInfoController;
import Controllers.UserController;

import java.io.IOException;

public class OutputUpcomingEventsPDFUI extends AbstractUI {
    private OutputConferenceInfoController pdf;

    public OutputUpcomingEventsPDFUI(UserController userController) {
        super(userController);
        pdf = new OutputConferenceInfoController(userController);
    }

    @Override
    public void run() {
        try{
            pdf.outputAllUpcomingEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
