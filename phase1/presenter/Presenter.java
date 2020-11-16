package presenter;
import java.util.*;

import controllers.OrganizerController;

import java.util.ArrayList;

public class Presenter {
    /*
    Present the menu options to the user
    For now it is text only, in phase 2 will upgrade to an UI
    Need to remove println lines from the controllers and replace them with methods in this class
     */

    //Universal presenters
    public static void printInvalid(String obj){
        System.out.println("Invalid " + obj + "!!!");
    }
    /*Replace:
    -System.out.println("invalid input! try again; \n");
    -System.out.println("Invalid password or Username");
    -System.out.println("Invalid username or usertype! Try again later!");
    -System.out.println("invalid action.");
    -System.out.println("Invalid activity ID.");
    -System.out.println("Invalid time period! Please reconsider another time!!!");
    -System.out.println("Invalid capacity.");
    -System.out.println("invalid activity ID! try again later");
    -System.out.println("invalid speaker! try again later");
    -System.out.println("invalid input!!! try again later. ");
    -System.out.println("Wrong input!!! Try again later. ");
    -System.out.println("Wrong password!!!");
    -System.out.println("Username not found!!!");
    -System.out.println("Wrong user type!!!\n");
    input, activity ID, username or password/usertype, action, time period, capacity, speaker, password, username
    */

    //LoginController presenters
    public static void printLoginMenu(){
        System.out.println("Enter your choice:\n[0] SignUp\n[1] Login\n[Q] uit");
    }

    public static void printPasswordPrompt(){
        System.out.println("Enter Password:");
    }
    /* Replace:
    System.out.println("Please enter your password:");
    System.out.println("Enter Password:");
    System.out.println("Enter the password of this Speaker");
    */

    public static void printNewUserCreated(String userType){
        System.out.println("New " + userType + " Created!");
    }
    /* Replace:
    System.out.println("New Organizer Created!");
    System.out.println("New Attendant Created!");
    Organizer, Attendant
     */

    public static void printWrongInputMenu(){
        System.out.println("Continue running or not? (type \"(Y)es\" or \"(N)o\")");
    }

    public static void printSighUpMenu(){
        System.out.println("Enter the usertype you want to sign up: [0] Organizer [1] Attendant");
    }

    public static void printHandleLoginMenu(){
        System.out.println("Please enter your usertype [0] Organizer [1] Speaker [2] Attendant");
    }

    public static void printUsernamePrompt(){
        System.out.println("Please enter your username (NOTE: Your username is different from your signup name):");
    }

    public static void printEnterName(){
        System.out.println("Enter your name:");
    }

    public static void printUsernameIs(String username){
        System.out.println("Your username is " + username);
    }

    //UserController presenters
    public static void printUserToContactPrompt(){
        System.out.println("please input the username of the person you wish to contact");
    }

    //side note: this kinda sounds redundant
    public static void printList(Object[] list){
        for(Object i: list){
            System.out.println(i);
        }
    }

    //Prints the last 10 messages, the view message method in UserController need to prompt the user to choose a specific private user/group to pass the ArrayList of messages to this method
    //Also might want to implement User tag and time stamps
    public static void printMessagesInInterval(ArrayList<String> Messages, Integer num1, Integer num2){
        for(int i=Messages.size()-num1; i>=Messages.size()-num2; i--){
            System.out.println(Messages.get(i));
        }
    }

    public static void printContactPrompt(String item){
        System.out.println("Please select a " + item + ": (just copy and paste the line)");
    }

    public static void printTypeToContactPrompt(){
        System.out.println("please input the type of this user, either organizer, speaker or attendant: ");
    }

    //Shared between Attendant, Organizer, and Speaker Controllers too
    public static void printAvailableActions(ArrayList<String> availableAction){
        System.out.println("Services apply\n");
        for(String a: availableAction){
            System.out.println(availableAction.indexOf(a)+1 + " " + a);
        }
    }

