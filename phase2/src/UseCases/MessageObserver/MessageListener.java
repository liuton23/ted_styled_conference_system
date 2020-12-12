package UseCases.MessageObserver;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * a listener class keep tracks of the changes
 */
public class MessageListener implements PropertyChangeListener {

    private String changer;

    public MessageListener(String changer){
        this.changer = changer;
    }


    /**
     * Sends message about the details of event change
     * @param evt the event of change
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("read status") || evt.getPropertyName().equals("archive status")) {
            System.out.println("Message system observed a change in " +
                    evt.getPropertyName() + " of this message for " + this.changer);
            System.out.println(
                    "This message has been marked as " + evt.getNewValue() + " for " + this.changer);
        } else if (evt.getPropertyName().equals("delete status")){
            System.out.println(
                    "This message has been " + evt.getNewValue() + " for " + this.changer);
        } else {
            System.out.println("Message system observed a change in " +
                    evt.getPropertyName() + " of this message." );
            System.out.println(
                    "This message content has been changed to: {" + evt.getNewValue() + "} by " + this.changer);
        }
        System.out.println();
    }
}
