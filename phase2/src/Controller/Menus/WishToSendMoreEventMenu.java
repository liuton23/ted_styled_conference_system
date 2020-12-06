package Controller.Menus;

import java.util.ArrayList;

public class WishToSendMoreEventMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(S)end to one more event");
        options.add("(C)ontinue to message");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("S");
        choices.add("C");
        return choices;
    }
}
