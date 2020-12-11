package Controller.Menus;

import java.util.ArrayList;

/**
 * Event type menu.
 */
public class ViewEventTypeMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(S)peaker Event");
        options.add("(V)IP Speaker Event");
        options.add("(E)vent");
        options.add("V(I)P Event");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("S");
        choices.add("V");
        choices.add("I");
        choices.add("E");
        return choices;
    }
}
