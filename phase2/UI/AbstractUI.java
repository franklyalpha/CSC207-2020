package UI;

import Controllers.SendPrivateMessageController;
import Controllers.UserController;
import functionalityPresenters.*;
import Controllers.OutputConferenceInfoController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.UserNotFoundException;
import menuPresenter.UserPresenter;

import java.io.IOException;
import java.util.*;

//public abstract class controllers.UserController

/**
 *This is an abstract UI which will be extended for all other UIs.
 */
public class AbstractUI {
    /**
     * An instance of <code>UserController</code> being instantiated.
     */

    protected UserController userController;

    /**
     * An instance of <code>UserPresenter</code> being instantiated that used to present thing on screen.
     */
    final protected UserPresenter userPresenter = new UserPresenter();

    /**
     * Instantiates new <code>AbstractUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public AbstractUI(UserController userController){
        this.userController = userController;
    }

    /**
     * Abstract run method to be overridden.
     * @throws ExceedingMaxAttemptException when user exceed max attempt entering things.
     */
    public void run() throws ExceedingMaxAttemptException {}



}
