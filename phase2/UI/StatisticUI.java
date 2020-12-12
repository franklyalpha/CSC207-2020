package UI;

import Controllers.UserController;
import functionalityPresenters.StatisticPresenter;
/**
 * UI for presenting <code>Statistic<code/> calculation.
 */
public class StatisticUI extends AbstractUI{
    /**
     * An instance of <code>StatisticPresenter</code> being instantiated.
     */
    private StatisticPresenter statistic;
    /**
     * Instantiates new <code>StatisticUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public StatisticUI(UserController userController) {
        super(userController);
        statistic = new StatisticPresenter(userController);
    }

    /**
     * Shows the statistic result on the screen.
     */
     public void run(){
        if (statistic.popularEvent() == null){
            System.out.println(userPresenter.strNoEventsStats());
        } else {
            System.out.println(statistic.popularEvent());
        }
     }
}
