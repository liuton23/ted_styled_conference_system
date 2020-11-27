package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents an message objects in this system.
 */

public class Message implements Serializable {

    private ArrayList<String> recipients;
    private String sender;
    private String text;
    private LocalDateTime messageTime;

    /**
     * Create an instance of message
     * @param sender the username of sender
     * @param text the content of the message
     */

    public Message (String sender, String text){
        this.sender = sender;
        this.text = text;
        this.recipients = new ArrayList<String>();
        this.messageTime = LocalDateTime.now();
    }

    /**
     * This method sets the recipient of this message
     * @param recipient the username of the recipient
     */

    public void setRecipients(String recipient) {
        this.recipients.add(recipient);
    }

    /**
     * This method resets the content of the message.
     * @param newText new text message
     */

    public void setText(String newText){
        this.text = newText;
    }


    /**
     * This method gets the username of the sender
     * @return username of the sender
     */

    public String getSender() {
        return sender;
    }

    /**
     * This method gets an arraylist of usernames of the recipients.
     * @return an arraylist of usernames of the recipients
     */
    public ArrayList<String> getRecipients() {
        return recipients;
    }

    /**
     * This method gets the content of the message.
     * @return the text of the message
     */

    public String getText() {
        return text;
    }

    /**
     * Return the message time.
     * @return the time of this message
     */

    public LocalDateTime getMessageTime(){
        return messageTime;
    }


/*
    public static void main(String[] args) {
        Message m = new Message("felix", "I like cat");
        System.out.println(m.getSender());
        m.setRecipients("rita");
        System.out.println(m.getRecipients().size());
        System.out.println(m.getText());
        System.out.println(m.getMessageTime());
    }

*/

}
