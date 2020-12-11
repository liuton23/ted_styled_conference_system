package Controller.Menus;

import java.util.ArrayList;

/**
 * Main menu for organizers.
 */
public class BasicMenu2 extends BasicMenu1{
    /**
     * Returns list of options.
     * @return list of options.
     */
    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(U)pdate Account Email");
        options.add("(M)essages");
        options.add("(E)vents");
        options.add("(I)tinerary");
        options.add("(S)chedule events");
        options.add("(C)ancel events");
        options.add("create user (A)ccount");
        options.add("(D)ownload Schedule as PDF");
        options.add("(B)ack");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = super.getAllChoices();
        choices.add("S");
        choices.add("C");
        choices.add("A");
        return choices;
    }
}
