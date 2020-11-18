package UseCases;

import Entities.Attendee;
import Entities.Message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Manage all the messages
 */

public class MessageManager implements Serializable {
    private ArrayList<Message> messages;

    // All the string representation of Attendee is its username

    /**
     * initiate a new message manager with a new empty message list.
     */

    public MessageManager(){
        messages = new ArrayList<Message>();
    }

    /**
     * Create a message base on sender, recipient, and message content.
     * @param recipient username of the recipient
     * @param sender username of the sender
     * @param text content of the message
     * @return an message object
     */

    public Message createMessage(String recipient, String sender, String text){
        Message currMessage = new Message(sender, text);
        currMessage.setRecipients(recipient);
        messages.add(currMessage);
        return currMessage;
    }

    /**
     * Create a message base on sender, multiple recipients, and message content.
     * @param recipients a list of usernames of the recipients
     * @param sender username of the sender
     * @param text content of the message
     * @return an message object
     */
    public Message createMessage(ArrayList<String> recipients, String sender, String text) {
        Message currMessage = new Message(sender, text);
        for (String a : recipients) {
            currMessage.setRecipients(a);
        }
        messages.add(currMessage);
        return currMessage;
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
        messages.add(message);
        return message;
    }
    //This is not used, might delete later


    /**
     * Return a list of all the messages send by this sender.
     * @param sender sender's username
     * @return a list of all the messages send by this sender
     */
    public ArrayList<String> getSendBy(String sender){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getSender().equals(sender)){
                allMessages.add("To " + recipientsBuilder(m.getRecipients()) + ": " + m.getText() +
                        " @ " + m.getMessageTime().toString());
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
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getRecipients().contains(recipient)){
                allMessages.add("From " + m.getSender() + ": " + m.getText() + " @ " +
                        m.getMessageTime().toString());
            }
        }
        return allMessages;
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
                allMessages.add(m.getText() + " @ " + m.getMessageTime().toString());
            }
        }
        return allMessages;
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


    public static void main(String[] args){
        AttendeeManager a = new AttendeeManager();
        Attendee josh = a.createAttendee("iamjosh", "4532dgtf", false);
        Attendee rita = a.createAttendee("ritaishannie", "123456", false);
        Attendee bob = a.createAttendee("Bob", "98321", false);
        MessageManager mas = new MessageManager();
        Message m = mas.createMessage("iamjosh","ritaishannie","hello jesus");
        Message newm = mas.reply(m, "iamjosh","hello, rita");
        Message c = mas.reply(newm, "ritaishannie", "I'll go to eaton tomorrow");
        mas.reply(c, "iamjosh", "I'm watching start up.");
        Attendee org = a.createAttendee("lisa231", "iloveme", true);
        Attendee sam = a.createAttendee("sam","76gtej", false);
        Attendee go = a.createAttendee("gosh", "kihojsbc", false);
        ArrayList<String> att = new ArrayList<String>();
        att.add("iamjosh");
        att.add("ritaishannie");
        att.add("Bob");
        att.add("sam");
        Message meeting = mas.createMessage(att,"lisa231","meeting starts in 10mins!!");
        mas.reply(meeting,"ritaishannie","Got it!");
        System.out.println(mas.getReceivedBy("ritaishannie"));
        System.out.println(mas.getSendBy("lisa231"));
        System.out.println(mas.getAllMessagesFrom("ritaishannie","iamjosh"));
    }

}
