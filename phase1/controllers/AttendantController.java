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
    private void viewSchedules(){
        ArrayList<String[]> schedules = actmanag.viewUpcommingActivites();
        HashMap<LocalDateTime[], UUID> enrolledActivities = new HashMap<LocalDateTime[], UUID>();
        ArrayList<String[]> temp = new ArrayList<String[]>();
        for(UUID c: enrolledActivities.values()){
            temp.add(actmanag.searchActivityByUUID(c.toString()));
        }
        schedules.removeAll(temp);
        //presenter
    }

    //add a new activity to this user, and add this user to the corresponding conference room.
    public void enrollConference(){
        ArrayList<String> userName = new ArrayList<String>();
        userName.add(userma.currentUsername());
        Scanner scan = new Scanner(System.in);
        System.out.println("please input the activity's ID " +
                "you wish to enroll");
        String activityID = scan.nextLine();
        String[] temp = actmanag.searchActivityByUUID(activityID);
        if (temp != null){
            if (userma.schedules().containsValue(UUID.fromString(activityID))){
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime[] time = {LocalDateTime.parse(temp[2], df), LocalDateTime.parse(temp[3], df)};
                userma.selfAddSchedule(time, UUID.fromString(temp[0]));
                chatmana.addUser(userName, UUID.fromString(temp[4]));
            }
            else{

                System.out.println("You have already enroll in this activity");
            }
        }
        else{
            System.out.println("Invalid activity ID.");
        }
    }
    public void cancelEnrollment(){

    }
}
