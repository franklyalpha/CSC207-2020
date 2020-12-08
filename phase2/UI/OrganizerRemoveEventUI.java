package UI;

import Controllers.RemoveEventController;
import Controllers.UserController;
import globallyAccessible.NoEventsException;
import menuPresenter.RemoveEventPresenter;

import java.util.Scanner;
import java.util.UUID;

package useCases;

public class OrganizerModifyEventUI extends AbstractUI {
    private final RemoveEventController removeEvent;

    public OrganizerModifyEventUI(UserController userController) {
        super(userController);
        removeEvent = new RemoveEventController(userController);
    }

    @Override
    public void run() {
        RemoveEventPresenter presenter = new RemoveEventPresenter();
        try{
            inputAndUpdateModification(presenter);
        }catch (NoEventsException e){
            presenter.printNoEvent();
        }
    }

    private void inputAndUpdateModification(RemoveEventPresenter presenter)
            throws NoEventsException {
        presenter.printIDForTheEventToBeCancelled(removeEvent.getAllActivities());
        Scanner input_1 = new Scanner(System.in);
        UUID.fromString(input_1.nextLine());
        removeEvent.cancelEvent();
    }
}