    public static void printSchedule(ArrayList<String[]> schedule){
        for(String[] i: schedule){
            System.out.println("Topic: " + i[1] + ", \nStart Time: " + i[2] + ", \nEnd Time: " + i[3] + ", \nRoom: " + i[4]
                                + ", \nSpeakers: " + i[5] + ", \nActivityID: " + i[0] + "\n\n");
        }
    }

    public static void printMessagePrompt(){
        System.out.println("Please input message: ");
    }
    /* Replace:
    System.out.println("please input the message you wanna send:");
    System.out.println("Please input your message below: ");
    System.out.println("please input message: ");
    System.out.println("Please input the message you wish to send:");
    */

    //AttendantController presenters
    public static void printResults(ArrayList<String> result){
        System.out.println(result);
    }

    public static void printContinuePrompt(){
        System.out.println("Continue for other services? Please enter true or false. (false for log out)");
    }

    public static void printActivityIDPrompt(String des){
        System.out.println("please input the activity's ID you wish to " + des + ": ");
    }
    /* Replace:
    System.out.println("please input the activity's ID you wish to enroll");
    System.out.println("please input the activity's ID you wish to cancel");
    enroll, cancel
    */

    public static void printDescription(String des){
        System.out.println("here are " + des + ": ");
    }
    /* Replace:
    System.out.println("here are activities you can enroll: " + actIDs);
    System.out.println("here are activities you've enrolled: " + actIDs);
    System.out.println("here are all activity IDs: " + actIDs);
    System.out.println("here are available speakers : "+ freeSpeakers);
    activities you can enroll, activities you've enrolled, all activity IDs, available speakers
    */

    //OrganizerController presenter
    public static void printTimePrompt(String StartorEnd){
        System.out.println("Please input year, month, day, hour, minute of " + StartorEnd + " time IN ORDER: ");
    }
    /* Replace:
    System.out.println("Please input year, month, day, hour, minute of start time IN ORDER: ");
    System.out.println("Please input year, month, day, hour, minute of end time IN ORDER: ");
    start, end
    */

    public static void printSpeakers(ArrayList<String> freeSpeakers){
        System.out.println("here are the available speakers:");
        for(String i: freeSpeakers){
            System.out.println(i);
        }
    }

    public static void printSpeakerRoomPrompt(ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms){
        System.out.println("Here are available speaker names: " + freeSpeaker + "\n Here are available rooms ID: " +
                "" + freeRooms);
        System.out.println("Please input topic, speaker and ith room (e.g, 1st room: input 1) for this activity" +
                " IN ORDER and in different lines: (if there are invalid inputs, will use the first one as default)");
    }

    public static void printRoomCapacityPrompt(){
        System.out.println("Enter the capacity of this room");
    }

    public static void printRoomCapacityConfirmation(Integer a){
        System.out.println("This new room capacity is " + a);
    }

    public static void printSpeakerNamePrompt(){
        System.out.println("Enter the name of this Speaker");
    }

    public static void printSpeakerExist(){
        System.out.println("The speaker is already exist.");
    }

    public static void printChangeSpeakerIDPrompt(){
        System.out.println("Please input the ID of activity you wish to change speaker: ");
    }

    public static void printSpeakerAssignPrompt(){
        System.out.println("Please input the speaker you wish to assign");
    }

    public static void printContinueServicePrompt(){
        System.out.println("Continue for other services? Please enter true or false. (false for log out)");
    }

    //SpeakerController
    public static void printInfoIDs(ArrayList<String> infoIDs){
        System.out.println("here are activities you've been assigned: " + infoIDs);
    }

    public static void printActivityMessagePrompt(){
        System.out.println("please input the ith activity you wish to send " +
                "a message (e.g: the 1st in the list, then type 1):");
    }

    //Modify this one before merging
    public static void printSpeakerActions(){
        System.out.println("Actions available: 1: view private message; 2: send private message; 3: " +
                "view coop message; 4: send coop message; 5: send activity message; 6: view enrolled schedules" +
                "; 7: view activity message; ");
    }

    public static void printLogoutPrompt(){
        System.out.println("logout?");
    }

    public static void printNotEnrolled(){
        System.out.println("You are not enrolled in any activities. ");
    }
}
