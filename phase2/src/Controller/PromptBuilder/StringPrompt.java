package Controller.PromptBuilder;

import Presenter.Presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StringPrompt extends Prompt{

    public StringPrompt(Presenter presenter){
        super(presenter);
    }

    public String ask() throws IOException {
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = getMenuChoices();
        ArrayList<String> options = getMenuOptions();
        String chosen;
        do{
            presenter.prompt(options);
            chosen = input.nextLine().toUpperCase();
            if (chosen.equals(presenter.getExit())){
                throw new IOException();
            }
        }while(invalidInput(choices, chosen));
        return chosen;
    }
}
