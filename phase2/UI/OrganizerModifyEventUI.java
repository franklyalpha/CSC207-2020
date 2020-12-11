package UI;

import Controllers.ModifyEventController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.MaxNumberBeyondRoomCapacityException;
import globallyAccessible.NoEventsException;
import menuPresenter.ModifyEventPresenter;

import java.util.Scanner;
import java.util.UUID;
/**
 * UI for <code>Organizer</code> to modify events including changing max mum people in certain event.
 */
public class OrganizerModifyEventUI extends AbstractUI {
    /**
     * @param modifyEvent: an instance of <code>ModifyEventController</code> being instantiated.
     */
    private ModifyEventController modifyEvent;

    /**
     * Instantiates new <code>OrganizerCreateUserUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OrganizerModifyEventUI(UserController userController) {
        super(userController);
        modifyEvent = new ModifyEventController(userController);
    }

    /**
     * Lets <code>Organizer</code> choose the event wants to modify and modify the event.
     * Will display instructions if the input event ID or max number is invalid.
     */
    @Override
    public void run() {
        ModifyEventPresenter presenter = new ModifyEventPresenter();
        try{
            inputAndUpdateModification(modifyEvent, presenter);
        }catch (NoEventsException e1){
            System.out.println(presenter.printNoEvent());
        }catch(MaxNumberBeyondRoomCapacityException e2){
            System.out.println("Invalid max number!!!");
        } catch (Exception e3){
            System.out.println(presenter.strInvalidInput());
        }
    }

    private void inputAndUpdateModification(ModifyEventController modifyActivity, ModifyEventPresenter presenter)
            throws NoEventsException, IllegalArgumentException, MaxNumberBeyondRoomCapacityException {
        System.out.println(presenter.printMaxNumEventPrompt_1(modifyActivity.getAllActivities()));
        Scanner input_1 = new Scanner(System.in);
        UUID activityId = UUID.fromString(input_1.nextLine());
        System.out.println(presenter.printMaxNumEventPrompt_2());
        int newMaxNum = input_1.nextInt();
        modifyActivity.changeEventMaxNumPeople(activityId, newMaxNum);
    }
}
