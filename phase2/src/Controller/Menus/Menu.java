package Controller.Menus;

import java.util.ArrayList;

/**
 * Abstract menu.
 */
public abstract class Menu {

    /**
     * Returns list of options.
     * @return list of options.
     */
    public abstract ArrayList<String> getMenuOptions();

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public abstract ArrayList<String> getAllChoices();
}
