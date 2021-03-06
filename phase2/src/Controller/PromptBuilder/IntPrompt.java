package Controller.PromptBuilder;

import Presenter.Presenter;

import java.util.Scanner;

/**
 * Prompt for asking for an int (no restrictions)
 */
public class IntPrompt extends Prompt{
    /**
     * Method that sets the presenter for the prompt.
     * @param presenter for displaying prompt messages.
     */
    public IntPrompt(Presenter presenter){
        super(presenter);
    }

    /**
     * Return integer value from prompt options.
     * @return int value.
     */
    public int intAsk(){
        Scanner input = new Scanner(System.in);
        boolean done = false;
        int x = 0;
        do {
            try {
                x = Integer.parseInt(input.nextLine());
                done = true;
            } catch (NumberFormatException e) {
                presenter.displayMessages("invalidIntInput");
            }
        } while (!done);
        return x;
    }
}
