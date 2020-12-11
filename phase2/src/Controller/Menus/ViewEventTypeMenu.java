package Controller.Menus;

import java.util.ArrayList;

public class ViewEventTypeMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(S)peaker Event");
        options.add("(V)IP Speaker Event");
        options.add("(E)vent");
        options.add("V(I)P Event");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("S");
        choices.add("V");
        choices.add("I");
        choices.add("E");
        return choices;
    }
}
