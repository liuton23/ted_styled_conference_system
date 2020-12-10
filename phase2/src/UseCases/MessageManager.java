package UseCases;

import Entities.Message;
import Entities.User;
import Entities.UserFactory.UserType;
import UseCases.MessageObserver.MarkType;
import UseCases.MessageObserver.MessageListener;
import UseCases.MessageObserver.MessageUpdate;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Manage all the messages
 */

public class MessageManager implements Serializable {
    private ArrayList<Message> messages;
    private ArrayList<String> messageNumStorage;

    // All the string representation of Attendee is its username

    /**
     * initiate a new message manager with a new empty message list.
     */
    public MessageManager(){
        messages = new ArrayList<Message>();
        messageNumStorage = new ArrayList<String>();
    }

    /**
     * Create a message base on sender, multiple recipients, and message content.
     * @param recipients a list of usernames of the recipients
     * @param sender username of the sender
     * @param text content of the message
     * @return an message object
     */
    public String createMessage(ArrayList<String> recipients, String sender, String text) {
        Message currMessage = new Message(sender, text);
        for (String a : recipients) {
            currMessage.setRecipients(a);
        }
        messageNumGenerator(currMessage);
        messages.add(currMessage);
        return currMessage.getMessageNumber();
    }

    public void deleteMessage(Message messageObj) {
        messages.remove(messageObj);
        messageNumStorage.remove(messageObj.getMessageNumber());
    }

    /**
     * reply a message given sender and content of the message.
     * @param m message text
     * @param sender sender's username
     * @param text content of the message
     * @return a replied message object
     */
    public Message reply(Message m, String sender, String text){
        Message message = new Message (sender, text);
        message.setRecipients(m.getSender());
        messageNumGenerator(message);
        messages.add(message);
        return message;
    } //This is not used for phase 1, but may be useful in phase 2

