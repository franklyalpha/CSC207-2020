package UI;

import Controllers.UserController;
import functionalityPresenters.StatisticPresenter;

public class StatisticUI extends AbstractUI{
    private StatisticPresenter statistic;
    public StatisticUI(UserController userController) {
        super(userController);
        statistic = new StatisticPresenter(userController);
    }
     public void run(){
        if (statistic.popularEvent() == null){
            System.out.println(userPresenter.strNoEventsStats());
        } else {
            System.out.println(statistic.popularEvent());
        }
     }
}
