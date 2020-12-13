package Presenter;

import java.util.ArrayList;

/**
 * This class displays statement for message system only
 */
public class MessagePresenter {

    /**
     * General helper method for printing messages depends on input
     *
     * @param input order
     */
    public void generalPrintHelperForMS(String input) {
        switch (input) {
            case "printPleaseInputUsername":
                System.out.println("Please input an username");
                break;
            case "printIncorrectUsername":
                System.out.println("Incorrect username, please try again");
                break;
            case "printInputMessagePlz":
                System.out.println("Please input your message");
                break;
            case "printInputEventName":
                System.out.println("Please enter an event name");
                break;
            case "printNoSentForU":
                System.out.println("There are no sent messages from you");
                break;
            case "printNoRecForU":
                System.out.println("There are no received messages for you");
                break;
            case "inputMessageNum":
                System.out.println("Please input a message number (e.g. MSG12345):");
                break;
            case "noSuchMessageNum":
                System.out.println("There's no such message corresponding to this message number");
                break;
            case "cantMark":
                System.out.println("You can not make this action on this message");
                break;
            case "cantEdit":
                System.out.println("You can not edit a message you did not send");
                break;
            case "noArchive":
                System.out.println("You have no archived messages");
                break;
            case "recalled":
                System.out.println("You have recalled this message");
                break;
            case "cantUnread":
                System.out.println("You can not unread a sent message from yourself");
                break;
            case "cantRecall":
                System.out.println("You can not recall this message");
                break;
            case "cantUnArchive":
                System.out.println("You can not remove a not archived message");
                break;
            case "hereAreArchived":
                System.out.println("Here are the messages you have archived");
                break;
        }
    }

    /**
     * print "There are no messages sent to you from " + user
     *
     * @param user username
     */
    public void thereAreNoMessForUFrom(String user) {
        System.out.println("There are no messages sent to you from " + user);
    }

    /**
     * Displays message reflecting the situation after messaging an attendee.
     *
     * @param messageAttendee output from the MessageSystem.messageAttendee() method
     */

    public void printMessageAttendee(int messageAttendee) {
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
     *
     * @param messageAllSpeakers output from MessageSystem.messageAllSpeakers()
     */
    public void printMessageAllSpeakers(int messageAllSpeakers) {
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
     *
     * @param messageAllAttendees output from MessageSystem.messageAllAttendees()
     */
    public void printMessageAllAttendees(int messageAllAttendees) {
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
     * Displays message reflecting the situation after messaging all attendees from multiple events.
     *
     * @param messageMultipleEventsAttendees output from overloading MessageSystem.messageEventAttendees()
     */
    public void printMessageEventsAttendees(int messageMultipleEventsAttendees) {
        switch (messageMultipleEventsAttendees) {
            case 1:
                System.out.println("Event(s) you entered contain event you do not speak at.");
                break;
            case 2:
                System.out.println("Incorrect username. Please try again.");
                break;
            case 3:
                System.out.println("Only speakers can sent messages to all attendees of their talks they give.");
                break;
            case 4:
                System.out.println("Event(s) you entered contain invalid event.(do not exists or spelled name wrong)");
                break;
            case 5:
                System.out.println("The message has been successfully sent.");
                break;
            case 6:
                System.out.println("Event(s) you entered contain event with no attendees.");
                break;
        }
    }

    /**
     * Method which displays user's messages from a list
     *
     * @param messages a list of messages to be displayed
     */

    public void displayListOfMessage(ArrayList<String> messages) {
        for (String m : messages) {
            System.out.println(m);
        }
    }

    /**
     * displays "your original message is ..."
     *
     * @param text text of message
     */
    public void displayOriginalMessage(String text) {
        System.out.println("Your Original Message is: {" + text + "}.");
        System.out.println("Please enter your new message:");
    }

    /**
     * display you have create a message with a message number
     *
     * @param num message number
     */
    public void displayNewMessageNum(int num) {
        System.out.println("You have successfully create this message, message number is MSG" + num);
    }

    /**
     * display new messages statement
     *
     * @param num number of new messages
     */
    public void displayNumOfNew(int num) {
        if (num == 1) {
            System.out.println("You have a new message");
        } else {
            System.out.println("You have " + num + " new messages");
        }
    }

    /**
     * Display marked as message based on type
     *
     * @param type     different type
     * @param username username of user
     */
    public void displayMarkAs(int type, String username) {
        if (type == 1) {
            System.out.println("Message system observed a change in read status of this message for " + username);
            System.out.println("This message has been marked as UNREAD for " + username);
        } else if (type == 2) {
            System.out.println("Message system observed a change in archive status of this message for " + username);
            System.out.println("This message has been marked as ARCHIVED for " + username);
        } else if (type == 3) {
            System.out.println("Message system observed a change in the content of this message.");
            System.out.println("This message content has been EDITED by " + username);
        } else if (type == 4) {
            System.out.println("Message system observed a change in archive status of this message for " + username);
            System.out.println("This message has been removed from " + username + "'s archived messages list");
        }
        System.out.println();
    }

}
