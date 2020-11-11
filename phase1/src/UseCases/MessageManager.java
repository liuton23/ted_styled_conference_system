package UseCases;

import Entities.Attendee;
import Entities.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageManager implements Serializable {
    ArrayList<Message> messages;

    public MessageManager(){
        messages = new ArrayList<Message>();
    }

    public Message createMessage(Attendee recipient, Attendee sender, String text){
        Message currMessage = new Message(sender.getUsername(), text);
        currMessage.setRecipients(recipient.getUsername());
        messages.add(currMessage);
        return currMessage;
    }
    public Message createMessage(ArrayList<Attendee> recipients, Attendee sender, String text) {
        Message currMessage = new Message(sender.getUsername(), text);
        for (Attendee a : recipients) {
            currMessage.setRecipients(a.getUsername());
        }
        messages.add(currMessage);
        return currMessage;
    }
    public Message reply(Message m, Attendee sender, String text){
        Message message = new Message (sender.getUsername(), text);
        message.setRecipients(m.getSender());
        messages.add(message);
        return message;
    }


    public ArrayList<String> getSendBy(Attendee sender){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getSender().equals(sender.getUsername())){
                allMessages.add(m.getText());
            }
        }
        return allMessages;
    }

    public ArrayList<String> getReceivedBy(Attendee recipient){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getRecipients().contains(recipient.getUsername())){
                allMessages.add(m.getText());
            }
        }
        return allMessages;
    }


    public ArrayList<String> getAllMessagesFrom(Attendee recipient, Attendee sender){
        ArrayList<String> allMessages = new ArrayList<String>();
        for (Message m : messages){
            if (m.getSender().equals(sender.getUsername()) && m.getRecipients().contains(recipient.getUsername())){
                allMessages.add(m.getText());
            }
        }
        return allMessages;
    }


    public static void main(String[] args){
        //tests
        AttendeeManager a = new AttendeeManager();
        Attendee josh = a.createAttendee("josh", "iamjosh", "4532dgtf");
        Attendee rita = a.createAttendee("rita","ritaishannie", "123456");
        MessageManager mas = new MessageManager();
        Message m = mas.createMessage(josh,rita,"hello jesus");
        Message newm = mas.reply(m, josh,"hello, rita");
        Message c = mas.reply(newm, rita, "I'll go to eaton tomorrow");
        mas.reply(c, josh, "I'm watching start up.");
        Attendee org = a.createAttendee("lisa", "lisa231", "iloveme");
        ArrayList<Attendee> att = new ArrayList<Attendee>();
        att.add(josh);
        att.add(rita);
        Message meeting = mas.createMessage(att,org,"meeting starts in 10mins!!");
        mas.reply(meeting,rita,"Got it!");
        System.out.println(mas.getReceivedBy(rita));
        System.out.println(mas.getSendBy(rita));
        System.out.println(mas.getAllMessagesFrom(rita,josh));
    }
}
