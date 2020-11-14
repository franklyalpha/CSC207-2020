package entities;

import java.time.*;
import java.util.*;

public class Activity implements java.io.Serializable {
    /*
        Variables include:
        String/Date(require importing) time;
        String speakerName;
        Int duration
        Arraylist<Integer> participantID
        entities.Chatroom CorrespondingChatroom

        Methods include:
        Getters and setters;
    */
    //private final ArrayList<String> speakers;
    private String speakers;
    private final ArrayList<String> attendants;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final UUID identity;
    private final UUID conferenceChat;
    private UUID conferenceRoomNum; //will adjust accordingly when they actually implements it.
    private String topic;
    //Maybe event tags for easier search?

    //TODO thinking of making multiple constructors later
    //basic constructor
    public Activity(LocalDateTime startTime, LocalDateTime endTime, UUID conferenceChat, UUID conferenceRoomNum,
                    String topic){
        this.speakers = null;
        this.attendants = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = Duration.between(startTime, endTime);
        this.conferenceChat = conferenceChat;
        this.identity = UUID.randomUUID();
        this.conferenceRoomNum = conferenceRoomNum;
        this.topic = topic;
        // consider order of creating activity and corresponding chatroom
    }

    public boolean AddAttendants(String attendant){
        if(!this.attendants.contains(attendant)){
            this.attendants.add(attendant);
            return true;
        }else{
            return false;
        }
    }

    public boolean addAttendants(ArrayList<String> attendants){
        boolean addedAll = true;
        for(String i: attendants){
            if(this.attendants.contains(i)) {
                this.attendants.add(i);
            }else{
                addedAll = false;
            }
        }
        return addedAll;
    }

    public boolean removeAttendant(String attendant){return this.attendants.remove(attendant);}

    public boolean addSpeakers(String speaker){
        this.speakers = speaker;
        return true;
    }

//    public boolean addSpeakers(ArrayList<String> speakers){
//        boolean addedAll = true;
//        for(String i: speakers){
//            if(this.speakers.contains(i)) {
//                this.speakers.add(i);
//            }else{
//                addedAll = false;
//            }
//        }
//        return addedAll;
//    }

//    public boolean removeSpeaker(String speaker){return this.speakers.remove(speaker);}

    public void changeTopic(String topic){this.topic = topic;}

    public void changeRoom(UUID conferenceRoomNum){this.conferenceRoomNum = conferenceRoomNum;}

    public boolean changeStartTime(LocalDateTime startTime){
        if(startTime.isAfter(this.endTime)){
            return false;
        }else{
            this.startTime = startTime;
            return true;
        }
    }

    public boolean changeEndTime(LocalDateTime endTime){
        if(endTime.isBefore(this.startTime)){
            return false;
        }else{
            this.endTime = endTime;
            return true;
        }
    }

    public void changeDuration(Duration duration){
        this.duration = duration;
        this.endTime = this.startTime.plus(duration);
    }

    public ArrayList<String> getAttendantsList(){return this.attendants;}

    public String getSpeakersList(){return this.speakers;}

    public LocalDateTime getStartTime(){return this.startTime;}

    public LocalDateTime getEndTime(){return this.endTime;}

    public Duration getDuration(){return this.duration;}

    public UUID getChatID(){return this.conferenceChat;}

    public UUID getIdentity(){return this.identity;}

    public UUID getConferenceRoomNum(){return this.conferenceRoomNum;}

    public String getTopic(){return this.topic;}

//    public String toString(){
//        String description = "Topic: " + this.topic + "\n" +
//                "Speakers: ";
//        for(String i: this.speakers){description += (i + " ");}
//        description += ("\nConference entities.Room " + this.conferenceRoomNum);
//        description += ("\nFrom " + this.startTime + " to " + this.endTime);
//        description += ("\nID: " + this.identity);
//        description += ("\nChat ID: " + this.conferenceChat);
//        return description;
//    }
public String toString(){
    String description = "Topic: " + this.topic + "\n" +
            "Speakers: ";
    description += speakers;
    description += ("\nConference entities.Room " + this.conferenceRoomNum);
    description += ("\nFrom " + this.startTime + " to " + this.endTime);
    description += ("\nID: " + this.identity);
    description += ("\nChat ID: " + this.conferenceChat);
    return description;
}

}
