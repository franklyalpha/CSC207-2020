package menuPresenter;

import java.util.ArrayList;
import java.util.UUID;

public class UserPresenter extends GeneralMenuPresenter {

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

    public String strSchedule(ArrayList<String[]> schedule){
        StringBuilder scheduleInfo = new StringBuilder();
        for(String[] i: schedule){
            scheduleInfo.append("Topic: " + i[1] + ", \nStart Time: " + i[2] + ", \nEnd Time: " + i[3] + ", \nRoom: " +
                    i[4] + ", \nSpeakers: " + i[5] + ", \nActivity ID: " + i[0] + "\n\n");
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
        return "Do you wish to perform another action? Please enter 'true' or 'false' (false will log you out).";
    }
    //TODO change this so it match up the input pattern

    public String strDisplayMessageHistory(ArrayList<String> history){
        StringBuilder finalList = new StringBuilder();
        for(String message: history){
            finalList.append("[" + history.indexOf(message) + "] " + message + "\n");
        }
        return finalList.toString();
    }

    public String strDisplayChatGroups(ArrayList<String[]> groupConversationId){
        StringBuilder finalList = new StringBuilder();
        for(String[] info: groupConversationId){
            finalList.append(info[0] + ": " + info[1] + "\n");
        }
        return finalList.toString();
    }

    public String strTimePrompt(String startOrEnd){
        return "Please input the year, month, day, hour, and minute of the " + startOrEnd + " time (IN THAT ORDER, separated by a space): ";
    }

    public String strStartTimePrompt(){
        return strTimePrompt("start");
    }

    public String strEndTimePrompt(){
        return strTimePrompt("end");
    }

    public String strList(Object[] list) {
        StringBuilder finalList = new StringBuilder();
        for(int i=0; i<=list.length-1; i++){
            finalList.append("[" + (i+1)  + "] " + list[i] + "\n");
        }
        return finalList.toString();
    }

    public String strSpeakerList(ArrayList<String> speakers){
        StringBuilder finalList = new StringBuilder();
        for(String speaker: speakers){
            finalList.append("[" + speakers.indexOf(speaker) + "] " + speaker + "\n");
        }
        return finalList.toString();
    }

    public String strRoomList(ArrayList<UUID> rooms){
        StringBuilder finalList = new StringBuilder();
        for(UUID room: rooms){
            finalList.append("[" + rooms.indexOf(room) + "] " + room + "\n");
        }
        return finalList.toString();
    }

    public String strMessagesInInterval(ArrayList<String> Messages, Integer num1, Integer num2){
        StringBuilder finalList = new StringBuilder();
        for (int i = (Messages.size() - num2); i <= (Messages.size() - num1); i++){
            finalList.append(Messages.get(i) + "\n");
        }
        return finalList.toString();
    }

}
