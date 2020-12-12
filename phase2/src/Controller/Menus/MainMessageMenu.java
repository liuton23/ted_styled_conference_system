package Controller.Menus;

import java.util.ArrayList;

/**
 * Main messaging menu.
 */
public class MainMessageMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(M)essage users");
        options.add("(V)iew, edit and mark messages");
        options.add("(B)ack");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("M");
        choices.add("V");
        choices.add("B");
        return choices;
    }
}
