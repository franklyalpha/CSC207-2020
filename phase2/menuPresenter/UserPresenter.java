package menuPresenter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a UserPresenter.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class UserPresenter extends GeneralMenuPresenter {

    /**
     * Create a UserPresenter
     */
    public UserPresenter(){super();}

    public String strUserToContactPrompt(){
        return strPromptHelper("the username of the user you want to contact");
    }

    public String strMessagePrompt(){
        return strPromptHelper("the message you wish to send");
    }

    public String strInvalidEventID(){
        return strInvalidHelper("activity ID");
    }

    public String strInvalidSpeaker(){
        return strInvalidHelper("speaker");
    }

    public String strInvalidIndex(){
        return strInvalidHelper("index");
    }

    public String strInvalidEventType(){return strInvalidHelper("event type");}

    /**
     * @param availableAction ArrayList that contains all of the actions
     * @return The actions in string format
     */
    public String strAvailableActions(ArrayList<String> availableAction){
        StringBuilder action = new StringBuilder("  //-------------------------------------//\n" +
                " //          Available Actions:         //\n" +
                "//-------------------------------------//\n");
        for(String a: availableAction){
            action.append("[").append((availableAction.indexOf(a) + 1)).append("]").append(" ")
                    .append(a).append("\n");
        }
        return action.toString();
    }

    /**
     * @param schedule contains all of the schedule info in this ArrayList of arrays
     * @return all the schedules in string format
     */
    public String strSchedule(ArrayList<String[]> schedule){
        StringBuilder scheduleInfo = new StringBuilder();
        int j = 1;
        for(String[] i: schedule){
            scheduleInfo.append("[ Event No ] " + j + ": \n" +"[ Topic ] " + i[1] + ", \n[ Start Time ] " + i[2] + ", \n[ End Time ] " + i[3] + ", \n[ Room ] " +
                    i[4] + ", \n[ Speakers ]\n " + i[7] + "\nActivity ID: " + i[0] + "\n---------------------------------------------------------\n \n ");
            j += 1;
        }
        return scheduleInfo.toString();
    }

    public String strEnrolledMenuDes(){
        return "Here are activities you have enrolled: ";
    }

    public String strAllEventMenuDes(){
        return "Here are all the activities: ";
    }

    public String strContinueServicePrompt(){
        return "Do you want to perform another action? You will be logged out if not. [YES / NO] ";
    }

    /**
     * @param history messages stored in this ArrayList
     * @return the messages in string format
     */
    public String strDisplayMessageHistory(ArrayList<String> history){
        StringBuilder finalList = new StringBuilder();
        for(String message: history){
            finalList.append("[").append(history.indexOf(message)).append("] ").append(message).append("\n");
        }
        return finalList.toString();
    }

    /**
     * @param groupConversationId contains all of the info for groupchats
     * @return all of the group chats in String format
     */
    public String strDisplayChatGroups(ArrayList<String[]> groupConversationId){
        StringBuilder finalList = new StringBuilder();
        for(String[] info: groupConversationId){
            finalList.append(info[0]).append(": ").append(info[1]).append("\n");
        }
        return finalList.toString();
    }

    /**
     * @param startOrEnd helper functions to reduce redundancy
     * @return the prompt for time
     */
    public String strTimePrompt(String startOrEnd){
        return "Please input the year, month, day, hour, and minute of the " + startOrEnd + " time in number form (yyyy mm dd hh mm): ";
    }

    public String strStartTimePrompt(){
        return strTimePrompt("start");
    }

    public String strEndTimePrompt(){
        return strTimePrompt("end");
    }

    /**
     * @param list of options of menus
     * @return the menu options in string form
     */
    public String strList(Object[] list) {
        StringBuilder finalList = new StringBuilder();
        for(int i=0; i<=list.length-1; i++){
            finalList.append("[").append(i + 1).append("] ").append(list[i]).append("\n");
        }
        return finalList.toString();
    }

    /**
     * @param speakers contains all of the speakers
     * @return the speaker list
     */
    public String strSpeakerList(ArrayList<String> speakers){
        StringBuilder finalList = new StringBuilder();
        for(String speaker: speakers){
            finalList.append("[").append(speakers.indexOf(speaker)).append("] ").append(speaker).append("\n");
        }
        return finalList.toString();
    }

    /**
     * @param rooms contains UUID of the rooms
     * @return all rooms information in string form
     */
    public String strRoomList(ArrayList<UUID> rooms){
        StringBuilder finalList = new StringBuilder();
        for(UUID room: rooms){
            finalList.append("[").append(rooms.indexOf(room)).append("] ").append(room).append("\n");
        }
        return finalList.toString();
    }

    /**
     * Returns the messages from Messages from index num1 to num2 from the newest
     * @param Messages contains all the messages
     * @param num1 start of the messages
     * @param num2 end of the messages
     * @return the messages in String form
     */
    public String strMessagesInInterval(ArrayList<String> Messages, Integer num1, Integer num2){
        StringBuilder finalList = new StringBuilder();
        for (int i = (Messages.size() - num2); i <= (Messages.size() - num1); i++){
            finalList.append(Messages.get(i)).append("\n");
        }
        return finalList.toString();
    }

    public String strEventContactPrompt(){return "Please select an event by copying and pasting the topic: ";}

    public String strUserContactPrompt(){return "Please select a contact by copying and pasting the username: ";}

    public String strEventNull(){return "You are not in any events at the moment.\n ";}

    public String strHistoryChatNull(){return strEventNull() + "There are no announcements.\n ";}

    public String strNoEventsAvailable(){return "Sorry, there are no events available for registration at the moment. Please check back later.\n ";}

    public String strNoEventsStats(){return "Statistics could not be generated because there are no events yet.\n ";}

    public String strQuitPrompt(){return "[Q] - Go back";}
}
