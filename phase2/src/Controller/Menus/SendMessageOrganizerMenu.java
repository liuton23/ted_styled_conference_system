package Controller.Menus;

import java.util.ArrayList;

/**
 * Sending messages menu for organizers.
 */
public class SendMessageOrganizerMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Send to a (U)ser");
        options.add("Send to all (S)peakers");
        options.add("Send to all (A)ttendees");
        options.add("(R)ecall message");
        options.add("(B)ack");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("U");
        choices.add("S");
        choices.add("A");
        choices.add("R");
        choices.add("B");
        return choices;
    }
}
