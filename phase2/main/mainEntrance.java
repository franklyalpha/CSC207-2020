package main;

import UI.LoginUI;
import gateways.MongodbUser;

public class mainEntrance {
    public static void main(String[] args){
        // Mongodb
//        MongodbUser mongodbUser = new MongodbUser();
//        mongodbUser.startMongodbUser();

        // UI
        LoginUI login = new LoginUI();
        login.run();
    }

}