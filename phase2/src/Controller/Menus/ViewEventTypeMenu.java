package Controller.Menus;

import java.util.ArrayList;

public class ViewEventTypeMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Speaker Event");
        options.add("VIP Speaker Event");
        options.add("Event");
        options.add("VIP Event");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("SPEAKER EVENT");
        choices.add("VIP SPEAKER EVENT");
        choices.add("VIP EVENT");
        choices.add("EVENT");
        return choices;
    }
}
