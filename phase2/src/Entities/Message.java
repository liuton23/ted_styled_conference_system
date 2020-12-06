package Entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents an message objects in this system.
 */

public class Message implements Serializable {

    private ArrayList<String> recipients;
    private String sender;
    private String text;
    private LocalDateTime messageTime;
    private HashMap<String, Boolean> readDict;
    private HashMap<String, Boolean> archivedDict;
    private String messageNumber;

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
        this.readDict = new HashMap<String, Boolean>();
        this.archivedDict = new HashMap<String, Boolean>();
        archivedDict.put(sender,false);
    }


    /**
     * This method sets the recipient of this message
     * @param recipient the username of the recipient
     */

    public void setRecipients(String recipient) {
        this.recipients.add(recipient);
        this.readDict.put(recipient, false);
        this.archivedDict.put(recipient,false);
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

    public void setRead(String recipient, Boolean read){
        this.readDict.put(recipient,read);
    }

    public void setArchived(String changer, Boolean archived) {
        this.archivedDict.put(changer,archived);
    }

    public Boolean getRead(String recipient){
        return this.readDict.get(recipient);
    }

    public Boolean getArchived(String changer) {
        return this.archivedDict.get(changer);
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageNumber(){
        return this.messageNumber;
    }

    public void reset(){
        for (String recipient: recipients){
            this.readDict.put(recipient,false);
            this.archivedDict.put(recipient,false);
        }
    }

    /**
     * Return the message time.
     * @return the time of this message
     */

    public LocalDateTime getMessageTime(){
        return messageTime;
    }

    @Override
    public String toString() {
        return messageNumber;
    }


}
