package UseCases.MessageObserver;

import Entities.Message;
//import sun.management.jmxremote.ConnectorBootstrap;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MessageUpdate {
    private Message message;
    private PropertyChangeSupport observable;

    /**
     * Initialize a message update object
     * @param messageObj the message object
     */
    public MessageUpdate (Message messageObj) {
        this.message = messageObj;
        this.observable = new PropertyChangeSupport(this);
    }

    /*
     * Add a new observer to observe the changes to this class.
     * @param observer
     */
    public void addObserver(PropertyChangeListener observer) {
        observable.addPropertyChangeListener(observer);
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

    /**
     * Mark unread of a message for a recipient
     * @param recipient the username of the recipient
     */
    public void markUnread(String recipient) {

        String oldRead  = "Read";
        String newRead = "unread";
        message.setRead(recipient, false);
        PropertyChangeEvent newEvent = new PropertyChangeEvent (this, "read status", oldRead, newRead);
        notifyObservers (newEvent);
    }

    /**
     * Mark archive of a message for a user
     * @param changer the username of changer
     */
    public void markArchive(String changer) {
        String oldRead  = "Not archived";
        String newRead = "archived";
        message.setArchived(changer,true);
        PropertyChangeEvent newEvent = new PropertyChangeEvent (this, "archive status", oldRead, newRead);
        notifyObservers (newEvent);
    }

    /**
     * Edit message for a already sent message
     * @param sender the username of the sender
     * @param newText the updated text
     */

    public void editMessage(String sender, String newText){
        String oldMessage = message.getText();
        message.setText(newText);
        PropertyChangeEvent newEvent = new PropertyChangeEvent (this, "message content",
                oldMessage, newText);
        notifyObservers (newEvent);
    }


}
