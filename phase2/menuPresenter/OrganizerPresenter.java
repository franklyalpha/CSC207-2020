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
        return "Please enter the type of user (integer) you want to create: \n1: organizer; 2: attendee; 3: speaker";
    }

    public String strInvalidUserType(){
        return "Invalid User type!!! Try again!";
    }

    public String strCreateRoomPrompt(){
        return "Please input the capacity of room, has projector or not (true if have) and number of microphones\n" +
                "IN THAT ORDER and on the same line, separate by space";
    }

    public String strRoomCapacityConfirmation(int a){
        return "New room with capacity " + a + "is created successfully. ";
    }



}
