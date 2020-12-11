package Presenter;

import java.util.ArrayList;

/**
 * Display messages relevant for event signup/dropout and event viewing.
 */
public class SignUpPresenter {

    /**
     * Displays various messages depending on the input during event signup
     * @param message Type of message that will be displayed during event signup
     */
    public void printSignUpMessage(MessageType message){
        switch (message){
            case dropOut:
                System.out.println("Event Drop-out");
                break;
            case enterEventId:
                System.out.println("Enter Event ID:");
                break;
            case incorrectID:
                System.out.println("Incorrect ID. Please try again.");
                break;
            case incorrectUsername:
                System.out.println("Incorrect username. Please try again.");
                break;
            case signUp:
                System.out.println("Event Sign-up");
                break;
            case successfulDropOut:
                System.out.println("Successfully Dropped:");
            case viewAllEvents:
                System.out.println("View All Events");
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
     * Displays list of events depending on how events are sorted.
     * @param options list of sorted events.
     * @param way specify how the events are sorted.
     */
    public void displaySortedEvents(ArrayList<String> options, String way){
        switch(way) {
            case "time":
                System.out.println("Sort events by time");
                break;
            case "name":
                System.out.println("Sort events by name");
                break;
            case "speaker":
                System.out.println("Sort events by speaker");
                break;
        }
        for (String event: options){
            System.out.println(event);
        }
    }
}
