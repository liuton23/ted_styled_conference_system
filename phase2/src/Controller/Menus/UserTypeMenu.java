package Controller.Menus;

import java.util.ArrayList;

/**
 * User type menu.
 */
public class UserTypeMenu extends Menu{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(A)ttendee account");
        options.add("(O)rganizer account");
        options.add("(S)peaker account");
        options.add("(V)IP account");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("A");//Attendee
        choices.add("O");//Organizer
        choices.add("S");//Speaker
        choices.add("V");//VIP
        return choices;
    }
}
