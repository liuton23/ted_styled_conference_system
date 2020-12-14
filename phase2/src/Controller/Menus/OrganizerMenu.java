package Controller.Menus;

import java.util.ArrayList;

/**
 * Organizer scheduling menu.
 */
public class OrganizerMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(S)chedule Event");
        options.add("(A)dd Room");
        options.add("(C)hange Speaker");
        options.add("(B)ack");
        options.add("(Ch)ange Event Capacity");
        options.add("(Ad)d Speaker");
        options.add("(Re)move Speaker");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("S");
        choices.add("A");
        choices.add("C");
        choices.add("B");
        choices.add("CH");
        choices.add("AD");
        choices.add("RE");
        return choices;
    }
}
