package useCases;

import entities.User;

public class AdminManager extends UserManager {
    public AdminManager(UserManager userManager){
        super(userManager);
    }

    public void deleteContact(String deleter, String deleted){
        User targetedUser = findUser(deleter);
        assert targetedUser != null;
        targetedUser.getChatroom().remove(deleted);
    }
}
