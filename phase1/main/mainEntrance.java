package main;

import controllers.LoginController;

public class mainEntrance {
    public static void main(String[] args){
        LoginController login = new LoginController();
        login.run();
    }

}
