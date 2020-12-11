package Controller.PromptBuilder;

import Controller.Menus.*;
import Presenter.Presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Prompt {
    protected Presenter presenter;
    private Menu menu;

    /**
     * Method that sets the presenter for the prompt.
     * @param presenter
     */
    public Prompt(Presenter presenter){
        this.presenter = presenter;
    }

    /**
     * Method that sets the menue for the prompt.
     * @param menu
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * Method that returns the menu options.
     * @return list of menu options.
     */
    public ArrayList<String> getMenuOptions(){
        return menu.getMenuOptions();
    }

    /**
     * Method that returns the menu choices.
     * @return list of menu choices.
     */
    public ArrayList<String> getMenuChoices(){
        return menu.getAllChoices();
    }

    /**
     * Method that checks for invalid inputs.
     * @param choices list of choices.
     * @param chosen input from the user.
     * @return true iff the input is invalid.
     */
    public boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen)){
                return false;
            }
        }
        presenter.displayMessages("invalidInput");
        return true;
    }

    /**
     * Return boolean value from prompt options.
     * @return boolean value
     * @throws IOException
     */
    public boolean booleanAsk() throws IOException {return false;}

    /**
     * Return integer value from prompt options.
     * @return int value
     * @throws IOException
     */
    public int intAsk() throws IOException {return 0;}

    /**
     * Return String value from prompt options.
     * @return String value
     * @throws IOException
     */
    public String ask() throws IOException {return "";}
}
