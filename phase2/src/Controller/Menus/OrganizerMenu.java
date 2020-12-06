package Controller.Menus;

import java.util.ArrayList;

public class OrganizerMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(S)chedule Event");
        options.add("(A)dd Room");
        options.add("(C)hange Speaker");
        options.add("(B)ack");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("S");
        choices.add("A");
        choices.add("C");
        choices.add("B");
        return choices;
    }
}
