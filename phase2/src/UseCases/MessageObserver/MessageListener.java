package UseCases.MessageObserver;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * a listener class keep tracks of the changes
 */
public class MessageListener implements PropertyChangeListener {

    private int markBin;

    /**
     * Empty constructor for message listener
     */
    public MessageListener() {
    }


    /**
     * changes mark bin number based on different Mark type. The mark bin number will
     * navigate which message to display on screen.
     *
     * @param evt the event of change
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "read status":
                markBin = 1;
                break;
            case "archive":
                markBin = 2;
                break;
            case "message content":
                markBin = 3;
                break;
            case "unarchive":
                markBin = 4;
                break;
        }
    }

    /**
     * getter for mark bin
     *
     * @return a integer from 1 to 4
     */
    public int getMarkBin() {
        return this.markBin;
    }

}
