package controllers;

import entities.Activity;
import jdk.vm.ci.meta.Local;
import useCases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AttendantController extends UserController{

    public AttendantController(UserManager manager){
        super(manager);
    }
    /*
    require implementation:
    view all available schedules they can enroll
    enroll into one conference, cancel enrollment
     */

    //check whether the room is full, and whether this user is currently enroll.
    private ArrayList<String[]> availableSchedules(){
        ArrayList<String[]> schedules = actmanag.viewUpcommingActivites();
        ArrayList<String[]> temp = new ArrayList<String[]>();
        //activity that user currently enroll.
        HashMap<LocalDateTime[], UUID> enrolledSchedules = userma.getActivities();
        ArrayList<String[]> temp2 = new ArrayList<String[]>();
        for (LocalDateTime[] time : enrolledSchedules.keySet()){
            String[] partialInfo = actmanag.searchActivityByUUID(enrolledSchedules.get(time).toString());
            temp2.add(partialInfo);
        }
        schedules.removeAll(temp2);
        //activity that is full and user is not free.
        for(String[]d: schedules){
            if (!roomma.CheckRoomFullness(actmanag.numAttendant(UUID.fromString(d[0])), UUID.fromString(d[4]))){
                temp.add(actmanag.searchActivityByUUID(d[0]));
            }
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime[] time = {LocalDateTime.parse(d[2], df), LocalDateTime.parse(d[3], df)};
            if(!userma.isFree(time)){
                temp.add(actmanag.searchActivityByUUID(d[0]));
            }
        }

        schedules.removeAll(temp);
        return schedules;
    }

    public void viewSchedules(){
        ArrayList<String[]> result = this.availableSchedules();
        System.out.println(result);
        // presenter
    }

    //add a new activity to this user, and add this user to the corresponding conference chat.
    public void enrollConference(){
        ArrayList<String> userName = new ArrayList<String>();
        userName.add(userma.currentUsername());
        Scanner scan = new Scanner(System.in);
        System.out.println("please input the activity's ID " +
                "you wish to enroll");
        String activityID = scan.nextLine();
        String[] temp = actmanag.searchActivityByUUID(activityID);
        if (this.availableSchedules().contains(temp)){
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime[] time = {LocalDateTime.parse(temp[2], df), LocalDateTime.parse(temp[3], df)};
            userma.selfAddSchedule(time, actmanag.getConferenceChat(UUID.fromString(temp[0])));
            chatmana.addUser(userName, UUID.fromString(temp[4]));
            actmanag.addAttendant(UUID.fromString(activityID), userma.currentUsername());
        }
        else{
            System.out.println("Invalid activity ID.");
        }
    }

    public void cancelEnrollment(){
        ArrayList<String> userName = new ArrayList<String>();
        userName.add(userma.currentUsername());
        Scanner scan = new Scanner(System.in);
        System.out.println("please input the activity's ID " +
                "you wish to cancel");
        String activityID = scan.nextLine();
        HashMap<LocalDateTime[], UUID> temp = userma.getActivities();
        //check whether this activity user has enrolled.
        UUID actID = UUID.fromString(activityID);
        if(temp.containsValue(actID)){
            actmanag.removeAttendant(actID, userma.currentUsername());
            chatmana.removeUser(userName,actmanag.getConferenceChat(actID));
            String[] actInfo = actmanag.searchActivityByUUID(activityID);
            // update user's enrollment in User-manager
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime[] time = {LocalDateTime.parse(actInfo[2], df), LocalDateTime.parse(actInfo[3], df)};
            userma.deleteActivity(time);
        }
        else{
            System.out.println("Invalid activity ID.");
        }
    }
}
