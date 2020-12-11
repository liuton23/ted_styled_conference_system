package Controller.Menus;

import java.util.ArrayList;

/**
 * Boolean menu options.
 */
public class BooleanMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Y");
        options.add("T");
        options.add("YES");
        options.add("TRUE");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Y");
        choices.add("T");
        choices.add("YES");
        choices.add("TRUE");
        choices.add("N");
        choices.add("F");
        choices.add("NO");
        choices.add("FALSE");
        return choices;
    }
}
