package Controllers;

import globallyAccessible.RequestNotFoundException;
import useCases.RequestManager;
import java.util.ArrayList;
import java.util.UUID;

public class RequestController extends AbstractController {

    /**
     * Constructor for an instance of <code>RequestController</code>
     * @param userController Instance of <code>UserController</code> to use the superconstructor.
     */
    public RequestController(UserController userController) {
        super(userController);
    }

}

