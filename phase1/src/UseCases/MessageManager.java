package UseCases;

import Entities.Attendee;
import Entities.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageManager implements Serializable {
    private ArrayList<Message> messages;

    // All the string representation of Attendee is its username

    public MessageManager(){
        messages = new ArrayList<Message>();
    }

    public Message createMessage(String recipient, String sender, String text){
        Message currMessage = new Message(sender, text);
        currMessage.setRecipients(recipient);
        messages.add(currMessage);
        return currMessage;
    }
    public Message createMessage(ArrayList<String> recipients, String sender, String text) {
        Message currMessage = new Message(sender, text);
        for (String a : recipients) {
            currMessage.setRecipients(a);
        }
        messages.add(currMessage);
        return currMessage;
    }
    public Message reply(Message m, String sender, String text){
        Message message = new Message (sender, text);
        message.setRecipients(m.getSender());
        messages.add(message);
        return message;
    }


    public ArrayList<String> getSendBy(String sender){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getSender().equals(sender)){
                allMessages.add(m.getText());
            }
        }
        return allMessages;
    }

    public ArrayList<String> getReceivedBy(String recipient){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getRecipients().contains(recipient)){
                allMessages.add(m.getText());
            }
        }
        return allMessages;
    }


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
        Attendee josh = a.createAttendee("josh", "iamjosh", "4532dgtf", false);
        Attendee rita = a.createAttendee("rita","ritaishannie", "123456", false);
        MessageManager mas = new MessageManager();
        Message m = mas.createMessage("iamjosh","ritaishannie","hello jesus");
        Message newm = mas.reply(m, "iamjosh","hello, rita");
        Message c = mas.reply(newm, "ritaishannie", "I'll go to eaton tomorrow");
        mas.reply(c, "iamjosh", "I'm watching start up.");
        Attendee org = a.createAttendee("lisa", "lisa231", "iloveme", true);
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
