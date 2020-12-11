package Controller.PromptBuilder;

import Presenter.Presenter;

import java.io.IOException;
import java.util.Scanner;

public class IntDayPrompt extends IntPrompt{
    /**
     * Method that sets the presenter for the prompt.
     * @param presenter
     */
    public IntDayPrompt(Presenter presenter){
        super(presenter);
    }

    /**
     * Return integer value from prompt options.
     * @return int value
     * @throws IOException
     */
    public int intAsk(){
        Scanner input = new Scanner(System.in);
        boolean done = false;
        int x = 0;
        do {
            try {
                x = Integer.parseInt(input.nextLine());
                if (!(1 <= x && x <= 31)) {
                    presenter.printInvalidIntRangeMessage(1, 31);
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
