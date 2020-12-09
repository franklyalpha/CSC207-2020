package menuPresenter;

public class OrganizerPresenter extends UserPresenter {

    public OrganizerPresenter(){super();}

    public String strInvalidCapacity(){
        return strInvalidHelper("capacity");
    }

    public String strUserNamePrompt(){
        return strPromptHelper("the name of the user");
    }

    public String strUserTypePrompt(){
        return "Please enter number corresponding to the type of account you wish to create: \n(1) Organizer\n(2) Attendee \n(3) Speaker \n(4) Administrator";
    }

    public String strInvalidUserType(){
        return "Invalid input!!! Please try again and ensure you input the number corresponding to the type of account you wish to create.";
    }

    public String strCreateRoomPrompt(){
        return "Please input the room capacity";
    }

    public String strRoomCapacityConfirmation(int a){
        return "Successfully created a new room with a capacity of " + a + ".";
    }

    public String strNumOfMicrophone(){
        return "Please input the quantity (Arabian number) of Microphone this room has:";
    }

    public String strNumOfProjector(){
        return "Please input the quantity(Arabian number) of Projector this room has:";
    }

    public String strNumOfPartyAudioSystem(){
        return "Please input the quantity(Arabian number) of Party Audio System this room has:";
    }
}
