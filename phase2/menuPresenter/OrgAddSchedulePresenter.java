package menuPresenter;

import java.util.ArrayList;
import java.util.UUID;

public class OrgAddSchedulePresenter extends UserPresenter {

    public OrgAddSchedulePresenter(){super();}

    public String strInvalidTimePeriod(){
        return strInvalidHelper("time period");
    }

    public String strInvalidRoomIndex(){
        return strInvalidHelper("room index");
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

}
