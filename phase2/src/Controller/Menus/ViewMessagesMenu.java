package Controller.Menus;

import java.util.ArrayList;

public class ViewMessagesMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("View (S)ent messages");
        options.add("View (R)eceived messages");
        options.add("View (U)nread messages");
        options.add("View messages (F)rom another user");
        options.add("(B)ack");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("S");
        choices.add("R");
        choices.add("U");
        choices.add("M");
        choices.add("A");
        choices.add("F");
        choices.add("B");
        return choices;
    }
}