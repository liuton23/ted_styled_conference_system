package Controller.Menus;

import java.util.ArrayList;

public class LoginMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(L)ogin");
        options.add("(R)egister");
        options.add("Reset (P)assword");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("L");
        choices.add("R");
        choices.add("P");
        return choices;
    }
}
