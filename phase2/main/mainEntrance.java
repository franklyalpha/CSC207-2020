package main;

import UI.MainUI;
import gateways.SQLServer;

public class mainEntrance {
    public static void main(String[] args){
        SQLServer.init();
        MainUI mainUI = new MainUI();
        mainUI.run();
    }

}
