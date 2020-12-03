package Controller;

import Entities.*;
import Entities.Event;
import Entities.UserFactory.*;
import Presenter.*;
import UseCases.UserManager;
import UseCases.EventManager;
import UseCases.MessageManager;

import java.beans.PropertyChangeEvent;
import java.util.*;

/**
 * Control all the messages base on user's input
 */
public class MessageSystem extends Controller {

    MessageManager messageManager;
    UserManager userManager;
    EventManager eventManager;
    private MessagePresenter messagePresenter = new MessagePresenter();

    /**
     * Create an instance of MessageSystem
     * @param messageManager MessageManager
     * @param userManager AttendeeManager
     * @param eventManager EventManager
     */

    public MessageSystem(MessageManager messageManager, UserManager userManager,
                         EventManager eventManager){
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    // General message helper methods (Suitable for all attendees, speakers, and organizers)

    /**
     * This helper method can let all attendee, speakers and organizers message a specific user which is not an organizer.
     * @param sender username of the sender
     * @param attendee username of the attendee's the sender want to send message to
     * @param text the content of the message
     * @return integer which will send to presenter and present corresponding message
     */
    private int messageAttendee(String sender, String attendee, String text){
        Optional<User> obj1 = userManager.usernameToUserObject(sender);
        Optional<User> obj2 = userManager.usernameToUserObject(attendee);
        if (!obj1.isPresent()){
            return 1; //"Incorrect username. Please try again."
        } else if (!obj2.isPresent()){
            return 1; //"Incorrect username. Please try again.";
        }
        User recipient = obj2.get();
        if (userManager.checkIsOrganizer(recipient)){
            return 2; //"The message can not be sent to an Organizer."
        } else {
            ArrayList<String> att = new ArrayList<String>();
            att.add(attendee);
            messageManager.createMessage(att, sender, text);
            return 3; //"The message has been successfully sent."
        }
    }


    //All the Organizer helper methods

    //helper


    /**
     * This helper method will let only organizer sends message to all speakers at once.
     * @param sender the username of the sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the corresponding messages
     */

    private int messageAllSpeakers(String sender, String text){
        Optional<User> obj = userManager.usernameToUserObject(sender);
        ArrayList<TalkAble> listOfSpeakers = userManager.getAllSpeakers();
        ArrayList<String> list = new ArrayList<String>();
        for (TalkAble s : listOfSpeakers){
            list.add(userManager.getUsername((User) s));
        }
        if (!obj.isPresent()){
            return 1; /*"Incorrect username. Please try again."*/
        }
        User org = (User) obj.get();
        if (!userManager.checkIsOrganizer(org)){
            return 2;/*"Only Organizer can message all speakers."*/
        } else {
            messageManager.createMessage(list, sender, text);
            return 3; /*"The message has been successfully sent."*/
        }
    }

    /**
     * This helper method only let organizer sends message to all attendees in the system at once.
     * @param sender username of sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the crresponding messages
     */

    private int messageAllAttendees(String sender, String text){
        Optional<User> obj = userManager.usernameToUserObject(sender);
        if (!obj.isPresent()){
            return 1; /*"Incorrect username. Please try again."*/
        }
        User org = obj.get();
        if (!userManager.checkIsOrganizer(org)) {
            return 2; /*"Only Organizer can message all attendees."*/
        }
        ArrayList<String> allAtt = new ArrayList<String>();
        ArrayList<AttendAble> allAttObj = userManager.getAllAttendees();
        for(AttendAble att: allAttObj){
            allAtt.add(userManager.getUsername((User) att));
        }
        messageManager.createMessage(allAtt,sender,text);
        return 3; /*"The message has been successfully sent."*/

    }

    //All the Speaker helper methods

    /**
     * This helper method only let speaker message all attendees from multiple events they speak at.
     * @param eventNames a list of names of events
     * @param sender username of the sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the corresponding messages
     */

    private int messageEventAttendees(ArrayList<String> eventNames, String sender, String text){
        Optional<User> obj = userManager.usernameToUserObject(sender);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> noAtt = new ArrayList<String>();
        ArrayList<String> notSpeakAt = new ArrayList<String>();

        if (!obj.isPresent()){
            return 2; //"Incorrect username. Please try again.";
        }
        User se = obj.get();
        if (!(se instanceof Speaker)){
            return 3; //"Only speakers can sent messages to all attendees of their talks they give.";
        }
        for (String i : eventNames){
            Optional<Event> eve = eventManager.nameToEvent(i);
            if (!eve.isPresent()){
                return 4; //"Event do not exist or spell the name wrong
            }
            Event eventF = eve.get();
            if (!(eventF instanceof SpeakerEvent)){
                return 1; // event with no speaker == event you do not speak at
            }
            if (((SpeakerEvent) eventF).getSpeaker().contains(sender)) {
                if (eventManager.eventToAttendees(eventF).size() != 0) {
                    list.addAll(eventManager.eventToAttendees(eventF));
                } else noAtt.add(i);
            } else notSpeakAt.add(i);
        }
        if (notSpeakAt.size() != 0){
            return 1; //contain event you do not speak at
        } else if (noAtt.size() != 0){
            return 6; //"no attendee at this event"
        } else {
            messageManager.createMessage(list, sender, text);
            return 5; //"The message has been successfully sent.";
        }
    }


    // Activity main controller method below (depends on user input)

    /**
     * Messager menu to view and send messages.
     * @param username username of <code>Attendee</code>.
     */
    public void messageActivity(String username) {
        boolean messaging = true;
        while (messaging) {
            String chosen = askMenuInput(5);
            switch (chosen) {
                case "M":
                    messageUser(username);
                    break;
                case "V":
                    viewMessages(username);
                    break;
                case "B":
                    messaging = false;
                    break;
            }
        }
    }

    public void messageUser(String username){
        boolean messagingOther = true;
        User user = userManager.usernameToUserObject(username).get();
        while (messagingOther) {
            String chosen;
            if (user instanceof TalkAble) {
                chosen = askMenuInput(11);
            } else if (user instanceof OrganizeAble) {
                chosen = askMenuInput(10);
            } else
                chosen = askMenuInput(6);

            switch (chosen) {
                case "U":
                    messageOneUser(username);
                    save();
                    break;
                case "S":
                    messageAllSpeaker(username);
                    save();
                    break;
                case "A":
                    messageAllAtt(username);
                    save();
                    break;
                case "E":
                    ArrayList<String> events = new ArrayList<>();
                    messageEventAllAtt(username,events);
                    save();
                case "B":
                    messagingOther = false;
                    break;
            }
        }

    }

    /**
     * Messages all users attending a specific event.
     * @param username username of the sender.
     */
    private void messageEventAllAtt(String username, ArrayList<String> events){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printInputEventName");
        events.add(obj.nextLine().trim());
        String chosen = askMenuInput(12);
        switch(chosen){
            case "S":
                messageEventAllAtt(username,events);
                break;
            case "C":
                messagePresenter.generalPrintHelperForMS("printInputMessagePlz");
                String message = obj.nextLine().trim();
                messagePresenter.printMessageEventsAttendees(messageEventAttendees(events,username,message));
                break;
        }
    }

    /**
     * View message menu to view sent and received messages, and messages sent to <code>Attendee</code> with username
     * <code>username</code> by another <code>Attendee</code>.
     * @param username username of <code>Attendee</code> viewing the messages.
     */
    private void viewMessages(String username){
        boolean viewingMessage = true;
        while (viewingMessage) {
            String chosen = askMenuInput(7);
            switch (chosen) {
                case "S":
                    ArrayList<String> messagesS = messageManager.getSendBy(username);
                    if (messagesS.size() == 0){
                        messagePresenter.generalPrintHelperForMS("printNoSentForU");
                    } else messagePresenter.displayListOfMessage(messagesS);
                    break;
                case "R":
                    ArrayList<String> messagesR = messageManager.getReceivedBy(username);
                    if (messagesR.size() == 0){
                        messagePresenter.generalPrintHelperForMS("printNoRecForU");
                    } else messagePresenter.displayListOfMessage(messagesR);
                    break;
                case "U":
                    ArrayList<String> messagesU = messageManager.getUnreadMessage(username);
                    if (messagesU.size() == 0){
                        messagePresenter.generalPrintHelperForMS("printNoRecForU");
                    } else messagePresenter.displayListOfMessage(messagesU);
                    break;
                case "M":
                    markAsUnread(username);
                case "A":
                    archiveMessage(username);
                case "F":
                    viewFrom(username);
                    break;
                case "B":
                    viewingMessage = false;
                    break;
            }
        }
    }



    /**
     * Messages a single user.
     * @param username username of the sender.
     */
    public void messageOneUser(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printPleaseInputUsername");
        String user = obj.nextLine();
        messagePresenter.generalPrintHelperForMS("printInputMessagePlz");
        String message = obj.nextLine();
        messagePresenter.printMessageAttendee(messageAttendee(username,user,message));
    }

    /**
     * Messages all speakers.
     * @param username username of the sender.
     */
    public void messageAllSpeaker(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printPleaseInputUsername");
        String message = obj.nextLine();
        messagePresenter.printMessageAllSpeakers(messageAllSpeakers(username, message));
    }

    /**
     * Messages all users.
     * @param username username of the sender.
     */
    public void messageAllAtt(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printInputMessagePlz");
        String message = obj.nextLine();
        messagePresenter.printMessageAllAttendees(messageAllAttendees(username, message));
    }

    /**
     * Displays messages that were sent to an <code>Attendee</code> with username <code>username</code> by a specific
     * <code>Attendee</code>.
     * @param username username of <code>Attendee</code> viewing the messages.
     */
    public void viewFrom(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printPleaseInputUsername");
        String user = obj.nextLine();
        Optional<User> obj1 = userManager.usernameToUserObject(user);
        if (!obj1.isPresent()){
            messagePresenter.generalPrintHelperForMS("printIncorrectUsername");
            viewFrom(username);
        } else {
            ArrayList<String> messageF = messageManager.getAllMessagesFrom(username, user);
            if (messageF.size() == 0){
                messagePresenter.thereAreNoMessForUFrom(user);
            } else messagePresenter.displayListOfMessage(messageF);
        }
    }

    private void markAsUnread(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("inputMessageNum");
        String messageNum = obj.nextLine();
        Optional<Message> obj1 = messageManager.numToMessageObject(messageNum);
        if (!obj1.isPresent()){
            messagePresenter.generalPrintHelperForMS("noSuchMessageNum");
        } else {
            Message obj2 = obj1.get();
            messageManager.markUnread(obj2,username);
        }
    }

    private void archiveMessage(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("inputMessageNum");
        String messageNum = obj.nextLine();
        Optional<Message> obj1 = messageManager.numToMessageObject(messageNum);
        if (!obj1.isPresent()){
            messagePresenter.generalPrintHelperForMS("noSuchMessageNum");
        } else {
            Message obj2 = obj1.get();
            messageManager.markArchived(obj2,username);
        }
    }

}
