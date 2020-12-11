package Controller.Menus;

import java.util.ArrayList;

/**
 * Login menu
 */
public class LoginMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(L)ogin");
        options.add("(R)egister");
        options.add("Reset (P)assword");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("L");
        choices.add("R");
        choices.add("P");
        return choices;
    }
}
