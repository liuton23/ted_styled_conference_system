package Controller.Menus;

import java.util.ArrayList;

public class BasicMenu2 extends BasicMenu1{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(U)pdate Account Email");
        options.add("(M)essages");
        options.add("(E)vents");
        options.add("(I)tinerary");
        options.add("(S)chedule events");
        options.add("(C)reate user account");
        options.add("(B)ack");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = super.getAllChoices();
        choices.add("S");
        choices.add(("B"));
        return choices;
    }
}
