package Controller;

import Presenter.Presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PromptController {
    protected Presenter presenter;

    public PromptController(Presenter presenter){
        this.presenter = presenter;
    }
    /**
     * Makes sure user enters an int as input
     * @return the int the user entered
     */
    protected int getIntInput() {
        Scanner input = new Scanner(System.in);
        boolean done = false;
        int in = 0;
        do {
            try {
                in = Integer.parseInt(input.nextLine());
                done = true;
            } catch (NumberFormatException e) {
                presenter.printInvalidIntMessage();
            }
        } while (!done);
        return in;
    }

    /**
     * Makes sure user enters an int between start and end (inclusive)
     * @param start start of range
     * @param end end of range
     * @return the inputted int
     */
    protected int getIntInputInRange(int start, int end) {
        boolean done = false;
        int in = 0;
        do {
            in = getIntInput();
            if (!(start <= in && in <= end)) {
                presenter.printInvalidIntRangeMessage(start, end);
            } else {
                done = true;
            }
        } while (!done);
        return in;
    }

    /**
     * Makes sure user enters an int greater than or equal to start
     * @param start start of range
     * @return the inputted int
     */
    protected int getIntInputGreaterThanEqualTo(int start) {
        boolean done = false;
        int in = 0;
        do {
            in = getIntInput();
            if (!(start <= in)) {
                presenter.printInvalidIntRangeMessage(start);
            } else {
                done = true;
            }
        } while (!done);
        return in;
    }
    /**
     * Asks the user yes or no and receives input.
     * @return  true if the user inputs Y/YES/T/True and false if the user inputs N/NO/F/FALSE.
     */
    protected boolean askBooleanInput(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = new ArrayList<>();
        choices.addAll(presenter.chooseMenuOptions(14));
        choices.addAll(presenter.chooseMenuOptions(15));
        String chosen;
        do{
            presenter.display("Yes or No?");
            chosen = input.next().toUpperCase();
        }while(invalidInput(choices, chosen));
        return presenter.chooseMenuOptions(14).contains(chosen);
    }

    /**
     * Checks if <code>chosen</code> is a valid input in <code>choices</code>. If input is "EXIT", <code>exit</code>
     * will be called.
     * @param choices list of choices that are valid. Must be uppercase.
     * @param chosen input the user entered.
     * @return true iff <code>chosen</code> is an invalid input. False if it is valid.
     */
    protected boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen)){
                return false;
            }else if(chosen.equals(presenter.getExit())){
                break;//exit();
            }
        }
        presenter.invalidInput();
        return true;
    }

    /**
     * Asks the user for input and checks its validity.
     * @param i the menu id.
     * @return Uppercase String that is valid input from user.
     */
    protected String askMenuInput(int i){
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = presenter.chooseMenuOptions(i);
        String chosen;
        do{
            chooseMenuPrompt(i);
            chosen = input.nextLine().toUpperCase();
        }while(invalidInput(choices, chosen));
        return chosen;
    }



    /**
     * Displays a menu given <code>menu_id</code>.
     * @param menu_id determines which menu is needed.
     */
    protected void chooseMenuPrompt(int menu_id){
        switch (menu_id){
            case 1:
                presenter.loginMenu();
                break;
            case 2:
                presenter.basicMenu1();
                break;
            case 3:
                presenter.basicMenu2();
                break;
            case 4:
                presenter.organizerMenu();
                break;
            case 5:
                presenter.mainMessageMenu();
                break;
            case 6:
                presenter.sendMessageMenuAtt();
                break;
            case 7:
                presenter.viewMessageMenu();
                break;
            case 8:
                presenter.eventMenu();
                break;
            case 9:
                presenter.viewEventsMenu();
                break;
            case 10:
                presenter.sendOrgMessageOrg();
                break;
            case 11:
                presenter.sendMessageMenuSpeaker();
                break;
            case 12:
                presenter.wishToSendMoreEvent();
                break;
        }
    }
}
