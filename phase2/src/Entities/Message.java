package Entities;

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
    private Boolean edited;
    private String messageNumber;

    /**
     * Create an instance of message
     *
     * @param sender the username of sender
     * @param text   the content of the message
     */

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
        this.recipients = new ArrayList<String>();
        this.messageTime = LocalDateTime.now();
        this.readDict = new HashMap<String, Boolean>();
        this.archivedDict = new HashMap<String, Boolean>();
        archivedDict.put(sender, false);
        this.edited = false;
    }


    /**
     * This method sets the recipient of this message
     *
     * @param recipient the username of the recipient
     */

    public void setRecipients(String recipient) {
        this.recipients.add(recipient);
        this.readDict.put(recipient, false);
        this.archivedDict.put(recipient, false);

    }

    /**
     * This method resets the content of the message.
     *
     * @param newText new text message
     */

    public void setText(String newText) {
        this.text = newText;
    }


    /**
     * This method gets the username of the sender
     *
     * @return username of the sender
     */

    public String getSender() {
        return sender;
    }

    /**
     * This method gets an arraylist of usernames of the recipients.
     *
     * @return an arraylist of usernames of the recipients
     */
    public ArrayList<String> getRecipients() {
        return recipients;
    }

    /**
     * This method gets the content of the message.
     *
     * @return the text of the message
     */

    public String getText() {
        return text;
    }

    /**
     * set read status base on a recipient
     *
     * @param recipient username of the recipient
     * @param read      indicates read or not
     */
    public void setRead(String recipient, Boolean read) {
        this.readDict.put(recipient, read);
    }

    /**
     * set archived status
     *
     * @param changer  username of the changer
     * @param archived whether its archived
     */
    public void setArchived(String changer, Boolean archived) {
        this.archivedDict.put(changer, archived);
    }

    /**
     * get read status given a recipient
     *
     * @param recipient username of the recipient
     * @return true if read fot this user
     */
    public Boolean getRead(String recipient) {
        return this.readDict.get(recipient);
    }

    /**
     * get archived status given a user
     *
     * @param changer username
     * @return true if archived for this user
     */
    public Boolean getArchived(String changer) {
        return this.archivedDict.get(changer);
    }

    /**
     * set message to a unique 8-digit number
     *
     * @param messageNumber string of the number
     */
    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    /**
     * get this message number
     *
     * @return message number
     */
    public String getMessageNumber() {
        return this.messageNumber;
    }

    /**
     * reset status of read and archived for all recipient and sender after edited
     */
    public void reset() {
        for (String recipient : recipients) {
            this.readDict.put(recipient, false);
            this.archivedDict.put(recipient, false);
        }
        this.edited = true;
    }

    /**
     * get if this message is edited
     *
     * @return true if edited
     */
    public Boolean getEdited() {
        return this.edited;
    }

    /**
     * Return the message time.
     *
     * @return the time of this message
     */

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

}
