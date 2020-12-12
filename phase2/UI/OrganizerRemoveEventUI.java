package UI;

import Controllers.RemoveEventController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.NoEventsException;
import menuPresenter.RemoveEventPresenter;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

/**
 * UI for <code>Organizer</code> to remove an event.
 */
public class OrganizerRemoveEventUI extends AbstractUI {
    /**
     * an instance of <codeRemoveEventController</code>
     * being instantiated.
     */
    private final RemoveEventController removeEvent;


    /**
     * Instantiates new <code>OrganizerRemoveEventUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OrganizerRemoveEventUI(UserController userController) {
        super(userController);
        removeEvent = new RemoveEventController(userController);
    }

    /**
     * Lets <code>Organizer</code> choose the event they remove, and remove it.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
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
        throw new ExceedingMaxAttemptException("Exceeding maximum attempt times");
    }

    private void inputAndUpdateModification(RemoveEventPresenter presenter)
            throws NoEventsException, IllegalArgumentException {
        presenter.printIDForTheEventToBeCancelled(removeEvent.getAllActivities());
        Scanner input_1 = new Scanner(System.in);
        UUID actID = UUID.fromString(input_1.nextLine());
        removeEvent.cancelAndUpdate(actID);
    }
}

