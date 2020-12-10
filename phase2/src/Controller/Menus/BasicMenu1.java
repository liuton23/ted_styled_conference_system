package Controller.Menus;

import java.util.ArrayList;

public class BasicMenu1 extends Menu{

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
