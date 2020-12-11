package Controller.PromptBuilder;

import Presenter.Presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BooleanPrompt extends Prompt {
    /**
     * Method that sets the presenter for the prompt.
     * @param presenter
     */
    public BooleanPrompt(Presenter presenter){
        super(presenter);
    }

    /**
     * Return boolean value from prompt options.
     * @return boolean value
     * @throws IOException
     */
    public boolean booleanAsk() throws IOException {
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = getMenuChoices();
        ArrayList<String> options = getMenuOptions();
        String chosen;
        do{
            chosen = input.next().toUpperCase();
            if (chosen.equals(presenter.getExit())){
                throw new IOException();
            }
        }while(invalidInput(choices, chosen));
        return options.contains(chosen);
    }
}
