package Controller.PromptBuilder;

import Presenter.Presenter;

import java.util.Scanner;

/**
 * Prompt for asking for a minute int
 */
public class IntMinutePrompt extends IntPrompt{
    /**
     * Method that sets the presenter for the prompt.
     * @param presenter for displaying prompt messages.
     */
    public IntMinutePrompt(Presenter presenter){
        super(presenter);
    }

    /**
     * Return integer value from prompt options.
     * @return int value.
     */
    public int intAsk() {
        Scanner input = new Scanner(System.in);
        boolean done = false;
        int x = 0;
        do {
            try {
                x = Integer.parseInt(input.nextLine());
                if (!(0 <= x && x <= 59)) {
                    presenter.printInvalidIntRangeMessage(0, 59);
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
