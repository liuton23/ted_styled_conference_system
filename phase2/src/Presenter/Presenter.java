package Presenter;

import Entities.Event;

import java.util.ArrayList;

/**
 * Manages the formatting and output of most <code> Strings </code>.
 */
public class Presenter {

    /**
     * Displays a welcome message that runs at the beginning of the program.
     */
    public void welcomeMessage(){
        System.out.println("Welcome to the 207th Tech Conference!");
        System.out.println("You can enter " + getExit() + " to exit anytime.");
        System.out.println("Note that terminating the program without using the above method may not save your " +
                "information.");
    }

    /**
     * Returns the string that would be used by the user to exit the program
     * @return the string that would be used to exit the program
     */
    public String getExit() {
        return "EXIT";
    }

    /**
     * Displays a generic message.
     */
    public void display(String s){
        System.out.println(s);
    }

    /**
     * Displays <code>options</code> for the user.
     * @param options list of options for the user to choose from
     */
    public void prompt(ArrayList<String> options){
        int width = (options.size() / 4) + 1;
        int[] colLengths = new int[width];
        int[] lengths = new int[options.size()];
        for(int i = 0; i < options.size(); i++) {
            int length = (int) Math.ceil((options.get(i).length() + 1)/4.0);
            lengths[i] = length;
            if(colLengths[i % width] < length){
                colLengths[i%width] = length;
            }
        }
        System.out.println("Please select an option:");
        for(int i = 0; i < options.size(); i++) {
            StringBuilder tabspace = new StringBuilder();
            for(int j = 0; j < 2 + colLengths[i%width] - lengths[i]; j++){
                tabspace.append("\t");
            }
            System.out.print(options.get(i) + tabspace);
            if( (i + 1) % width == 0){
                System.out.println();
            }
        }
        System.out.print("Input: ");
    }


    //DISPLAY METHODS

    /**
     * Displays input message.
     * @param str input message.
     */
    public void displayMessages(String str){
        switch (str){
            case "M":
                System.out.println("MessageSystem");
                break;
            case "E":
                System.out.println("SignUpSystem");
                break;
            case "S":
                System.out.println("Schedule Event");
                System.out.println("Enter event title:");
                break;
            case "requestEventType":
                System.out.println("Please input the type of event you would like to schedule:");
                break;
            case "requestSpeaker":
                System.out.println("Enter speaker username:");
                break;
            case "requestNewSpeaker":
                System.out.println("Enter username of the new speaker:");
                break;
            case "requestOldSpeaker":
                System.out.println("Enter username of the speaker to be changed:");
                break;
            case "requestAdditionalSpeaker":
                System.out.println("If you would like to add an additional speaker input their username-- otherwise type 'No'");
                break;
            case "requestYear":
                System.out.println("Enter starting year of the event:");
                break;
            case "requestMonth":
                System.out.println("Enter starting month of event (ex. October):");
                break;
            case "requestDay":
                System.out.println("Enter starting day of event:");
                System.out.println("Enter a day of your chosen month from 1 up to 31");
                break;
            case "requestHour":
                System.out.println("Enter starting hour of the event:");
                System.out.println("Enter an integer between 0 - 23");
                break;
            case "requestMinute":
                System.out.println("Enter starting minute of the event:");
                System.out.println("Enter an integer between 0 - 59");
                break;
            case "requestDuration":
                System.out.println("Enter length of event (hours):");
                break;
            case "requestRoom":
                System.out.println("Enter room id of event room:");
                break;
            case "requestAddRoom":
                System.out.println("Add Room:");
                break;
            case "requestCapacity":
                System.out.println("Enter room capacity:");
                break;
            case "changeSpeaker":
                System.out.println("Change Speaker");
                break;
            case "requestEventCapacity":
                System.out.println("Enter event attendee capacity:");
                break;
            case "sortBySpeaker":
                System.out.println("Events sorted by speaker:");
                break;
            case "viewEvents":
                System.out.println("View all events");
                break;
            case "signUp":
                System.out.println("Sign up");
                break;
            case "requestEventId":
                System.out.println("Input the ID of the event you would like to sign up for:");
                break;
            case "dropOut":
                System.out.println("Drop out");
                break;
            case "requestEventIdDropOut":
                System.out.println("Event ID of event you'd like to drop out of:");
                break;
            case "enterSpeakerPswd":
                System.out.println("Enter Speaker Password:");
                break;
            case "sendingEmail":
                System.out.println("Sending email. This may take a moment.");
                break;
            case "sentEmail":
                System.out.println("Email has been sent.");
                break;
            case "emailError":
                System.out.println("There was an error sending the email");
                break;
            case "enterEmail":
                System.out.println("Enter email or enter \"B\" to go back:");
                break;
            case "yourEmail":
                System.out.println("You already have an email associated with this account.");
                break;
            case "takenEmail":
                System.out.println("There is another account associated with this email.");
                break;
            case "areUOrg":
                System.out.println("Are you an organizer?");
                break;
            case "invalidInput":
                System.out.println("The input you entered is invalid.");
                System.out.println("Please try again.");
                break;
            case "invalidIntInput":
                System.out.println("Error: Please enter a valid integer");
                break;
            case "YorN":
                System.out.println("(Y)es or (N)o");
                break;
            case "requestCancelEvent":
                System.out.println("Enter an event ID you would like to cancel:");
                break;
        }
    }



