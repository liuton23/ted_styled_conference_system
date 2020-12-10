package Presenter;

import java.util.LinkedHashMap;

/**
 * Contains or presents strings for ScheduleDownloader
 */
public class ScheduleDownloaderPresenter {

    /**
     * Gets the name of the file to create
     * @return the name of the file
     */
    public String getFileName() {
        return "Conference Schedule"; //Note: file name must be more than 3 characters long
    }

    /**
     * Gets the field for the document author
     * @return the document author string
     */
    public String getDocumentAuthor() {
        return "207th Tech Conference Schedule";
    }

    /**
     * Gets the field for the document creator
     * @return the document creator string
     */
    public String getDocumentCreator() {
        return "207th Tech Conference Schedule";
    }

    /**
     * Gets the field for the document title
     * @return the document title string
     */
    public String getDocumentTitle() {
        return "207th Tech Conference Schedule";
    }

    /**
     * Gets the field for the document subject
     * @return the document subject string
     */
    public String getDocumentSubject() {
        return "207th Tech Conference Schedule"; //TO DO: add date when made
    }

    /**
     * Gets a linked hash map with the mapping for column header to the string to display for the column header
     * @return the linked hash map with headers to strings
     */
    public LinkedHashMap<String, String> getHeaders() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        headers.put("Title", "Title");
        headers.put("Start", "Start");
        headers.put("End", "End");
        headers.put("Room", "Room");
        headers.put("Speaker", "Speaker(s)");
        return headers;
    }

    /**
     * Gets the string to display when an event does not contain the info for a column
     * @return the string when an event does not have the info for a column
     */
    public String getNotApplicable() {
        return "N/A";
    }

    /**
     * Displays an error message for when generating the PDF failed
     */
    public void displayErrorGeneratingMessage() {
        System.out.println("There was an error generating your PDF. Please try again Later");
    }

    /**
     * Displays an error message for when the PDF was generated but could not find its file path
     */
    public void displayErrorFindingPathMessage() {
        System.out.println("PDF was successfully generated but file path could not be found.");
        System.out.println("Your PDF was likely placed in the Downloads folder. Please check there.");
    }

    /**
     * Displays a message that the PDF was generated and where it can be found
     * @param filepath the location of the pdf
     */
    public void displayFinishedMessage(String filepath) {
        System.out.println("Your PDF was successfully generated");
        System.out.println("You can find the PDF at " + filepath);
    }

    /**
     * Displays an error message for when there are no events in the system
     */
    public void displayNoEventsMessage() {
        System.out.println("Error: there are no events in the system!");
    }
}
