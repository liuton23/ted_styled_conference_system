package Controller.PromptBuilder;

import Presenter.Presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BooleanPrompt extends Prompt {

    public BooleanPrompt(Presenter presenter){
        super(presenter);
    }

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
