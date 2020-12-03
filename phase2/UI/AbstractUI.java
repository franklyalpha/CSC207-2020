package UI;

import Controllers.SendPrivateMessageController;
import Controllers.UserController;
import functionalityPresenters.*;
import Controllers.OutputConferenceInfoController;
import globallyAccessible.UserNotFoundException;
import menuPresenter.UserPresenter;

import java.io.IOException;
import java.util.*;

//public abstract class controllers.UserController

/**
 * Controller for <code>User</code>-related functions, calling the appropriate methods.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class AbstractUI {
    protected UserController userController;
    final protected UserPresenter userPresenter = new UserPresenter();


    public AbstractUI(UserController userController){
        this.userController = userController;
    }

    /**
     * Abstract run method to be overridden.
     */
    public void run() {}



}
