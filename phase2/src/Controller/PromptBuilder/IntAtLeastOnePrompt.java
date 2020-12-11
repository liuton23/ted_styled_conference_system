package Controller.PromptBuilder;

import Presenter.Presenter;

import java.util.Scanner;

public class IntAtLeastOnePrompt extends IntPrompt{

    public IntAtLeastOnePrompt(Presenter presenter){
        super(presenter);
    }

    public int intAsk(){
        Scanner input = new Scanner(System.in);
        boolean done = false;
        int x = 0;
        do {
            try {
                x = Integer.parseInt(input.nextLine());
                if (!(1 <= x)) {
                    presenter.printInvalidIntRangeMessage(1);
                } else {
                    done = true;
                }
            } catch (NumberFormatException e) {
                presenter.displayMessages("invalidIntInput");
            }
        } while (!done);
        return x;
    }
}
