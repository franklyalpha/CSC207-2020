package controllers;

import useCases.UserManager;

import java.time.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class OrganizerController extends UserController {
    public OrganizerController(UserManager manager) {
        super(manager);
    }

    @Override
    public void run() {
        super.run();
    }

    /*
    require implementation:

    create room, create speaker account, modify speaker,
     */

    private boolean addSchedule(){
        Scanner start = new Scanner(System.in);
        System.out.println("Please input year, month, day, hour, minute of start time IN ORDER: ");
        LocalDateTime startDateTime = LocalDateTime.of(start.nextInt(),
                start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt());

        Scanner end = new Scanner(System.in);
        System.out.println("Please input year, month, day, hour, minute of end time IN ORDER: ");
        LocalDateTime endDateTime = LocalDateTime.of(start.nextInt(),
                start.nextInt(), start.nextInt(), start.nextInt(), start.nextInt());
        LocalDateTime[] targetPeriod = {startDateTime, endDateTime};
        // input time;
        ArrayList<String> freeSpeaker = userma.availableSpeakers(targetPeriod);
        ArrayList<UUID> freeRooms = roomma.bookingAvailable(targetPeriod);
        if (freeRooms.size() != 0 &&
                freeSpeaker.size() != 0){
            // check whether there are rooms available during that time; (use UUID, and int for capacity)
            // if that is, allow organizer to input info of conference (code below);
            Scanner moreInfo = new Scanner(System.in);
            System.out.println(freeRooms);
            System.out.println("Please input topic, speaker and ith room (e.g, 1st room: input 1) for this activity" +
                    " IN ORDER and in different lines: ");
            String topic = moreInfo.nextLine();
            String speaker = moreInfo.nextLine();
            int roomIndex = moreInfo.nextInt() - 1;
            UUID assignedChat = chatmana.createChatroom(new ArrayList<>());
            // above arraylist has size zero, which will be assigned to conference chat automatically;
            UUID assignedroom = freeRooms.get(roomIndex);
            UUID actID = actmanag.addNewActivity(targetPeriod[0], targetPeriod[1], assignedChat, assignedroom, topic);
            actmanag.addSpeaker(actID, speaker);
            roomma.BookRoom(targetPeriod, actID, assignedroom);
            userma.otherAddSchedule(speaker, targetPeriod, actID);
            // then choose a room;
            // then program creates schedule automatically, and update both activity, speaker and room.
            // saving file???
            return true;
        }
        else{
            System.out.println("Invalid time period! Please reconsider another time!!!");
        }
        return false;
    }


}
