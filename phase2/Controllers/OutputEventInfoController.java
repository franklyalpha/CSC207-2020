package Controllers;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.*;

import java.util.ArrayList;

/**
 * Outputs all upcoming events as a PDF file.
 */
public class OutputEventInfoController extends EventController {

    private static final String DEST = "./eventsInfo.pdf";


    private final ArrayList<String[]> upcomingEvents;

    /**
     * A class to output desired pdf file.
     * @param userController get the upcomingEvents from userController.
     */
    public OutputEventInfoController(UserController userController){
        super(userController);
        this.upcomingEvents = eventManager.viewUpcomingEvents();
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
            document.add(new Paragraph("No upcoming events found!"));
        }
        document.close();
    }


    /**
     * Writes data into a PDF file.
     * @param events information of events.
     * @throws IOException: when the PDF file is not found.
     */
    public void outputEvents(ArrayList<String[]> events) throws IOException{
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        Document document = new Document(pdf);
        for (String[] event : events){
            writeSingleEvent(event, document);
        }
        document.close();
    }

    private void writeSingleEvent(String[] event, Document document) {
        document.add(new Paragraph(event[5]));
    }
}
