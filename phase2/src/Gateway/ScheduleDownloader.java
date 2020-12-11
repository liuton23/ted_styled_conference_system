package Gateway;

import java.io.FileOutputStream;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import Presenter.ScheduleDownloaderPresenter;
import UseCases.EventManager;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Generates and downloads the schedule of the conference system
 */
public class ScheduleDownloader {
    private EventManager eventManager;
    private ScheduleDownloaderPresenter presenter = new ScheduleDownloaderPresenter();

    /**
     * Creates an instance of ScheduleDownloader
     * @param eventManager the EventManager for the conference to download the schedule of
     */
    public ScheduleDownloader(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Generates a PDF of the conference's schedule
     */
    public void generatePDF() {
        if (eventManager.getAllEvents().isEmpty()) {
            presenter.displayNoEventsMessage();
        } else {
            Document doc = new Document();
            try {
                String home = System.getProperty("user.home");
                File dir = new File(home + "/Downloads/"); //the directory of the downloads folder
                File file = File.createTempFile(presenter.getFileName(), ".pdf", dir);
                PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
                doc.open();

                //set font
                Font font = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);

                //set title in pdf
                Paragraph title = new Paragraph(presenter.getDocumentTitle());
                doc.add(title);
                doc.add(Chunk.NEWLINE); //adds a space after title

                //set table
                Paragraph schedule = new Paragraph();

                //get events infos in sorted order by time
                ArrayList<LinkedHashMap<String, String>> eventInfos = eventManager.getEventInfoLists();
                ArrayList<String> keys = getKeys(eventInfos);
                int max = keys.size();
                LinkedHashMap<String, String> headers = presenter.getHeaders(); //put headers in presenter

                PdfPTable table = new PdfPTable(max); //create a table with enough columns to accommodate all the info

                //place headers
                for (String key : keys) {
                    table.addCell(headers.getOrDefault(key, "+"));
                }

                //place info in columns
                for (LinkedHashMap<String, String> info : eventInfos) {
                    for (String key : keys) {
                        table.addCell(info.getOrDefault(key, presenter.getNotApplicable()));
                    }
                }

                schedule.add(table);
                doc.add(schedule);

                //File Attributes
                doc.addAuthor(presenter.getDocumentAuthor());
                doc.addCreationDate();
                doc.addCreator(presenter.getDocumentCreator());
                doc.addTitle(presenter.getDocumentTitle());
                doc.addSubject(presenter.getDocumentSubject());

                doc.close();
                writer.close();
                presenter.displayFinishedMessage(file.getAbsolutePath());
            } catch (SecurityException e) {
                presenter.displayErrorGeneratingMessage();
                e.printStackTrace();
            } catch (Exception e) {
                presenter.displayErrorFindingPathMessage();
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the keys from the linked hash maps in a list
     * @param listOfLists the list of linked hash maps
     * @return a list of the keys
     */
    private ArrayList<String> getKeys(ArrayList<LinkedHashMap<String, String>> listOfLists) {
        ArrayList<String> keys = new ArrayList<>();
        for (LinkedHashMap<String, String> dict : listOfLists) {
            for (String key : dict.keySet()) {
                if (!keys.contains(key)) {
                    keys.add(key);
                }
            }
        }
        return keys;
    }
}
