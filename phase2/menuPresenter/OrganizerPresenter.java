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
        return "Please enter the type of user (integer) you want to create: \n1: organizer; 2: attendee; 3: speaker 4: administrator";
    }

    public String strInvalidUserType(){
        return "Invalid User type!!! Try again!";
    }

    public String strCreateRoomPrompt(){
        return "Please input the room capacity, whether or not the room has a projector (true or false), and number of available microphones\n" +
                "IN THAT ORDER, on the same line, and each separated by a space.";
    }

    public String strRoomCapacityConfirmation(int a){
        return "New room with capacity " + a + " is created successfully. ";
    }



}
