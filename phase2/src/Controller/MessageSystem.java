package Controller;

import Controller.PromptBuilder.Prompt;
import Controller.PromptBuilder.PromptBuilder;
import Controller.PromptBuilder.PromptType;
import Entities.*;
import Entities.Event;
import Entities.UserFactory.*;
import Presenter.*;
import UseCases.MessageObserver.MarkType;
import UseCases.MessageObserver.MessageListener;
import UseCases.MessageObserver.MessageUpdate;
import UseCases.UserManager;
import UseCases.EventManager;
import UseCases.MessageManager;

import java.io.IOException;
import java.util.*;

/**
 * Control all the messages base on user's input
 */
public class MessageSystem extends Controller {

    MessageManager messageManager;
    UserManager userManager;
    EventManager eventManager;
    private MessagePresenter messagePresenter = new MessagePresenter();
    private MessageListener messageListener = new MessageListener();

    /**
     * Create an instance of MessageSystem
     *
     * @param messageManager MessageManager
     * @param userManager    AttendeeManager
     * @param eventManager   EventManager
     */

    public MessageSystem(MessageManager messageManager, UserManager userManager,
                         EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    // General message helper methods (Suitable for all attendees, speakers, and organizers)

    /**
     * This helper method can let all attendee, speakers and organizers message a specific user which is not an organizer.
     *
     * @param sender   username of the sender
     * @param attendee username of the attendee's the sender want to send message to
     * @param text     the content of the message
     */
    private void messageAttendee(String sender, String attendee, String text) {
        Optional<User> obj1 = userManager.usernameToUserObject(sender);
        Optional<User> obj2 = userManager.usernameToUserObject(attendee);
        if (!obj1.isPresent()) {
            messagePresenter.printMessageAttendee(1);
        } else if (!obj2.isPresent()) {
            messagePresenter.printMessageAttendee(1);
        } else {
            User recipient = obj2.get();
            if (userManager.checkIsOrganizer(recipient)) {
                messagePresenter.printMessageAttendee(2); //"The message can not be sent to an Organizer."
            } else {
                ArrayList<String> att = new ArrayList<>();
                att.add(attendee);
                int messageNum = messageManager.createMessage(att, sender, text);
                messagePresenter.displayNewMessageNum(messageNum); //"The message has been successfully sent."
            }
        }
    }

    //All the Organizer helper methods

    /**
     * This helper method will let only organizer sends message to all speakers at once.
     *
     * @param sender the username of the sender
     * @param text   the content of the message
     */

    private void messageAllSpeakers(String sender, String text) {
        Optional<User> obj = userManager.usernameToUserObject(sender);
        ArrayList<TalkAble> listOfSpeakers = userManager.getAllSpeakers();
        ArrayList<String> list = new ArrayList<>();
        for (TalkAble s : listOfSpeakers) {
            list.add(userManager.getUsername(s));
        }
        if (!obj.isPresent()) {
            messagePresenter.printMessageAllSpeakers(1); /*"Incorrect username. Please try again."*/
        } else {
            User org = obj.get();
            if (!userManager.checkIsOrganizer(org)) {
                messagePresenter.printMessageAllSpeakers(2);/*"Only Organizer can message all speakers."*/
            } else {
                int newMessage = messageManager.createMessage(list, sender, text);
                messagePresenter.displayNewMessageNum(newMessage); /*"The message has been successfully sent."*/
            }
        }
    }

    /**
     * This helper method only let organizer sends message to all attendees in the system at once.
     *
     * @param sender username of sender
     * @param text   the content of the message
     */

    private int messageAllAttendees(String sender, String text) {
        Optional<User> obj = userManager.usernameToUserObject(sender);
        if (!obj.isPresent()) {
            return 1; /*"Incorrect username. Please try again."*/
        } else {
            User org = obj.get();
            if (!userManager.checkIsOrganizer(org)) {
                return 2; /*"Only Organizer can message all attendees."*/
            }
            ArrayList<String> allAtt = new ArrayList<>();
            ArrayList<AttendAble> allAttObj = userManager.getAllAttendees();
            for (AttendAble att : allAttObj) {
                allAtt.add(userManager.getUsername(att));
            }
            return messageManager.createMessage(allAtt, sender, text); /*"The message has been successfully sent."*/
        }
    }

    //All the Speaker helper methods

    /**
     * This helper method only let speaker message all attendees from multiple events they speak at.
     *
     * @param eventNames a list of names of events
     * @param sender     username of the sender
     * @param text       the content of the message
     */

    private int messageEventAttendees(ArrayList<String> eventNames, String sender, String text) {
        Optional<User> obj = userManager.usernameToUserObject(sender);
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> noAtt = new ArrayList<>();
        ArrayList<String> notSpeakAt = new ArrayList<>();

        if (!obj.isPresent()) {
            return 2;
            //"Incorrect username. Please try again.";
        } else {
            User se = obj.get();
            if (!(se instanceof Speaker)) {
                return 1;
                //"Only speakers can sent messages to all attendees of their talks they give.";
            }
            for (String i : eventNames) {
                Optional<Event> eve = eventManager.nameToEvent(i);
                if (!eve.isPresent()) {
                    return 4;
                    //"Event do not exist or spell the name wrong
                } else {
                    Event eventF = eve.get();
                    if (!(eventF instanceof SpeakerEvent)) {
                        return 1;
                        // event with no speaker == event you do not speak at
                    }
                    if (eventManager.getSpeakers((SpeakerEvent) eventF).contains(sender)) {
                        if (eventManager.eventToAttendees(eventF).size() != 0) {
                            list.addAll(eventManager.eventToAttendees(eventF));
                        } else noAtt.add(i);
                    } else notSpeakAt.add(i);
                }
            }
            if (notSpeakAt.size() != 0) {
                return 1;
                //contain event you do not speak at
            } else if (noAtt.size() != 0) {
                return 6;
                //"no attendee at this event"
            } else {
                return messageManager.createMessage(list, sender, text);
                //"The message has been successfully sent.";
            }
        }

    }


    // Activity main controller method below (depends on user input)

    /**
     * Message menu to view and send messages.
     *
     * @param username username of <code>Attendee</code>.
     */
    public void messageActivity(String username) throws IOException {
        messagePresenter.displayNumOfNew(messageManager.getNumOfUnreadMessage(username));
        System.out.println();
        boolean messaging = true;
        PromptBuilder promptBuilder = new PromptBuilder();
        Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.mainMessageMenu);
        while (messaging) {
            String chosen = prompt.ask();
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

    /**
     * This method is a prompt method for message users
     *
     * @param username the username of current user
     * @throws IOException username do not exist
     */

    public void messageUser(String username) throws IOException {
        boolean messagingOther = true;
        User user = userManager.usernameToUserObject(username).get();
        PromptBuilder promptBuilder = new PromptBuilder();
        // username which do not exist will not access this function
        while (messagingOther) {
            String chosen;
            if (user instanceof TalkAble) {
                Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.sendMessageSpeakerMenu);
                chosen = prompt.ask();
            } else if (user instanceof OrganizeAble) {
                Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.sendMessageOrganizerMenu);
                chosen = prompt.ask();
            } else {
                Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.sendMessageAttendeeMenu);
                chosen = prompt.ask();
            }

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
                    messageEventAllAtt(username, events);
                    save();
                    break;
                case "R":
                    markAs(username, MarkType.RECALL);
                    save();
                    break;
                case "B":
                    messagingOther = false;
                    break;
            }
        }

    }

    /**
     * Messages all users attending a specific event.
     *
     * @param username username of the sender.
     */
    private void messageEventAllAtt(String username, ArrayList<String> events) throws IOException {
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printInputEventName");
        events.add(obj.nextLine().trim());
        PromptBuilder promptBuilder = new PromptBuilder();
        Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.wishToSendMoreEventMenu);
        String chosen = prompt.ask();
        switch (chosen) {
            case "S":
                messageEventAllAtt(username, events);
                save();
                break;
            case "C":
                messagePresenter.generalPrintHelperForMS("printInputMessagePlz");
                String message = obj.nextLine().trim();
                int i = messageEventAttendees(events, username, message);
                if (i > 6) { // if it's a five-digit int, message is created we need to display the number
                    messagePresenter.displayNewMessageNum(i);
                } else {
                    messagePresenter.printMessageEventsAttendees(i);
                }
                save();
                break;
        }
    }

    /**
     * View message menu to view sent and received messages, and messages sent to <code>Attendee</code> with username
     * <code>username</code> by another <code>Attendee</code>.
     *
     * @param username username of <code>Attendee</code> viewing the messages.
     */
    private void viewMessages(String username) throws IOException {
        boolean viewingMessage = true;
        while (viewingMessage) {
            PromptBuilder promptBuilder = new PromptBuilder();
            Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.viewMessageMenu);
            String chosen = prompt.ask();
            switch (chosen) {
                case "S":
                    ArrayList<String> messagesS = messageManager.getSendBy(username);
                    if (messagesS.size() == 0) {
                        messagePresenter.generalPrintHelperForMS("printNoSentForU");
                    } else messagePresenter.displayListOfMessage(messagesS);
                    System.out.println();
                    save();
                    break;
                case "R":
                    ArrayList<String> messagesR = messageManager.getReceivedBy(username);
                    if (messagesR.size() == 0) {
                        messagePresenter.generalPrintHelperForMS("printNoRecForU");
                    } else messagePresenter.displayListOfMessage(messagesR);
                    System.out.println();
                    save();
                    break;
                case "U":
                    ArrayList<String> messagesU = messageManager.getUnreadMessage(username);
                    if (messagesU.size() == 0) {
                        messagePresenter.displayNumOfNew(0);
                    } else messagePresenter.displayListOfMessage(messagesU);
                    System.out.println();
                    save();
                    break;
                case "M":
                    markAs(username, MarkType.UNREAD);
                    save();
                    break;
                case "A":
                    markAs(username, MarkType.ARCHIVED);
                    save();
                    break;
                case "E":
                    editMessage(username);
                    save();
                    break;
                case "F":
                    viewFrom(username);
                    save();
                    break;
                case "H":
                    getArchived(username);
                    save();
                    break;
                case "O":
                    messagePresenter.generalPrintHelperForMS("hereAreArchived");
                    getArchived(username);
                    removeArchive(username);
                    save();
                    break;
                case "B":
                    viewingMessage = false;
                    break;
            }
        }
    }


    /**
     * Messages a single user.
     *
     * @param username username of the sender.
     */
    public void messageOneUser(String username) {
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printPleaseInputUsername");
        String user = obj.nextLine();
        messagePresenter.generalPrintHelperForMS("printInputMessagePlz");
        String message = obj.nextLine();
        messageAttendee(username, user, message);
    }

    /**
     * Messages all speakers.
     *
     * @param username username of the sender.
     */
    public void messageAllSpeaker(String username) {
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printPleaseInputUsername");
        String message = obj.nextLine();
        messageAllSpeakers(username, message);
    }

    /**
     * Messages all users.
     *
     * @param username username of the sender.
     */
    public void messageAllAtt(String username) {
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printInputMessagePlz");
        String message = obj.nextLine();
        int i = messageAllAttendees(username, message);
        if (i > 3) { // if it's a five-digit int, message is created we need to display the number
            messagePresenter.displayNewMessageNum(i);
        } else {
            messagePresenter.printMessageAllAttendees(i);
        }
    }

    /**
     * Displays messages that were sent to an <code>Attendee</code> with username <code>username</code> by a specific
     * <code>Attendee</code>.
     *
     * @param username username of <code>Attendee</code> viewing the messages.
     */
    public void viewFrom(String username) {
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("printPleaseInputUsername");
        String user = obj.nextLine();
        Optional<User> obj1 = userManager.usernameToUserObject(user);
        if (!obj1.isPresent()) {
            messagePresenter.generalPrintHelperForMS("printIncorrectUsername");
        } else {
            ArrayList<String> messageF = messageManager.getAllMessagesFrom(username, user);
            if (messageF.size() == 0) {
                messagePresenter.thereAreNoMessForUFrom(user);
            } else messagePresenter.displayListOfMessage(messageF);
        }
        System.out.println();
    }

    /**
     * This method allow a user to mark a message as Archive or Unread
     *
     * @param username the username of current user
     * @param type     the type the user want to mark a message as
     */
    private void markAs(String username, MarkType type) {
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("inputMessageNum");
        String messageNum = obj.nextLine().toUpperCase();
        Optional<Message> obj1 = messageManager.numToMessageObject(messageNum);
        if (!obj1.isPresent()) {
            messagePresenter.generalPrintHelperForMS("noSuchMessageNum");
        } else {
            Message obj2 = obj1.get();
            if (!messageManager.getSenderAndRecipients(obj2).contains(username)) {
                messagePresenter.generalPrintHelperForMS("cantMark");
            } else if (messageManager.getSenderAndRecipients(obj2).get(0).equals(username) && type == MarkType.UNREAD) {
                messagePresenter.generalPrintHelperForMS("cantUnread");
            } else if (!messageManager.getSenderAndRecipients(obj2).get(0).equals(username) && type == MarkType.RECALL) {
                messagePresenter.generalPrintHelperForMS("cantRecall");
            } else {
                if (type == MarkType.RECALL) {
                    messageManager.deleteMessage(obj2);
                    messagePresenter.generalPrintHelperForMS("recalled");
                } else {
                    observeMarkAs(obj2, username, type);
                }
            }
        }
    }

    /**
     * private helper method for let messageUpdate update message and notify changes
     *
     * @param m       message object
     * @param changer username of the changer
     * @param type    a type of which message can be marked as
     */
    private void observeMarkAs(Message m, String changer, MarkType type) {
        // precondition: the changer must be in the recipient list of this message.
        // Start observing
        MessageUpdate messageUpdate = new MessageUpdate(m);
        messageUpdate.addObserver(messageListener);
        switch (type) {
            case UNREAD:
                messageUpdate.markUnread(changer);
                break;
            case ARCHIVED:
                messageUpdate.markArchive(changer);
                break;
            case UNARCHIVED:
                messageUpdate.removeArchive(changer);
                break;
        }
        messagePresenter.displayMarkAs(messageListener.getMarkBin(), changer);
        messageUpdate.removeObserver(messageListener);
    }

    /**
     * This method edit sent message. A message can only be edited by its sender
     *
     * @param username the username of current user
     */
    private void editMessage(String username) {
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("inputMessageNum");
        String messageNum = obj.nextLine().toUpperCase();
        Optional<Message> obj1 = messageManager.numToMessageObject(messageNum);
        if (!obj1.isPresent()) {
            messagePresenter.generalPrintHelperForMS("noSuchMessageNum");
        } else {
            Message obj2 = obj1.get();
            if (messageManager.getSenderAndRecipients(obj2).get(0).equals(username)) {
                messagePresenter.displayOriginalMessage(messageManager.getContent(obj2));
                String newText = obj.nextLine();
                // start observing the changes
                MessageUpdate messageUpdate = new MessageUpdate(obj2);
                messageUpdate.addObserver(messageListener);
                messageUpdate.editMessage(newText);
                messagePresenter.displayMarkAs(messageListener.getMarkBin(), username);
                messageUpdate.removeObserver(messageListener);
                // end observing
            } else {
                messagePresenter.generalPrintHelperForMS("cantEdit");
            }
        }
    }

    /**
     * Displays archived message;
     *
     * @param username username of current user
     */
    private void getArchived(String username) {
        ArrayList<String> messagesH = messageManager.getArchiveMessages(username);
        if (messagesH.size() == 0) {
            messagePresenter.generalPrintHelperForMS("noArchive");
        } else messagePresenter.displayListOfMessage(messagesH);
        System.out.println();
    }

    /**
     * change an archived message to not archived
     *
     * @param username username of current user
     */
    private void removeArchive(String username) {
        Scanner obj = new Scanner(System.in);
        messagePresenter.generalPrintHelperForMS("inputMessageNum");
        String messageNum = obj.nextLine().toUpperCase();
        Optional<Message> obj1 = messageManager.numToMessageObject(messageNum);
        if (!obj1.isPresent()) {
            messagePresenter.generalPrintHelperForMS("noSuchMessageNum");
        } else {
            Message obj2 = obj1.get();
            if (!messageManager.getArchivedObj(username).contains(obj2)) {
                messagePresenter.generalPrintHelperForMS("cantUnArchive");
            } else {
                observeMarkAs(obj2, username, MarkType.UNARCHIVED);
            }
        }
    }

}

