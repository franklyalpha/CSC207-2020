package main;

import UI.MainUI;
import gateways.MySQL;

public class mainEntrance {
    public static void main(String[] args){
        MySQL.init();
        MainUI mainUI = new MainUI();
        mainUI.run();
    }

}
