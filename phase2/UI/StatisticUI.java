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
        if (statistic.popularEvent() == null){
            System.out.println("Statistics could not be generated because there are no events yet.\n ");
        } else {
            System.out.println(statistic.popularEvent());
        }
     }
}
