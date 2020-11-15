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


    /**
     * Return a list of all the messages send by this sender.
     * @param sender sender's username
     * @return a list of all the messages send by this sender
     */
    public ArrayList<String> getSendBy(String sender){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getSender().equals(sender)){
                allMessages.add(m.getText());
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
                allMessages.add(m.getText());
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
                allMessages.add(m.getText());
            }
        }
        return allMessages;
    }


    public static void main(String[] args){
        //tests
        AttendeeManager a = new AttendeeManager();
        Attendee josh = a.createAttendee("iamjosh", "4532dgtf", false);
        Attendee rita = a.createAttendee("ritaishannie", "123456", false);
        MessageManager mas = new MessageManager();
        Message m = mas.createMessage("iamjosh","ritaishannie","hello jesus");
        Message newm = mas.reply(m, "iamjosh","hello, rita");
        Message c = mas.reply(newm, "ritaishannie", "I'll go to eaton tomorrow");
        mas.reply(c, "iamjosh", "I'm watching start up.");
        Attendee org = a.createAttendee("lisa231", "iloveme", true);
        ArrayList<String> att = new ArrayList<String>();
        att.add("iamjosh");
        att.add("ritaishannie");
        Message meeting = mas.createMessage(att,"lisa231","meeting starts in 10mins!!");
        mas.reply(meeting,"ritaishannie","Got it!");
        System.out.println(mas.getReceivedBy("ritaishannie"));
        System.out.println(mas.getSendBy("ritaishannie"));
        System.out.println(mas.getAllMessagesFrom("ritaishannie","iamjosh"));
    }
}
