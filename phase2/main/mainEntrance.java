package main;

import UI.MainUI;
//import gateways.Mongodb;

public class mainEntrance {
    public static void main(String[] args){
//        Mongodb mongodb = new Mongodb();
//        MainUI mainUI = new MainUI(mongodb.database);
        MainUI  mainUI = new MainUI();
        mainUI.run();
    }

}
