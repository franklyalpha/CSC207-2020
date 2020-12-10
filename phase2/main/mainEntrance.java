package main;

import UI.MainUI;
import gateways.MySQL;

public class mainEntrance {
    public static void main(String[] args){
        try {
            new MySQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainUI mainUI = new MainUI();
        mainUI.run();
    }

}