    /**
     * Displays an message depends on how events are sort then displays a String representation of events one at a time.
     * @param options list of String options
     * @param way ways to sort
     */
    public void displaySchedule(ArrayList<String> options, String way){
        switch(way){
            case "itinerary":
                System.out.println("Your itinerary of event(s) you are attending:");
                break;
            case "speakItinerary":
                System.out.println("Your itinerary of event(s) you speaks at:");
                break;
            case "VIPItinerary":
                System.out.println("Your VIP-only list of event(s) you speaks at:");
                break;
        }
        for (String str: options){
            System.out.println(str);
        }
    }

    /**
     * display cancel event message
     * @param value cases
     */
    public void cancelEventMessage(int value){
        switch (value){
            case 0:
                System.out.println("Event name does not correspond to an event.");
                break;
            case 1:
                System.out.println("Event successfully removed.");
                break;
        }
    }


    /**
     * Prompts user for username.
     */
    public void printUsernameMessage(int cases){
        switch(cases){
            case 1:
                System.out.println("Enter your username:");
                break;
            case 2:
                System.out.println("Enter an username:");
                break;
        }

    }


    /**
     * Prompts user for password.
     */
    public void printPasswordMessage(int ind) {
        switch (ind){
            case 1:
                System.out.println("Enter your password");
                break;
            case 2:
                System.out.println("Enter a password");
                break;
        }
    }

    /**
     * Displays successful login message.
     */
    public void printLoginSucceedMessage(){
        System.out.println("You have successfully logged in.");
    }

    /**
     * Displays failed login message.
     */
    public void printLoginFailMessage(){
        System.out.println("Your username or password is incorrect.");
    }

    /**
     * Displays successful registration message.
     */
    public void printRegisterSucceedMessage(){
        System.out.println("You have successfully registered. Please login to continue.");
    }
    /**
     * Displays that the organizer successfully added a speaker to the system.
     */

    public void printAccountCreatedMessage(){
        System.out.println("This account was successfully added to the system.");
    }


    /**
     * Displays failed registration message.
     */
    public void printRegisterFailMessage(){
        System.out.println("This username or password is invalid.");
        System.out.println("Please check that username or password is non-empty and username is not already taken.");
    }


    /**
     * Prints a message saying no save file was found
     */
    public void printNoSaveFile() {
        System.out.println("NO save file found.");
        System.out.println("Save file was not found or was corrupted.");
    }

    /**
     * Prints a message that user should enter a valid integer between start and end
     * @param start start of valid range
     * @param end end of valid range
     */
    public void printInvalidIntRangeMessage(int start, int end) {
        System.out.println("Error: Please enter a valid integer between " + start + " and " + end);
    }

    /**
     * Prints a message that user should enter a valid integer greater or equal to start
     * @param start start of valid range
     */
    public void printInvalidIntRangeMessage(int start) {
        System.out.println("Error: Please enter a valid integer greater than or equal to " + start);
    }

}
