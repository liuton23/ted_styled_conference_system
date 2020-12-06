package UseCases.MessageObserver;

import Entities.Message;
import sun.management.jmxremote.ConnectorBootstrap;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MessageUpdate {
    private Message message;
    private PropertyChangeSupport observable;


    /*
     * Add a new observer to observe the changes to this class.
     * @param observer
     */
    public MessageUpdate (Message m) {
        this.message = m;
        this.observable = new PropertyChangeSupport(this);
    }


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

    public void markUnread(String recipient) {

        String oldRead  = "Read";
        String newRead = "unread";
        message.setRead(recipient, false);
        PropertyChangeEvent newEvent = new PropertyChangeEvent (this, "read status", oldRead, newRead);
        notifyObservers (newEvent);
    }

    public void markArchive(String changer) {
        String oldRead  = "Not archived";
        String newRead = "archived";
        message.setArchived(changer,true);
        PropertyChangeEvent newEvent = new PropertyChangeEvent (this, "archive status", oldRead, newRead);
        notifyObservers (newEvent);
    }

    public void editMessage(String sender, String newText){
        String oldMessage = message.getText();
        message.setText(newText);
        PropertyChangeEvent newEvent = new PropertyChangeEvent (this, "message content",
                oldMessage, newText);
        notifyObservers (newEvent);
    }
}
