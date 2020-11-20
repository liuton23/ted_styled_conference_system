package Controller;

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
        System.out.println("You can enter 'EXIT' to exit anytime.");
        System.out.println("Note that terminating the program without using the above method may not save your " +
                "information.");
    }

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
        System.out.println("What would you like to do:");
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
     * Method which displays user's messages from a list
     * @param messages a list of messages to be displayed
     */

    public void displayListOfMessage(ArrayList<String> messages) {
        for (String m: messages) {
            System.out.println(m);
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
     * Prompts user to input other user's username when message others
     */
    public void printPleaseInputUsername(){
        System.out.println("Please input an username");
    }

    /**
     * Prompts user when input username is incorrect
     */
    public void printIncorrectUsername(){
        System.out.println("Incorrect username please try again");
    }

    /**
     * Prompts user to input message
     */
    public void printInputMessagePlz(){
        System.out.println("Please input your message");
    }

    /**
     * Prompts user to input event number
     */
    public void printInputEventName(){
        System.out.println("Please enter an event name");
    }

    /**
     * Prompts user to input event number or zero if no more event
     */
    public void printInputEventNumOrZero(){
        System.out.println("Please enter another event name " +
                "if you wish, otherwise just enter (N)o");
    }

    /**
     * Prompts user when there are no sent messages for this user
     */
    public void printNoSentForU(){
        System.out.println("There are no sent messages from you");
    }

    /**
     * Prompts user when there are no received messages for this user
     */
    public void printNoRecForU(){
        System.out.println("There are no received messages for you");
    }


    /**
     * string of message below
     * @return "There are no messages sent to you from "
     */
    public String thereAreNoMessForUFrom(){
        return "There are no messages sent to you from ";
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
        else {
            System.out.println("There are no such event");
        }
    }

    /**
     * Displays message reflecting the situation after messaging an attendee.
     * @param messageAttendee output from the MessageSystem.messageAttendee() method
     */

    public void printMessageAttendee(int messageAttendee){
        switch (messageAttendee) {
            case 1:
                System.out.println("Incorrect username. Please try again.");
                break;
            case 2:
                System.out.println("The message can not be sent to an Organizer.");
                break;
            case 3:
                System.out.println("The message has been successfully sent.");
                break;
        }
    }

    /**
     * Displays message reflecting the situation after messaging all speakers.
     * @param messageAllSpeakers  output from MessageSystem.messageAllSpeakers()
     */
    public void printMessageAllSpeakers(int messageAllSpeakers){
        switch (messageAllSpeakers) {
            case 1:
                System.out.println("Incorrect username. Please try again.");
                break;
            case 2:
                System.out.println("Only Organizer can message all speakers.");
                break;
            case 3:
                System.out.println("The message has been successfully sent.");
                break;
        }
    }

    /**
     * Displays message reflecting the situation after messaging all attendees.
     * @param messageAllAttendees  output from MessageSystem.messageAllAttendees()
     */
    public void printMessageAllAttendees(int messageAllAttendees){
        switch (messageAllAttendees) {
            case 1:
                System.out.println("Incorrect username. Please try again.");
                break;
            case 2:
                System.out.println("Only Organizer can message all attendees.");
                break;
            case 3:
                System.out.println("The message has been successfully sent.");
                break;
        }

    }

    /**
     * Displays message reflecting the situation after messaging all attendees from a specific event.
     * @param messageEventAttendees  output from MessageSystem.messageEventAttendees()
     */
    public void printMessageEventAttendees(int messageEventAttendees){
        switch (messageEventAttendees) {
            case 1:
                System.out.println("You do not speak at this event.");
                break;
            case 2:
                System.out.println("Incorrect username. Please try again.");
                break;
            case 3:
                System.out.println("Only speakers can sent messages to all attendees of their talks they give.");
                break;
            case 4:
                System.out.println("This event do not exist or you spell the name wrong");
                break;
            case 5:
                System.out.println("The message has been successfully sent.");
                break;
            case 6:
                System.out.println("This event has no attendees.");
                break;
        }
    }

    /**
     * Displays message reflecting the situation after messaging all attendees from multiple events.
     * @param messageMultipleEventsAttendees output from overloading MessageSystem.messageEventAttendees()
     */
    public void printMessageMultipleEventsAttendees(int messageMultipleEventsAttendees){
          switch(messageMultipleEventsAttendees){
              case 1:
                  System.out.println("Events you entered contains event you do not speak at.");
                  break;
              case 2:
                  System.out.println("Incorrect username. Please try again.");
                  break;
              case 3:
                  System.out.println("Only speakers can sent messages to all attendees of their talks they give.");
                  break;
              case 4:
                  System.out.println("Events you entered contains invalid event.(do not exists or spelled name wrong)");
                  break;
              case 5:
                  System.out.println("The message has been successfully sent.");
                  break;
              case 6:
                  System.out.println("Events you entered contains event with no attendees.");
                  break;
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
