package menuPresenter;

import java.util.ArrayList;
import java.util.UUID;

public class OrganizerAddSchedulePresenter extends UserPresenter {

    public OrganizerAddSchedulePresenter(){super();}

    public String strInvalidTimePeriod(){
        return strInvalidHelper("time period");
    }

    public String strInvalidRoomIndex(){
        return strInvalidHelper("room index");
    }

    public String strRoomPrompt(ArrayList<UUID> rooms){
        String finalList = "Here are the IDs of all available rooms: \n" + strRoomList(rooms) +
                "Please input the index of the room(if an invalid room is given, the first room will be used by default.):";
        return finalList;
    }

    public String strSpeakerPrompt(ArrayList<String> speakers){
        StringBuilder finalList = new StringBuilder("Here are the names of all available speakers: \n");
        finalList.append(strSpeakerList(speakers));
        return finalList.toString();
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

    public String strSpeakerRoomPrompt(ArrayList<String> speakers, ArrayList<UUID> rooms){
        StringBuilder finalList = new StringBuilder("Here are the names of all available speakers: \n");
        finalList.append(strSpeakerList(speakers));
        finalList.append("Here are the IDs of all available rooms: \n");
        finalList.append(strRoomList(rooms));
        finalList.append("Please input the topic, speaker, room index and maximum capacity for this activity\n" +
                "IN THAT ORDER and on different lines: (if an invalid room is given, the first room will be used by default.");
        return finalList.toString();
    }

    public String strInvalidMaxNum(){return strInvalidHelper("max enrollment");}

}