    /**
     * Gets a list of archived messages for a user
     * @param username username of this user
     * @return a list of archived messages
     */
    public ArrayList<String> getArchiveMessages(String username){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (getSenderAndRecipients(m).contains(username)){
                if (m.getArchived(username)) {
                    allMessages.add(m.getMessageNumber() + ": From " + m.getSender() + " To " +
                            recipientsBuilder(m.getRecipients()) + " {" + m.getText() +
                            "} @ " + m.getMessageTime().toString());
                }
            }
        }
        return allMessages;
    }


    /**
     * Return a list of all the messages send by this sender.
     * @param sender sender's username
     * @return a list of all the messages send by this sender
     */
    public ArrayList<String> getSendBy(String sender){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getSender().equals(sender) && !m.getDeletedForSender()){
                allMessages.add(m.getMessageNumber() + ": To " + recipientsBuilder(m.getRecipients()) + " {" + m.getText() +
                        "} @ " + m.getMessageTime().toString());
            }
        }
        return allMessages;
    }

    /**
     * Return a list of all the messages received by this user
     * @param recipient recipient's username
     * @return a list of all the messages received by this user
     */
    public ArrayList<String> getReceivedBy(String recipient){
        ArrayList<Message> allMessagesObj = getAllReceivedBy(recipient);
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : allMessagesObj) {
            if (m.getEdited()) {
                allMessages.add(m.getMessageNumber() + ": From " + m.getSender() + " {" + m.getText() + "} @ " +
                        m.getMessageTime().toString() + " (edited)");
            } else {
                allMessages.add(m.getMessageNumber() + ": From " + m.getSender() + " {" + m.getText() + "} @ " +
                        m.getMessageTime().toString());
            }
            m.setRead(recipient, true);
        }
        return allMessages;
    }

    /**
     * Helper method which return a list of message objects a recipient has received so far
     * @param recipient the username of this recipient
     * @return a list of received message objects
     */
    private ArrayList<Message> getAllReceivedBy(String recipient){
        ArrayList<Message> allMessages = new ArrayList<Message>();
        for (Message m : messages) {
            if (m.getRecipients().contains(recipient)) {
                allMessages.add(m);
            }
        }
        return allMessages;
    }

    /**
     * Get a list of unread messages for a recipient
     * @param recipient username of this recipient
     * @return a list of unread messages
     */
    public ArrayList<String> getUnreadMessage(String recipient){
        ArrayList<Message> allMessagesObj = getAllReceivedBy(recipient);
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : allMessagesObj) {
            if (!m.getRead(recipient)) {
                if (m.getEdited()){
                    allMessages.add(m.getMessageNumber() + ": From " + m.getSender() + " {" + m.getText() + "} @ " +
                            m.getMessageTime().toString() + " (edited)");
                } else {
                    allMessages.add(m.getMessageNumber() + ": From " + m.getSender() + " {" + m.getText() + "} @ " +
                            m.getMessageTime().toString());
                }
                m.setRead(recipient, true);
            }
        }
        return allMessages;
    }

    /**
     * Return how many unread messages a user have
     * @param recipient username of this user
     * @return a number of how many unread messages for this user
     */
    public int getNumOfUnreadMessage(String recipient){
        ArrayList<Message> allMessagesObj = getAllReceivedBy(recipient);
        int i = 0;
        for (Message m: allMessagesObj){
            if (!m.getRead(recipient)){
                i++;
            }
        }
        return i;
    }



    /**
     * Gets all messages from sender to recipient.
     * @param recipient recipient's username
     * @param sender sender's username
     * @return a list of all messages from sender to recipient
     */

    public ArrayList<String> getAllMessagesFrom(String recipient, String sender){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getSender().equals(sender) && m.getRecipients().contains(recipient)){
                if (m.getEdited()) {
                    allMessages.add(m.getMessageNumber() + ": {" + m.getText() + "} @ " + m.getMessageTime().toString()
                            + " (edited)");
                }
                m.setRead(recipient,true);
            }
        }
        return allMessages;
    }

    /**
     * Mark a message as a type depends on input
     * @param m message object
     * @param changer username of the changer
     * @param type a type of which message can be marked as
     */
    public void markAs(Message m, String changer, MarkType type){
        // precondition: the changer must be in the recipient list of this message.
        PropertyChangeListener messageListener = new MessageListener(changer);
        MessageUpdate messageUpdate = new MessageUpdate(m);
        messageUpdate.addObserver(messageListener);
        switch (type){
            case UNREAD:
                messageUpdate.markUnread(changer);
                break;
            case ARCHIVED:
                messageUpdate.markArchive(changer);
                break;
            case RECALL:
                deleteMessage(m);
                break;
        }
        messageUpdate.removeObserver(messageListener);
    }

    /**
     * Edit a sent message
     * @param m message object
     * @param newText updated text
     */
    public void editMessage(Message m, String newText){
        PropertyChangeListener messageListener = new MessageListener(m.getSender());
        MessageUpdate messageUpdate = new MessageUpdate(m);
        messageUpdate.addObserver(messageListener);
        m.setText(newText);
        m.reset();
        messageUpdate.editMessage(m.getSender(),newText);
        messageUpdate.removeObserver(messageListener);
    }

    /**
     * Gets the sender and recipients of a message
     * @param messageObj message object
     * @return a list of sender and recipients
     */
    public ArrayList<String> getSenderAndRecipients(Message messageObj){
        ArrayList<String> combined = new ArrayList<String>();
        combined.add(messageObj.getSender()); // at index 0, is the sender of this message
        combined.addAll(messageObj.getRecipients());
        return combined;
    }

    public String getContent(Message m){
        return m.getText();
    }


    /**
     * Private helper function
     * @param list list of strings
     * @return a string representation of this list.
     */
    private String recipientsBuilder(ArrayList<String> list){
        /* Pre-condition, we use this method under the condition that list is non-empty, so we don't need
        * empty list check */
        String x = "";
        int max = list.size();
        if (max == 1){
            return list.get(0);
        } else if (max <= 4){
            for (int i = 0; i < max - 2; i++){
                x += list.get(i) + ", ";
            }
            return x + list.get(max - 2) + " and " + list.get(max - 1);
        } else {
            for (int i = 0; i < 2; i++){
                x += list.get(i) + ", ";
            }
            return x + "..., " + list.get(max - 2) + " and " + list.get(max - 1);
        }
    }

    /**
     * Generate a unique message number (id)
     * @param message message object
     */
    public void messageNumGenerator(Message message){
        String x = "";
        int num = 10000 + (int)(Math.random() * (90000));
        x = "MSG" + num;
        if (messageNumStorage.contains(x)){
            messageNumGenerator(message);
        } else {
            messageNumStorage.add(x);
            message.setMessageNumber(x);
        }
    }

    /**
     * Given a message number returns a message object
     * @param messageNum a message number
     * @return a message obj
     */
    public Optional<Message> numToMessageObject(String messageNum) {
        for (Message m: messages){
            if (m.getMessageNumber().equals(messageNum)){
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }


    public static void main(String[] args){
        UserManager a = new UserManager();
        User josh = a.createAttendee("iamjosh", "4532dgtf", UserType.ATTENDEE);
        User rita = a.createAttendee("ritaishannie", "123456", UserType.ATTENDEE);
        User bob = a.createAttendee("Bob", "98321", UserType.ATTENDEE);
        MessageManager mas = new MessageManager();
        ArrayList<String> rs = new ArrayList<String>();
        rs.add("iamjosh");
        Message m = mas.numToMessageObject(mas.createMessage(rs,"ritaishannie","hello jesus")).get();
        Message newm = mas.reply(m, "iamjosh","hello, rita");
        Message c = mas.reply(newm, "ritaishannie", "I'll go to eaton tomorrow");
        mas.reply(c, "iamjosh", "I'm watching start up.");
        User org = a.createAttendee("lisa231", "iloveme", UserType.ORGANIZER);
        User sam = a.createAttendee("sam","76gtej", UserType.ATTENDEE);
        User go = a.createAttendee("gosh", "kihojsbc", UserType.ATTENDEE);
        ArrayList<String> att = new ArrayList<String>();
        att.add("iamjosh");
        att.add("ritaishannie");
        att.add("Bob");
        att.add("sam");
        Message meeting = mas.numToMessageObject(mas.createMessage
                (att,"lisa231","meeting starts in 10mins!!")).get();
        mas.reply(meeting,"ritaishannie","Got it!");
        System.out.println(mas.getReceivedBy("ritaishannie"));
        m.setRead("iamjosh", true);
        mas.markAs(m, "iamjosh",MarkType.UNREAD);
        mas.markAs(meeting, "ritaishannie",MarkType.ARCHIVED);
        mas.editMessage(m,"I am here");
        System.out.println(mas.getReceivedBy("iamjosh"));
        System.out.println(mas.getSendBy("lisa231"));
        System.out.println(mas.getAllMessagesFrom("ritaishannie","iamjosh"));
    }
}
