package Controller.Menus;

import java.util.ArrayList;

public class EventsOrgMenu extends Menu {

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(V)iew all events");
        options.add("(B)ack");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("V");
        choices.add("B");
        return choices;
    }
}
