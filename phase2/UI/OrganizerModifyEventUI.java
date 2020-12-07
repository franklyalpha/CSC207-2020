package UI;

import Controllers.ModifyEventController;
import Controllers.UserController;
import globallyAccessible.NoEventsException;
import menuPresenter.ModifyEventPresenter;

import java.util.Scanner;
import java.util.UUID;

public class OrganizerModifyEventUI extends AbstractUI {
    private ModifyEventController modifyEvent;

    public OrganizerModifyEventUI(UserController userController) {
        super(userController);
        modifyEvent = new ModifyEventController(userController);
    }

    @Override
    public void run() {
        ModifyEventPresenter presenter = new ModifyEventPresenter();
        try{
            inputAndUpdateModification(modifyEvent, presenter);
        }catch (NoEventsException e){
            presenter.printNoEvent();
        }
    }

    private void inputAndUpdateModification(ModifyEventController modifyActivity, ModifyEventPresenter presenter)
            throws NoEventsException {
        presenter.printMaxNumEventPrompt_1(modifyActivity.getAllActivities());
        Scanner input_1 = new Scanner(System.in);
        UUID activityId = UUID.fromString(input_1.nextLine());
        presenter.printMaxNumEventPrompt_2();
        int newMaxNum = input_1.nextInt();
        modifyActivity.changeEventMaxNumPeople(activityId, newMaxNum);
    }
}
