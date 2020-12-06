package Controller.Menus;

import java.util.ArrayList;

public class ViewEventsMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Sort events by (T)ime");
        options.add("Sort events by (N)ame");
        options.add("Sort events by (S)peaker");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("T");
        choices.add("N");
        choices.add("S");
        return choices;
    }
}
