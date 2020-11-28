package Controllers;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.*;

import java.util.ArrayList;

public class OutputConferenceInfoController extends ActivityController {
    private static final String DEST = "./eventsInfo.pdf";
    private final int ID = 0;
    private final int TOPIC = 1;
    private final int START = 2;
    private final int END = 3;
    private final int ROOMNUM = 4;
    private final int SPEAKER = 5;


    private final ArrayList<String[]> upcomingEvents;

    public OutputConferenceInfoController(UserController userController){
        super(userController);
        this.upcomingEvents = activityManager.viewUpcommingActivites();
    }

    public void outputAllUpcomingEvents() throws IOException{
        PdfDocument pdf = new PdfDocument(new PdfWriter("./allUpcomingEvents.pdf"));
        Document document = new Document(pdf);
        document.add(new Paragraph("eventHeader"));
//        for (String[] event : this.upcomingEvents){
//            writeSingleEvent(event, document);
//        }
        document.close();
    }

    public void outputEvents(ArrayList<String[]> events) throws IOException{
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        Document document = new Document(pdf);
        for (String[] event : events){
            writeSingleEvent(event, document);
        }
        document.close();
    }

    private void writeSingleEvent(String[] event, Document document) {
        String eventHeader = eventHeader(event);
        String eventTime = eventTime(event);
        String eventLocation = eventLocation(event);
        String endOfEvent = "\n";

        document.add(new Paragraph(eventHeader));
        document.add(new Paragraph(eventTime));
        document.add(new Paragraph(eventLocation));
        document.add(new Paragraph(endOfEvent));
    }

    private String eventHeader(String[] event){
        String id = event[ID];
        String topic = event[TOPIC];
        String speaker = event[SPEAKER];

        return topic + " hold by " + speaker + " (#" + id + ")";
    }

    private String eventTime(String[] event){
        String startTime = event[START];
        String endTime = event[END];

        return "Start at: " + startTime + ", end at: " + endTime;
    }

    private String eventLocation(String[] event){
        String roomNum = event[ROOMNUM];

        return "Location: Conference Room #" + roomNum;
    }
}
