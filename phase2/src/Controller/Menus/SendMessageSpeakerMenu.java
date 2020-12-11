package Controller.Menus;

import java.util.ArrayList;

/**
 * Sending messages menue for speakers.
 */
public class SendMessageSpeakerMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Send to a (U)ser");
        options.add("Send to all attendees in one or multiple (E)vents");
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
        choices.add("E");
        choices.add("B");
        choices.add("R");
        return choices;
    }
}
