package Controller.Menus;

import java.util.ArrayList;

/**
 * Events menu for organizers.
 */
public class EventsOrgMenu extends Menu {
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(V)iew all events");
        options.add("(B)ack");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("V");
        choices.add("B");
        return choices;
    }
}
