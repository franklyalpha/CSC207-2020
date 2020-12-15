package UI;

import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.UserPresenter;


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
