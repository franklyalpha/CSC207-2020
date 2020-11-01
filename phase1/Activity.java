import java.time.*;
import java.util.*;

public class Activity {
    /*
        Variables include:
        String/Date(require importing) time;
        String speakerName;
        Int duration
        Arraylist<Integer> participantID
        Chatroom CorrespondingChatroom

        Methods include:
        Getters and setters;
     */
    private final ArrayList<UUID> speakers;
    private final ArrayList<UUID> attendants;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final UUID conferenceChat; //TODO UUID or int? (im thinking of UUID)
    //TODO add conference room
    //TODO add topic

    //TODO Im thinking of making multiple constructors later
    //basic constructor
    public Activity(LocalDateTime startTime, LocalDateTime endTime, UUID conferenceChat){
        this.speakers = new ArrayList<UUID>();
        this.attendants = new ArrayList<UUID>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = Duration.between(startTime, endTime);
        this.conferenceChat = conferenceChat;
    }

    public boolean AddAttendants(UUID attendant){
        if(attendants.contains(attendant)){
            this.attendants.add(attendant);
            return true;
        }else{
            return false;
        }
    }

    //TODO add failsafe for all the rest
    public void addAttendants(ArrayList<UUID> attendants){this.attendants.addAll(attendants);}

    public void removeAttendants(UUID attendant){this.attendants.remove(attendant);}

    public void addSpeakers(UUID speaker){this.speakers.add(speaker);}

    public void addSpeakers(ArrayList<UUID> speakers){this.speakers.addAll(speakers);}

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

    public ArrayList<UUID> getAttendantsList(){return this.attendants;}

    public ArrayList<UUID> getSpeakersList(){return this.speakers;}

    public LocalDateTime getStartTime(){return this.startTime;}

    public LocalDateTime getEndTime(){return this.endTime;}

    public Duration getDuration(){return this.duration;}

    public UUID getChatID(){return this.conferenceChat;}

    //TODO add toString and ShortToString
    public String toString(){
        return "";
    }

}
