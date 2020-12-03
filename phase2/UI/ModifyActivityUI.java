package UI;

import Controllers.ModifyActivityController;
import Controllers.UserController;
import globallyAccessible.NoActivitiesException;
import menuPresenter.ModifyActivityPresenter;

import java.awt.desktop.AppReopenedEvent;
import java.util.Scanner;
import java.util.UUID;

public class ModifyActivityUI extends AbstractUI {
    public ModifyActivityUI(UserController userController) {
        super(userController);
    }

    @Override
    public void run() {
        ModifyActivityController modifyActivity = new ModifyActivityController(userController);
        ModifyActivityPresenter presenter = new ModifyActivityPresenter();
        try{
            inputAndUpdateModification(modifyActivity, presenter);
        }catch (NoActivitiesException e){
            presenter.printNoActivity();
        }
    }

    private void inputAndUpdateModification(ModifyActivityController modifyActivity, ModifyActivityPresenter presenter)
            throws NoActivitiesException {
        presenter.printMaxNumActivityPrompt_1(modifyActivity.getAllActivities());
        Scanner input_1 = new Scanner(System.in);
        presenter.printMaxNumActivityPrompt_2();
        Scanner input_2 = new Scanner(System.in);
        UUID activityId = UUID.fromString(input_1.nextLine());
        int newMaxNum = input_2.nextInt();
        modifyActivity.changeActivityMaxNumPeople(activityId, newMaxNum);
    }
}
