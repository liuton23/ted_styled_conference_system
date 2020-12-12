package Controller.Menus;

import java.util.ArrayList;
/**
 * Main menu for non-organizers.
 */
public class BasicMenu1 extends Menu{
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
        options.add("(D)ownload Schedule as PDF");
        options.add("(B)ack");
        return options;
    }

    /**
     * Returns list of choices.
     * @return list of choices.
     */
    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("U");
        choices.add("M");
        choices.add("E");
        choices.add("I");
        choices.add("D");
        choices.add("B");
        return choices;
    }
}
