package Presenters;
import java.util.*;

import java.util.ArrayList;

/**
 * Presenter in charge of outputting menu options to the user.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class Presenter {
    /*
    Present the menu options to the user
    For now it is text only, in phase 2 will upgrade to an UI
    Need to remove println lines from the controllers and replace them with methods in this class
     */

    //Universal presenters

    /**
     * Prints out invalid messages for inputs. 'obj' is the specific type of invalidity of input received.
     * @param obj the specific invalidity of input being received in <code>String</code>.
     */
    public static void printInvalid(String obj){
        System.out.println("Invalid " + obj + "!!!");
    }

    //LoginController presenters ***************************************************************************************

    /**
     * Prints out the instruction to ask for operations on sign up, login or quit the program.
     */
    public static void printLoginMenu(){
        System.out.println("What would you like to do?\n[0] Sign Up\n[1] Log in\n[Q] Quit");
    }

    /**
     * Prints out the instruction to ask the user for passwords.
     */
    public static void printPasswordPrompt(){
        System.out.println("Enter Password:");
    }

    /**
     * Prints out the message that a new type of user has been created.
     * @param userType the type of user in <code>String</code>; possible types include 'organizer', 'attendee' and so on.
     */
    public static void printNewUserCreated(String userType){
        System.out.println("New " + userType + " account created!");
    }

    /**
     * Prints out instructions to ask the user whether to continue after completing a task or
     * when a task is unexpectedly terminated.
     */
    public static void printWrongInputMenu(){
        System.out.println("Would you like to continue? (type \"(Y)es\" or \"(N)o\")");
    }

    /**
     * Prints out instructions to ask the user the usertype the user wants to sign up for.
     */
    public static void printSighUpMenu(){
        System.out.println("Please enter the user-type you wish to sign up as: [0] Organizer [1] Attendee");
    }

    /**
     * Prints out instructions to ask the user to state user-type.
     */
    public static void printHandleLoginMenu(){
        System.out.println("Please enter your user-type: [0] Organizer [1] Speaker [2] Attendee");
    }

    /**
     * Prints out instruction to ask the user to input a unique username.
     */
    public static void printUsernamePrompt(){
        System.out.println("Please enter your username (NOTE: Your username is different from your ACTUAL name):");
    }

    /**
     * Prints out instruction to ask the user to enter their name.
     */
    public static void printEnterName(){
        System.out.println("Please enter your name:");
    }

    /**
     * Prints out text confirming the chosen username.
     * @param username String representing the username.
     */
    public static void printUsernameIs(String username){
        System.out.println("Your username is: " + username +
                ". You will use this to log in, so remember it!");
    }

    //UserController presenters ****************************************************************************************

    /**
     * Prints out instruction to ask the user to enter the username of the person they wish to contact.
     */
    public static void printUserToContactPrompt(){
        System.out.println("Please input the username of the person you wish to contact: ");
    }

    /**
     * Prints out each item in the input list.
     * @param list The input list to be printed.
     */
    public static void printList(Object[] list){
        for(Object i: list){
            System.out.println(i);
        }
    }

    //Prints the last 10 messages, the view message method in UserController need to prompt the user to choose a specific private user/group to pass the ArrayList of messages to this method
    //Also might want to implement User tag and time stamps

    /**
     * Prints messages from a chatHistory within the specified interval.
     * @param Messages ArrayList representing the chatHistory from which to retrieve messages.
     * @param num1 Integer representing the most recent message to output.
     * @param num2 Integer representing the oldest message to output.
     */
    public static void printMessagesInInterval(ArrayList<String> Messages, Integer num1, Integer num2){
        for (int i = (Messages.size() - num2); i <= (Messages.size() - num1); i++){
            System.out.println(Messages.get(i));
        }
    }

    /**
     * Prints out instruction to ask user to select item.
     * @param item String representing item to ask user to select.
     */
    public static void printContactPrompt(String item){
        System.out.println("Please select a " + item + " by copying and pasting the line: ");
    }

    /**
     * Prints out instruction to ask user what type of user they are trying to contact.
     */
    public static void printTypeToContactPrompt(){
        System.out.println("Please input the type of the user you wish to contact (organizer; speaker; attendee): ");
    }

    //Shared between Attendee, Organizer, and Speaker Controllers
    /**
     * Prints out a list of actions that can be currently be performed.
     * @param availableAction ArrayList of Strings representing the list of actions that can be performed.
     */
    public static void printAvailableActions(ArrayList<String> availableAction){
        System.out.println("\n  //-------------------------------------//\n" + " //          Available Actions:         //" +
                "\n//-------------------------------------//\n");
        for(String a: availableAction){
            System.out.println(availableAction.indexOf(a)+1 + " " + a);
        }
    }

    /**
     * Prints out formatted information of activities.
     *
     * @param schedule An array list of <code>String</code> array containing: the UUID of activity, the topic,
     *                 the start time, end time of activity, the assigned room UUID and assigned speaker's name
     *                 of this activity.
     */
    public static void printSchedule(ArrayList<String[]> schedule){
        for(String[] i: schedule){
            System.out.println("Topic: " + i[1] + ", \nStart Time: " + i[2] + ", \nEnd Time: " + i[3] + ", \nRoom: " + i[4]
                                + ", \nSpeakers: " + i[5] + ", \nActivity ID: " + i[0] + "\n\n");
        }
    }

    /**
     * Prints out instruction to ask user to input their message.
     */
    public static void printMessagePrompt(){
        System.out.println("Please input the message you wish to send: ");
    }

    //AttendeeController Presenters ***********************************************************************************

    /**
     * Prints out the list of results.
     * @param result ArrayList of Strings representing the list of results to be printed.
     */
    public static void printResults(ArrayList<String> result){
        System.out.println(result);
    }

    /**
     * Prints out instruction asking user if they wish to continue or not.
     */
    public static void printContinuePrompt(){
        System.out.println("Do you wish to continue and use other services? (true or false; false will log you out");
    }

    /**
     * Prints out instruction asking user to input the activity ID to enroll in or withdraw from.
     * @param des String representing the action to be performed with the activity ID.
     */
    public static void printActivityIDPrompt(String des){
        System.out.println("Please input the ID of the activity that you wish to " + des + ": ");
    }

    /**
     * Prints out a description for items being displayed.
     * @param des String representing the items to be displayed.
     */
    public static void printDescription(String des){
        System.out.println("Here are " + des + ": ");
    }

    //OrganizerController presenter ************************************************************************************

    /**
     * Prints out instruction asking user for the input of a time.
     * @param StartorEnd String representing whether a start time or end time is being asked for.
     */
    public static void printTimePrompt(String StartorEnd){
        System.out.println("Please input the year, month, day, hour, and minute of the " + StartorEnd + " time (IN THAT ORDER, separate by space): ");
    }

    /**
     * Prints out the list of available speakers.
     * @param freeSpeakers ArrayList of Strings representing speakers that are available.
     */
    public static void printSpeakers(ArrayList<String> freeSpeakers){
        System.out.println("Here are the available speakers:");
        for(String i: freeSpeakers){
            System.out.println(i);
        }
    }

    /**
     * Prints out a list of available speakers and rooms, as well as instructions asking the user to select a topic, speaker, and room for an activity.
     * @param freeSpeaker ArrayList of Strings representing all available speakers.
     * @param freeRooms ArrayList of Strings representing all available rooms.
     */
    public static void printSpeakerRoomPrompt(ArrayList<String> freeSpeaker, ArrayList<UUID> freeRooms){
        System.out.println("Here are the names of all available speakers: " + freeSpeaker +
                "\n Here are the IDs of all available rooms: " + "" + freeRooms);
        System.out.println("Please input the topic, speaker, and room number for this activity (i.e., input 1 for the first room, 2 for the second)" +
                " IN THAT ORDER and on different lines: (if an invalid room is given, the first room will be used by default.");
    }

    /**
     * Prints out instruction asking user to enter the new capacity of the room.
     */
    public static void printRoomCapacityPrompt(){
        System.out.println("Please enter the this room's maximum capacity:");
    }

    /**
     * Prints out a statement confirming a room's new maximum capacity.
     * @param a Integer representing the room's new maximum capacity.
     */
    public static void printRoomCapacityConfirmation(Integer a){
        System.out.println("This room's new capacity is " + a + ".");
    }

    /**
     * Print out instructions to ask user entering the username of speaker;
     */
    public static void printSpeakerNamePrompt(){
        System.out.println("Enter the name of this speaker: ");
    }

    /**
     * Print out message that there is a speaker with same username as user's input.
     */
    public static void printSpeakerExist(){
        System.out.println("This speaker already exists.");
    }

    /**
     * Print out instruction to ask the user the activity UUID, in whom the user wants to reassign another speaker.
     */
    public static void printChangeSpeakerIDPrompt(){
        System.out.println("Please input the ID of the activity for which you wish to change the speaker of: ");
    }

    /**
     * Print out instruction to ask the user input username of speaker, in whom the user wants to assign.
     */
    public static void printSpeakerAssignPrompt(){
        System.out.println("Please input the speaker you wish to assign: ");
    }

    /**
     * Print out instruction to ask user whether to do other actions within this account or logout.
     */
    public static void printContinueServicePrompt(){
        System.out.println("Do you wish to perform another action? Please enter 'true' or 'false' (false will log you out).");
    }

    //SpeakerController ************************************************************************************************

    /**
     * Print out information of UUIDs for activities in which the user has enrolled/assigned.
     *
     * @param infoIDs An array list storing UUIDs of activities in which the user is enrolled/assigned.
     */
    public static void printInfoIDs(ArrayList<String> infoIDs){
        System.out.println("Here are the activities you've been assigned: " + infoIDs);
    }

    /**
     * Print out instruction to ask the user which activity's enrolled users should receive the
     * message the user is about to send.
     */
    public static void printActivityMessagePrompt(){
        System.out.println("Please input the i-th activity to which you wish to send " +
                "a message to (i.e., type 1 to send a message to the first in the list):");
    }

    /**
     * Print out the instruction to ask whether to logout.
     */
    public static void printLogoutPrompt(){
        System.out.println("Do you want to logout?");
    }

    /**
     * Print out the message that the user is not enrolled/assigned to any activities.
     */
    public static void printNotEnrolled(){
        System.out.println("You are not enrolled in any activities. ");
    }
}
