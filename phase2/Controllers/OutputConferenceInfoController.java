package Controllers;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.*;

import java.util.ArrayList;

/**
 *
 */
public class OutputConferenceInfoController extends ActivityController {

    private static final String DEST = "./eventsInfo.pdf";
    private static final int ID = 0;
    private static final int TOPIC = 1;
    private static final int START = 2;
    private static final int END = 3;
    private static final int ROOMNUM = 4;
    private static final int SPEAKER = 5;


    private final ArrayList<String[]> upcomingEvents;

    /**
     * A class to output desired pdf file.
     * @param userController get the upcomingEvents from userController.
     */
    public OutputConferenceInfoController(UserController userController){
        super(userController);
        this.upcomingEvents = activityManager.viewUpcommingActivites();
    }

    /**
     * Generate a pdf file containing all upcoming events.
     * @throws IOException exception threw by PdfDocument
     */
    public void outputAllUpcomingEvents() throws IOException{
        PdfDocument pdf = new PdfDocument(new PdfWriter("./phase2/allUpcomingEvents.pdf"));
        Document document = new Document(pdf);
        if (this.upcomingEvents.size() != 0){
            for (String[] event : this.upcomingEvents){
                writeSingleEvent(event, document);
            }
        } else {
            document.add(new Paragraph("No upcoming event founded!"));
        }
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
