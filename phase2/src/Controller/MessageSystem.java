package Controller;

import Entities.*;
import Entities.Event;
import Entities.UserFactory.*;
import Presenter.*;
import UseCases.MessageObserver.MarkType;
import UseCases.UserManager;
import UseCases.EventManager;
import UseCases.MessageManager;

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
     */
    private void messageAttendee(String sender, String attendee, String text){
        Optional<User> obj1 = userManager.usernameToUserObject(sender);
        Optional<User> obj2 = userManager.usernameToUserObject(attendee);
        if (!obj1.isPresent()){
            messagePresenter.printMessageAttendee(1);
        } else if (!obj2.isPresent()){
            messagePresenter.printMessageAttendee(1);
        } else {
            User recipient = obj2.get();
            if (userManager.checkIsOrganizer(recipient)) {
                messagePresenter.printMessageAttendee(2); //"The message can not be sent to an Organizer."
            } else {
                ArrayList<String> att = new ArrayList<String>();
                att.add(attendee);
                String messageNum = messageManager.createMessage(att, sender, text);
                messagePresenter.displayNewMessageNum(messageNum); //"The message has been successfully sent."
            }
        }
    }


    //All the Organizer helper methods

    //helper


    /**
     * This helper method will let only organizer sends message to all speakers at once.
     * @param sender the username of the sender
     * @param text the content of the message
     */

    private void messageAllSpeakers(String sender, String text){
        Optional<User> obj = userManager.usernameToUserObject(sender);
        ArrayList<TalkAble> listOfSpeakers = userManager.getAllSpeakers();
        ArrayList<String> list = new ArrayList<String>();
        for (TalkAble s : listOfSpeakers){
            list.add(userManager.getUsername(s));
        }
        if (!obj.isPresent()){
            messagePresenter.printMessageAllSpeakers(1); /*"Incorrect username. Please try again."*/
        } else {
            User org = obj.get();
            if (!userManager.checkIsOrganizer(org)) {
                messagePresenter.printMessageAllSpeakers(2);/*"Only Organizer can message all speakers."*/
            } else {
                String newMessage = messageManager.createMessage(list, sender, text);
                messagePresenter.displayNewMessageNum(newMessage); /*"The message has been successfully sent."*/
            }
        }
    }

    /**
     * This helper method only let organizer sends message to all attendees in the system at once.
     * @param sender username of sender
     * @param text the content of the message
     */

    private void messageAllAttendees(String sender, String text){
        Optional<User> obj = userManager.usernameToUserObject(sender);
        if (!obj.isPresent()){
            messagePresenter.printMessageAllAttendees(1); /*"Incorrect username. Please try again."*/
        }else {
            User org = obj.get();
            if (!userManager.checkIsOrganizer(org)) {
                messagePresenter.printMessageAllAttendees(2); /*"Only Organizer can message all attendees."*/
            }
            ArrayList<String> allAtt = new ArrayList<String>();
            ArrayList<AttendAble> allAttObj = userManager.getAllAttendees();
            for (AttendAble att : allAttObj) {
                allAtt.add(userManager.getUsername(att));
            }
            String num = messageManager.createMessage(allAtt, sender, text);
            messagePresenter.displayNewMessageNum(num); /*"The message has been successfully sent."*/
        }
    }

    //All the Speaker helper methods

    /**
     * This helper method only let speaker message all attendees from multiple events they speak at.
     * @param eventNames a list of names of events
     * @param sender username of the sender
     * @param text the content of the message
     */

    private void messageEventAttendees(ArrayList<String> eventNames, String sender, String text){
        Optional<User> obj = userManager.usernameToUserObject(sender);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> noAtt = new ArrayList<String>();
        ArrayList<String> notSpeakAt = new ArrayList<String>();

        if (!obj.isPresent()){
            messagePresenter.printMessageEventsAttendees(2);
            //"Incorrect username. Please try again.";
        } else {
            User se = obj.get();
            if (!(se instanceof Speaker)) {
                messagePresenter.printMessageEventsAttendees(3);
                //"Only speakers can sent messages to all attendees of their talks they give.";
            }
            for (String i : eventNames) {
                Optional<Event> eve = eventManager.nameToEvent(i);
                if (!eve.isPresent()) {
                    messagePresenter.printMessageEventsAttendees(4);
                    //"Event do not exist or spell the name wrong
                } else {
                    Event eventF = eve.get();
                    if (!(eventF instanceof SpeakerEvent)) {
                        messagePresenter.printMessageEventsAttendees(1);
                        // event with no speaker == event you do not speak at
                    }
                    if (((SpeakerEvent) eventF).getSpeaker().contains(sender)) {
                        if (eventManager.eventToAttendees(eventF).size() != 0) {
                            list.addAll(eventManager.eventToAttendees(eventF));
                        } else noAtt.add(i);
                    } else notSpeakAt.add(i);
                }
            }
            if (notSpeakAt.size() != 0) {
                messagePresenter.printMessageEventsAttendees(1);
                //contain event you do not speak at
            } else if (noAtt.size() != 0) {
                messagePresenter.printMessageEventsAttendees(6);
                //"no attendee at this event"
            } else {
                String num = messageManager.createMessage(list, sender, text);
                messagePresenter.displayNewMessageNum(num); //"The message has been successfully sent.";
            }
        }
    }


    // Activity main controller method below (depends on user input)

    /**
     * Message menu to view and send messages.
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
        // username which do not exist will not access this function
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
                messageEventAttendees(events,username,message);
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
                    markAs(username,MarkType.UNREAD);
                    break;
                case "A":
                    markAs(username,MarkType.ARCHIVED);
                    break;
                case "E":
                    editMessage(username);
                    break;
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
        messageAttendee(username,user,message);
    }

    /**
     * Messages all speakers.
     * @param username username of the sender.
     */
    public void messageAllSpeaker(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printPleaseInputUsername");
        String message = obj.nextLine();
        messageAllSpeakers(username, message);
    }

    /**
     * Messages all users.
     * @param username username of the sender.
     */
    public void messageAllAtt(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printInputMessagePlz");
        String message = obj.nextLine();
        messageAllAttendees(username, message);
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

    private void markAs(String username, MarkType type){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("inputMessageNum");
        String messageNum = obj.nextLine();
        Optional<Message> obj1 = messageManager.numToMessageObject(messageNum);
        if (!obj1.isPresent()){
            messagePresenter.generalPrintHelperForMS("noSuchMessageNum");
        }else {
            Message obj2 = obj1.get();
            if (messageManager.getSenderAndRecipients(obj2).contains(username)) {
                messageManager.markAs(obj2, username, type);
            } else messagePresenter.generalPrintHelperForMS("cantMark");
        }
    }

    private void editMessage(String username){
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("inputMessageNum");
        String messageNum = obj.nextLine();
        Optional<Message> obj1 = messageManager.numToMessageObject(messageNum);
        if (!obj1.isPresent()){
            messagePresenter.generalPrintHelperForMS("noSuchMessageNum");
        }else {
            Message obj2 = obj1.get();
            if (messageManager.getSenderAndRecipients(obj2).get(0).equals(username)) {
                messagePresenter.displayOriginalMessage(messageManager.getContent(obj2));
                String newText = obj.nextLine();
                messageManager.editMessage(obj2, newText);
            } else {
                messagePresenter.generalPrintHelperForMS("cantEdit");
            }
        }
    }
}

