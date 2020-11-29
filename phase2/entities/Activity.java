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
    private String speaker;
    private final ArrayList<String> attendeeList;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final UUID identity;
    private final UUID conferenceChat;
    private UUID conferenceRoomNum; //will adjust accordingly when they actually implements it.
    private String topic;
    private Integer maxNumAttendee;
    //Maybe event tags for easier search?

    //TODO：thinking of making multiple constructors later
    //basic constructor
    /**
     * Creates <code>Activity</code> with specified start time, end time, conference chat id, conference room number
     * and topic.
     * @param period LocalDateTime of start & end time in <code>Activity</code>.
     * @param chatRoomID UUID of assigned chatroom ID and room ID <code>Activity</code>.
     * @param topic the topic of the activity in <code>Activity</code>.
     */
    public Activity(LocalDateTime[] period, UUID[] chatRoomID,
                    String topic){
        this.speaker = null;
        this.attendeeList = new ArrayList<>();
        this.startTime = period[0];
        this.endTime = period[1];
        this.duration = Duration.between(startTime, endTime);
        this.conferenceChat = chatRoomID[0];
        this.identity = UUID.randomUUID();
        this.conferenceRoomNum = chatRoomID[1];
        this.topic = topic;
        // consider order of creating activity and corresponding chatroom
    }

    /**
     * Determine whether this attendee should be added.
     * @return <CODE>true</CODE> if this attendee not in the Arraylist of attendees,
     * <CODE>false</CODE> otherwise
     */
    public boolean addAttendeesToList(String attendee){
        if(!this.attendeeList.contains(attendee)){
            this.attendeeList.add(attendee);
            return true;
        }else{
            return false;
        }
    }

    //phase 2 may use this method.
    public boolean addAttendeesToList(ArrayList<String> attendeeList){
        boolean addedAll = true;
        for(String i: attendeeList){
            if(this.attendeeList.contains(i)) {
                this.attendeeList.add(i);
            }else{
                addedAll = false;
            }
        }
        return addedAll;
    }

    /**
     * Determine whether this attendee has been removed.
     * @return <CODE>true</CODE> if this attendee has been removed form the Arraylist of attendees.
     * <CODE>false</CODE> otherwise
     */
    public boolean removeAttendee(String attendee){return this.attendeeList.remove(attendee);}

    /**
     * Determine whether this speaker has been added.
     * @return <CODE>true</CODE> if this speaker has been added.
     * <CODE>false</CODE> otherwise
     */
    public boolean addSpeakers(String speaker){
        this.speaker = speaker;
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

    /**
     * Changes the topic of this activity with the given new topic.
     * @param  topic the new topic of this activity.
     */
    public void setTopic(String topic){this.topic = topic;}

    /**
     * Changes the conference room number of this activity with the given new conference room number.
     * @param  conferenceRoomNum the new conference room number of this activity.
     */
    public void setRoom(UUID conferenceRoomNum){this.conferenceRoomNum = conferenceRoomNum;}

    /**
     * Changes the start time of this activity with the given new start time.
     * @param  startTime the new start time of this activity.
     */
    public boolean setStartTime(LocalDateTime startTime){
        if(startTime.isAfter(this.endTime)){
            return false;
        }else{
            this.startTime = startTime;
            changeDuration(Duration.between(this.startTime, this.endTime));
            return true;
        }
    }

    /**
     * Changes the end time of this activity with the given new end time.
     * @param  endTime the new start time of this activity.
     */
    public boolean setEndTime(LocalDateTime endTime){
        if(endTime.isBefore(this.startTime)){
            return false;
        }else{
            this.endTime = endTime;
            changeDuration(Duration.between(this.startTime, this.endTime));
            return true;
        }
    }

    /**
     * Changes the duration of this activity with the given new duration.
     * @param  duration the new duration of this activity.
     */
    public void setDuration(Duration duration){
        changeDuration(duration);
        this.endTime = this.startTime.plus(duration);
    }

    /**
     * Changes the Maximum Number of attendees with the given new integer.
     * @param  MaxNum the new Maximum number of attendees of this activity.
     */
    public void setMaxNumAttendee(int MaxNum){
        this.maxNumAttendee = MaxNum;
    }

    private void changeDuration(Duration duration){
        this.duration = duration;
    }

    /**
     * Gets an attendees list in this <code>Activity</code>.
     * @return A list of strings corresponding to the attendees of <code>attendees</code> in this
     * <code>Activity</code>.
     */
    public ArrayList<String> getAttendeeList(){return this.attendeeList;}

    /**
     * Gets an speakers list in this <code>Activity</code>.
     * @return A list of strings corresponding to the speakers of <code>speakers</code> in this
     * <code>Activity</code>.
     */
    public String getSpeaker(){return this.speaker;}

    /**
     * Gets the start time for this <code>Activity</code>.
     * @return The LocalDateTime corresponding to the start time of this <code>Activity</code>.
     */
    public LocalDateTime getStartTime(){return this.startTime;}

    /**
     * Gets the end time for this <code>Activity</code>.
     * @return The LocalDateTime corresponding to the end time of this <code>Activity</code>.
     */
    public LocalDateTime getEndTime(){return this.endTime;}

    /**
     * Gets the duration for this <code>Activity</code>.
     * @return The Duration corresponding to the duration of this <code>Activity</code>.
     */
    public Duration getDuration(){return this.duration;}

    /**
     * Gets the unique identifier for this <code>Activity</code>.
     * @return The UUID corresponding to this <code>Activity</code>.
     */
    public UUID getChatID(){return this.conferenceChat;}

    /**
     * Gets the Maximum Number of attendees for this <code>Activity</code>.
     * @return The integer corresponding to this <code>Activity</code>.
     */
    public Integer getMaxNumAttendee(){
        return this.maxNumAttendee;
    }

    /**
     * Gets an identity in this <code>Activity</code>.
     * @return A UUID corresponding to the identity in this <code>Activity</code>.
     */
    public UUID getIdentity(){return this.identity;}

    /**
     * Gets a conference room number in this <code>Activity</code>.
     * @return A UUID corresponding to the number of the conference room in this <code>Activity</code>.
     */
    public UUID getConferenceRoomNum(){return this.conferenceRoomNum;}

    /**
     * Gets a topic in this <code>Activity</code>.
     * @return A string corresponding to the topic in this <code>Activity</code>.
     */
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

    /**
     * This method show the info of activity in string.
     * @return the string of the class Activity
     */

    public String toString(){
        String description = "Topic: " + this.topic + "\n" +
                "Speakers: ";
        description += speaker;
        description += ("\nConference entities.Room " + this.conferenceRoomNum);
        description += ("\nFrom " + this.startTime + " to " + this.endTime);
        description += ("\nID: " + this.identity);
        description += ("\nChat ID: " + this.conferenceChat);
        return description;
    }

}
