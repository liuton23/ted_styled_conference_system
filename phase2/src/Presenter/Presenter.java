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
        System.out.println("Please select an option:");
        for(int i = 0; i < options.size(); i++) {
            System.out.print(options.get(i) + "\t \t \t");
            if( (i + 1) % width == 0){
                System.out.println();
            }
        }
        System.out.print("Input: ");
    }
    //MENU METHODS

    /**
     * Displays the login menu.
     */
    public void loginMenu(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(L)ogin");
        options.add("(R)egister");
        prompt(options);
    }

    /**
     * Displays the main menu for attendees.
     */
    public void basicMenu1(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(M)essages");
        options.add("(E)vents");
        options.add("(I)tinerary");
        options.add("(B)ack");
        prompt(options);
    }

    /**
     * Displays the main menu for organizers.
     */
    public void basicMenu2(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(M)essages");
        options.add("(E)vents");
        options.add("(I)tinerary");
        options.add("(S)chedule events");
        options.add("(C)reate speaker account");
        options.add("(B)ack");
        prompt(options);
    }

    /**
     * Displays an organizer only menu.
     */
    public void organizerMenu(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(S)chedule Event");
        options.add("(A)dd Room");
        options.add("(C)hange Speaker");
        options.add("(B)ack");
        prompt(options);
    }

    /**
     * Displays the main message menu.
     */
    public void mainMessageMenu(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(M)essage users");
        options.add("(V)iew messages");
        options.add("(B)ack");
        prompt(options);
    }

    /**
     * Displays the send message menu for attendee.
     */
    public void sendMessageMenuAtt(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Send to a (U)ser");
        options.add("(B)ack");
        prompt(options);
    }

    /**
     * Displays the send message menu for organizer.
     */
    public void sendOrgMessageOrg(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Send to a (U)ser");
        options.add("Send to all (S)peakers");
        options.add("Send to all (A)ttendees");
        options.add("(B)ack");
        prompt(options);
    }

    public void wishToSendMoreEvent(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(S)end to one more event");
        options.add("(C)ontinue to message");
        prompt(options);
    }

    /**
     * Displays the send message menu for speaker.
     */
    public void sendMessageMenuSpeaker(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Send to a (U)ser");
        options.add("Send to all attendees in one or multiple (E)vents");
        options.add("(B)ack");
        prompt(options);
    }

    /**
     * Displays the view message menu.
     */
    public void viewMessageMenu(){
        ArrayList<String> options = new ArrayList<>();
        options.add("View (S)ent messages");
        options.add("View (R)eceived messages");
        options.add("View (U)nread messages");
        options.add("View messages (F)rom another user");
        options.add("(B)ack");
        prompt(options);
    }



    /**
     * Displays the event menu.
     */
    public void eventMenu(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(V)iew all events");
        options.add("(S)ign up for events");
        options.add("(D)rop out of events");
        options.add("(B)ack");
        prompt(options);
    }

    /**
     * Displays the view events menu.
     */
    public void viewEventsMenu(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Sort events by (T)ime");
        options.add("Sort events by (N)ame");
        options.add("Sort events by (S)peaker");
        prompt(options);
    }

    /**
     * Displays the month options menu.
     */
    public void viewMonthsMenu(){
        ArrayList<String> options = new ArrayList<>();
        options.add("JANUARY");
        options.add("FEBRUARY");
        options.add("MARCH");
        options.add("APRIL");
        options.add("MAY");
        options.add("JUNE");
        options.add("JULY");
        options.add("AUGUST");
        options.add("SEPTEMBER");
        options.add("OCTOBER");
        options.add("NOVEMBER");
        options.add("DECEMBER");
        prompt(options);
    }

    /**
     * Chooses which options are valid input options for a menu given <code>menu_id</code>.
     * @param menu_id determines which menu choices are needed.
     * @return list of valid input choices for a menu.
     */
    public ArrayList<String> chooseMenuOptions(int menu_id){
        ArrayList<String> choices = new ArrayList<>();
        switch (menu_id){
            case 1:
                choices.add("L");
                choices.add("R");
                break;
            case 3:
                choices.add("C");
                choices.add("S");
            case 2:
                choices.add("M");
                choices.add("E");
                choices.add("I");
                choices.add(("B"));
                break;
            case 4:
                choices.add("S");
                choices.add("A");
                choices.add("C");
                choices.add(("B"));
                break;
            case 5:
                choices.add("M");
                choices.add("V");
                choices.add(("B"));
                break;
            case 6:
                choices.add("U");
                choices.add("B");
                break;
            case 7:
                choices.add("S");
                choices.add("R");
                choices.add("U");
                choices.add("M");
                choices.add("A");
                choices.add("F");
                choices.add("B");
                break;
            case 8:
                choices.add("V");
                choices.add("S");
                choices.add("D");
                choices.add(("B"));
                break;
            case 9:
                choices.add("T");
                choices.add("N");
                choices.add("S");
                break;
            case 10:
                choices.add("U");
                choices.add("S");
                choices.add("A");
                choices.add("B");
                break;
            case 11:
                choices.add("U");
                choices.add("E");
                choices.add("B");
                break;
            case 12:
                choices.add("S");
                choices.add("C");
            case 13:
                choices.add("JANUARY");
                choices.add("FEBRUARY");
                choices.add("MARCH");
                choices.add("APRIL");
                choices.add("MAY");
                choices.add("JUNE");
                choices.add("JULY");
                choices.add("AUGUST");
                choices.add("SEPTEMBER");
                choices.add("OCTOBER");
                choices.add("NOVEMBER");
                choices.add("DECEMBER");
            case 14: //yes options for are you organizer
                choices.add("Y");
                choices.add("T");
                choices.add("YES");
                choices.add("TRUE");
            case 15: //no options for are you organizer
                choices.add("N");
                choices.add("F");
                choices.add("NO");
                choices.add("FALSE");
        }
        return choices;
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
            case "requestSpeaker":
                System.out.println("Enter speaker username:");
                break;
            case "requestYear":
                System.out.println("Enter starting year of the event:");
                break;
            case "requestMonth":
                System.out.println("Enter starting month of event (ex. October):");
                break;
            case "requestDay":
                System.out.println("Enter starting day of event:");
                break;
            case "requestHour":
                System.out.println("Enter starting hour of the event:");
                break;
            case "requestMinute":
                System.out.println("Enter starting minute of the event:");
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
        }
    }



    /**
     * Displays an message depends on how events are sort then displays a String representation of events one at a time.
     * @param options list of String options
     * @param way ways to sort
     */
    public void displaySchedule(ArrayList<String> options, String way){
        switch(way){
            case "time":
                System.out.println("Sort events by time");
                break;
            case "name":
                System.out.println("Sort events by name");
                break;
            case "speaker":
                System.out.println("Sort events by speaker");
                break;
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
     * Displays an initial message then displays a list of Strings one at a time by speakers
     * @param events list of events
     */
    public void displayAllEvents(ArrayList<Event> events, String way){
        switch(way){
            case "time":
                System.out.println("Events sorted by time");
                break;
            case "name":
                System.out.println("Events sorted by name");
                break;
            case "speaker":
                System.out.println("Events sorted by speaker");
                break;
        }
        for (Event e: events){
            System.out.println(e);
        }
    }


    /**
     * Displays a message when input is invalid.
     */
    public void invalidInput(){
        System.out.println("The input you entered is invalid.");
        System.out.println("Please try again.");
    }

    /**
     * Prompts user for username.
     */
    public void printUsernameMessage(){
        System.out.println("Enter your username:");
    }

    /**
     * Prompts user to input event number or zero if no more event
     */
    public void printInputEventNumOrZero(){
        System.out.println("Please enter another event name " +
                "if you wish, otherwise just enter (N)o");
    }


    /**
     * Prompts user for password.
     */
    public void printPasswordMessage() {
        System.out.println("Enter your password");
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

    public void printSpeakerCreatedMessage(){
        System.out.println("The speaker was successfully added to the system.");
    }

    /**
     * Displays that "Are u an organizer".
     */
    public void printAreUAOrg(){
        System.out.println("Are you an organizer?");
    }

    /**
     * Displays failed registration message.
     */
    public void printRegisterFailMessage(){
        System.out.println("Your username or password is invalid.");
        System.out.println("Please check that username or password is non-empty and username is not already taken.");
    }

    /**
     * Displays message reflecting whether a speaker was successfully changed.
     * @param changeSpeakerOutput output of the ScheduleSystem.changeSpeaker() method.
     */
    public void printChangeSpeakerMessage(int changeSpeakerOutput){
        switch (changeSpeakerOutput) {
            case 0:
                System.out.println("Speaker changed successfully.");
                break;
            case 1:
                System.out.println("Speaker is already booked at this time.");
                break;
            case 2:
                System.out.println("This person is not a speaker.");
                break;
            case 3:
                System.out.println("This user does not exist.");
                break;
            case 4:
                System.out.println("This event name does not correspond to an event in the system.");
                break;
            case 5:
                System.out.println("The username provided does not match a speaker in the system.");
                break;
        }
    }

    /**
     * Displays message reflecting whether an event was successfully created and if not what the issue is.
     * @param scheduleEventMessage is the output of the ScheduleSystem.createEvent() method.
     */
    public void printScheduleEventMessage(int scheduleEventMessage) {
        switch (scheduleEventMessage) {
            case 0:
                System.out.println("Room is already booked for this timeslot.");
                break;
            case 1:
                System.out.println("Speaker is already booked for this timeslot.");
                break;
            case 2:
                System.out.println("This event name has already been taken.");
                break;
            case 3:
                System.out.println("Event successfully created.");
                break;
            case 4:
                System.out.println("The username provided does not match a speaker in the system.");
                break;
            case 5:
                System.out.println("This room is not in the system.");
                break;
        }
    }

    /**
     * Displays message reflecting whether a room was successfully added or a message indicating error.
     * @param addRoomMessage output from the ScheduleSystem.addRoom() method
     */
    public void printAddRoomMessage(int addRoomMessage){
        switch (addRoomMessage){
            case 0:
                System.out.println("Room successfully added.");
                break;
            case 1:
                System.out.println("Room already exists in System.");
                break;
        }
    }

    /**
     * Displays message reflecting whether or not an Attendee was successfully signed up for an event or a message
     * indicating error.
     * @param signUpOutput is a boolean value outputted by EventManager.signUp() signalling successful or unsuccessful sign up.
     */
    public void printSignUpMessage(int signUpOutput){
        if (signUpOutput == 2){
            System.out.println("User was successfully signed up.");
        }
        else if (signUpOutput == 3) {
            System.out.println("Event is at capacity: unable to sign up user.");
        }
        else if (signUpOutput == 5){
            System.out.println("User is already signed up for this event.");
            }
        else if (signUpOutput == 4){
            System.out.println("Invalid ID. Please try again.");
        }
        else if (signUpOutput == 6) {
            System.out.println("You cannot attend this event. Please select another event and try again.");
        }
        else {
            System.out.println("There are no such event");
        }
    }


    /**
     * Prints a message that user should enter a valid integer
     */
    public void printInvalidIntMessage() {
        System.out.println("Error: Please enter a valid integer");
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

    /**
     * Prints a message saying no save file was found
     */
    public void printNoSaveFile() {
        System.out.println("NO save file found.");
        System.out.println("Save file was not found or was corrupted.");
    }

}
