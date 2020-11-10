import java.time.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class OrganizerController extends UserController {
    @Override
    public void run() {
        super.run();
    }

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
        if (roomma.bookingAvailable(targetPeriod) && freeSpeaker.size() != 0){
            UUID roomID = roomma.getRoomID();
            // check whether there are rooms available during that time; (use UUID, and int for capacity)
            // if that is, allow organizer to input info of conference;
            Scanner moreInfo = new Scanner(System.in);
            System.out.println("Please input topic and speaker IN ORDER and in different lines: ");
            String topic = moreInfo.nextLine();
            String speaker = moreInfo.nextLine();
            UUID assignedChat = chatmana.createChatroom(new ArrayList<>());
            // above arraylist has size zero, which will be assigned to conference chat automatically;
            UUID actID = actmanag.addNewActivity(targetPeriod[0], targetPeriod[1], assignedChat, roomID, topic);
            actmanag.addSpeaker(actID, speaker);
            roomma.BookRoom(targetPeriod, actID);
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
