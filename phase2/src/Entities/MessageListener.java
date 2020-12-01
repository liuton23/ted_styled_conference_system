package Entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MessageListener implements PropertyChangeListener {

    private String changer;

    public MessageListener(String changer){
        this.changer = changer;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Message system observed a change in " +
                evt.getPropertyName() + " of " + evt.getSource() + " for " + this.changer);

        System.out.println(
                evt.getOldValue() + " has changed to " + evt.getNewValue() + ". ");

        System.out.println();
    }
}
