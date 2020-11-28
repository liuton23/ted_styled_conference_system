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
    private PropertyChangeSupport observable;
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
        this.observable = new PropertyChangeSupport(this);
    }


    /*
     * Add a new observer to observe the changes to this class.
     * @param observer
     */
    public void addObserver(PropertyChangeListener observer) {
        observable.addPropertyChangeListener("read", observer);
    }


    /*
     * Remove an existing observer from the list of observers.
     * @param observer
     */
    public void removeObserver(PropertyChangeListener observer) {
        observable.removePropertyChangeListener(observer);
    }

    /*
     * Notify observers o the change event.
     * @param newEvent
     */
    public void notifyObservers (PropertyChangeEvent newEvent)
    {
        for ( PropertyChangeListener observer : observable.getPropertyChangeListeners())
            observer.propertyChange(newEvent);
    }

    public void markUnread(String recipient) {

        String oldRead  = "READ";
        String newRead = "UNREAD";
        this.readDict.put(recipient,false);
        PropertyChangeEvent newEvent = new PropertyChangeEvent (this, "read status", oldRead, newRead);
        notifyObservers (newEvent);

        /*
         * The following line does not work.
         *  observable.firePropertyChange("The location: ", oldLocation, newLocation);
         */
    }

    /**
     * This method sets the recipient of this message
     * @param recipient the username of the recipient
     */

    public void setRecipients(String recipient) {
        this.recipients.add(recipient);
        this.readDict.put(recipient, false);
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

    public void setRead(String recipient){
        if (!readDict.get(recipient)){
            this.readDict.put(recipient,true);
        }
    }

    public Boolean getRead(String recipient){
        return this.readDict.get(recipient);
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageNumber(){
        return this.messageNumber;
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
        return "From " + sender + ": " + text + " @ " +
                messageTime.toString();
    }






}
