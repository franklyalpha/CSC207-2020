package menuPresenter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a OrganizerAddSchedulePresenter.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class OrganizerAddSchedulePresenter extends UserPresenter {

    public OrganizerAddSchedulePresenter(){super();}

    public String strInvalidTimePeriod(){
        return strInvalidHelper("time period");
    }

    public String strInvalidRoomIndex(){
        return strInvalidHelper("room index");
    }

    /**
     * @param rooms ArrayList of the UUID of rooms
     * @return the list in String format
     */
    public String strRoomPrompt(ArrayList<UUID> rooms){
        return "Here are the IDs of all available rooms: \n" + strRoomList(rooms) +
                "Please input the index of the room(if an invalid room is given, the first room will be used by default.):";
    }

    /**
     * @param speakers contains all of the speakers names
     * @return the list in String form with prompt
     */
    public String strSpeakerPrompt(ArrayList<String> speakers){
        return "Here are the names of all available speakers: \n" + strSpeakerList(speakers);
    }

    public String strMultiSpeakerPrompt(){
        return "Please input the names of speakers on different lines and, when you are done, input \"end\":";
    }

    public String strSingleSpeakerPrompt(){
        return "Please input the name of speaker:";
    }

    public String strTypePrompt(){
        return "Please enter one of the following number to choose the type of this event:\n" +
                "[1] Talk: One Speaker\n" +
                "[2] Panel: Multiple Speakers\n" +
                "[3] Party: No Speakers\n";
    }

    public String strTopicPrompt(){
        return "Please enter the topic for this event:";
    }

    public String strMaxNumPrompt(){
        return "Please enter the maximum capacity for this event:";
    }

    public String strRoomNumPrompt(){
        return "Please input the room number of which you wish to use: (e.g. No.1, then input '1')";
    }

    /**
     * @param speakers contains all speakers names
     * @param rooms contains all rooms IDs
     * @return returns the block of prompt and lists
     */
    public String strSpeakerRoomPrompt(ArrayList<String> speakers, ArrayList<UUID> rooms){
        String finalList = "Here are the names of all available speakers: \n" + strSpeakerList(speakers) +
                "Here are the IDs of all available rooms: \n" +
                strRoomList(rooms) +
                "Please input the topic, speaker, room index and maximum capacity for this activity\n" +
                "IN THAT ORDER and on different lines: (if an invalid room is given, the first room will be used by default.";
        return finalList;
    }

    public String strInvalidMaxNum(){return strInvalidHelper("max enrollment");}

}
