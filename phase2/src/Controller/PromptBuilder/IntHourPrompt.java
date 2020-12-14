package Controller.PromptBuilder;

import Presenter.Presenter;

import java.util.Scanner;

/**
 * Prompt for asking for an hour int
 */
public class IntHourPrompt extends IntPrompt{
    /**
     * Method that sets the presenter for the prompt.
     * @param presenter for displaying prompt messages.
     */
    public IntHourPrompt(Presenter presenter){
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
                if (!(0 <= x && x <= 23)) {
                    presenter.printInvalidIntRangeMessage(0, 23);
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
