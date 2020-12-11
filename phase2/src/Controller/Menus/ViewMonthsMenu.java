package Controller.Menus;

import java.util.ArrayList;

/**
 * Months menu.
 */
public class ViewMonthsMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("JANUARY");
        options.add("FEBRUARY");
        options.add("MARCH");
        options.add("APRIL");
        options.add("MAY");
        options.add("JUNE");
        options.add("JULY");
        options.add("AUGUST");
        options.add("SEPTEMBER");
        options.add("OCTOBER");
        options.add("NOVEMBER");
        options.add("DECEMBER");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("JANUARY");
        choices.add("FEBRUARY");
        choices.add("MARCH");
        choices.add("APRIL");
        choices.add("MAY");
        choices.add("JUNE");
        choices.add("JULY");
        choices.add("AUGUST");
        choices.add("SEPTEMBER");
        choices.add("OCTOBER");
        choices.add("NOVEMBER");
        choices.add("DECEMBER");
        return choices;
    }
}
