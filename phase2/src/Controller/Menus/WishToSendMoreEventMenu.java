package Controller.Menus;

import java.util.ArrayList;

/**
 * Messaging multiple users menu.
 */
public class WishToSendMoreEventMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(S)end to one more event");
        options.add("(C)ontinue to message");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("S");
        choices.add("C");
        return choices;
    }
}
