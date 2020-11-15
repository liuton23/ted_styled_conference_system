package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

    private ArrayList<String> recipients;
    private String sender;
    private String text;

    public Message (String sender, String text){
        this.sender = sender;
        this.text = text;
        this.recipients = new ArrayList<String>();
    }

    public void setRecipients(String recipient) {
        this.recipients.add(recipient);
    }

    public void setText(String newText){
        this.text = newText;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public String getText() {
        return text;
    }

    public static void main(String[] args) {
        //tests
        Message m = new Message("felix", "I like cat");
        System.out.println(m.getSender());
        m.setRecipients("rita");
        System.out.println(m.getRecipients().size());
        System.out.println(m.getText());
    }

}
