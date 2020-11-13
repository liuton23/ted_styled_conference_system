package Controller;

import java.util.ArrayList;

public class Presenter {

    public void promptList(ArrayList<String> options){
        System.out.println("Please choose one of the following: ");

        for(String option: options){
            System.out.println(option);
        }
    }

    public void prompt(ArrayList<String> options){
        System.out.print("Please input ");
        StringBuilder output = new StringBuilder();
        for(String option: options) {
            output.append(option);
            output.append(", ");
        }
        String str = output.substring(0, output.length() - 3);
        System.out.print(str + ".\n");
    }

    public void invalid_input(){
        System.out.println("The input you entered is invalid.");
    }

}
