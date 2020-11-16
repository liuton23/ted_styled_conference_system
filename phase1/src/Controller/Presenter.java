package Controller;

import Entities.Event;

import java.util.ArrayList;

/**
 * Manages the formatting and output of most <code> Strings </code>.
 * @author Muhammad Hasnat Khan
 * @version 1.0
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

    /**
     * Displays <code>options</code> for the user.
     * @param options list of options for the user to choose from
     */
    public void prompt(ArrayList<String> options){
        int width = (options.size() / 4) + 1;
        System.out.println("Would you like to do:");
        for(int i = 0; i < options.size(); i++) {
            System.out.print(options.get(i) + "\t");
            if( (i + 1) % width == 0){
                System.out.println();
            }
        }
        System.out.print("Input: ");
    }

    public void displayMessages(String str){
        System.out.println(str);
    }
    public void displaySchedule(ArrayList<String> options, String message){
        System.out.println(message);
        for (String str: options){
            System.out.println(str);
        }
    }

    public void displayAllEvents(ArrayList<Event> events, String message){
        System.out.println(message);
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
        switch (changeSpeakerOutput){
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
    public void printSignUpMessage(boolean signUpOutput){
        if (signUpOutput){
            System.out.println("User was successfully signed up.");
        }
        else{
            System.out.println("Event is at capacity: unable to sign up user.");
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
                System.out.println("There is no such event.");
                break;
            case 2:
                System.out.println("Incorrect username. Please try again.");
                break;
            case 3:
                System.out.println("Only speakers can sent messages to all attendees of their talks they give.");
                break;
            case 4:
                System.out.println("You do not speak at this event.");
                break;
            case 5:
                System.out.println("The message has been successfully sent.");
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
                  System.out.println("Event list contains non-existed event.");
                  break;
              case 2:
                  System.out.println("Incorrect username. Please try again.");
                  break;
              case 3:
                  System.out.println("Only speakers can sent messages to all attendees of their talks they give.");
                  break;
              case 4:
                  System.out.println("Event list contains event which you do not speak at.");
                  break;
              case 5:
                  System.out.println("The message has been successfully sent.");
                  break;
          }
    }

}
