package UseCases.MessageObserver;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * a listener class keep tracks of the changes
 */
public class MessageListener implements PropertyChangeListener {

    private int markBin;

    public MessageListener(){ }


    /**
     * Sends message about the details of event change
     * @param evt the event of change
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("read status")){
            markBin = 1;
        } else if (evt.getPropertyName().equals("archive status")) {
            markBin = 2;
        } else {
            markBin = 3;
        }
    }
    public int getMarkBin(){
        return this.markBin;
    }

}
