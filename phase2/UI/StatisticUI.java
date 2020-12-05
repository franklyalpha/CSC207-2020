package UI;

import Controllers.UserController;
import functionalityPresenters.StatisticPresenter;

public class StatisticUI extends AbstractUI{
    private StatisticPresenter statistic;
    public StatisticUI(UserController userController) {
        super(userController);
        StatisticPresenter statistic = new StatisticPresenter(userController);
    }
     public void run(){
         System.out.println(statistic.popularEvent());

     }
}
