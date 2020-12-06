package Controller.Menus;

import java.util.ArrayList;

public class BasicMenu2 extends BasicMenu1{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = super.getMenuOptions();
        options.add("(S)chedule events");
        options.add("(C)reate user account");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("M");
        choices.add("E");
        choices.add("I");
        choices.add("C");
        choices.add("S");
        choices.add(("B"));
        return choices;
    }
}
