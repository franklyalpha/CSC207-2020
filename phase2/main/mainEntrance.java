package main;

import UI.MainUI;
import gateways.SQLServer;
import globallyAccessible.ExceedingMaxAttemptException;

public class mainEntrance {
    public static void main(String[] args) throws ExceedingMaxAttemptException {
        SQLServer.init();
        MainUI mainUI = new MainUI();
        mainUI.run();
    }

}
