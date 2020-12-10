package Controller.PromptBuilder;

import Presenter.Presenter;

import java.util.Scanner;

public class IntPrompt extends Prompt{

    public IntPrompt(Presenter presenter){
        super(presenter);
    }

    public int intAsk(){
        Scanner input = new Scanner(System.in);
        boolean done = false;
        int x = 0;
        do {
            presenter.display(getText());
            try {
                x = Integer.parseInt(input.nextLine());
                done = true;
            } catch (NumberFormatException e) {
                presenter.printInvalidIntMessage();
            }
        } while (!done);
        return x;
    }
}
