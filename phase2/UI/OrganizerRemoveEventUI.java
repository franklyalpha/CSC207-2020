package UI;

import Controllers.RemoveEventController;
import Controllers.UserController;
import globallyAccessible.NoEventsException;
import menuPresenter.RemoveEventPresenter;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;


public class OrganizerRemoveEventUI extends AbstractUI {
    private final RemoveEventController removeEvent;

    public OrganizerRemoveEventUI(UserController userController) {
        super(userController);
        removeEvent = new RemoveEventController(userController);
    }

    @Override
    public void run() {
        for(int i = 0; i < 3; i++){
            RemoveEventPresenter presenter = new RemoveEventPresenter();
            try{
                inputAndUpdateModification(presenter);
                break;
            }catch (NoEventsException e){
                System.out.println(presenter.printNoEvent());
            }catch(IllegalArgumentException e2){
                System.out.println(presenter.strInvalidInput());
            }
        }
    }

    private void inputAndUpdateModification(RemoveEventPresenter presenter)
            throws NoEventsException, IllegalArgumentException {
        presenter.printIDForTheEventToBeCancelled(removeEvent.getAllActivities());
        Scanner input_1 = new Scanner(System.in);
        UUID actID = UUID.fromString(input_1.nextLine());
        removeEvent.cancelAndUpdate(actID);
    }
}

