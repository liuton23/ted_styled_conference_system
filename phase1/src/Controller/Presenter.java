package Controller;

import java.util.ArrayList;

public class Presenter {

    public void welcomeMessage(){
        System.out.println("Welcome to the 207th Tech Conference!");
        System.out.println("You can enter 'E' to exit anytime.");
    }

    public void promptList(ArrayList<String> options){
        System.out.println("Please choose one of the following: ");

        for(String option: options){
            System.out.println(option);
        }
    }

    public void prompt(ArrayList<String> options){
        System.out.println("Would you like to ");
        for(String option: options) {
            System.out.println(option);
        }
    }
    public void displayMessages(){

    }
    public void displaySchedule(){

    }

    public void invalidInput(){
        System.out.println("The input you entered is invalid.");
        System.out.println("Please try again.");
    }

    public void printUsernameMessage(){
        System.out.println("Enter your username:");
    }

    public void printPasswordMessage() {
        System.out.println("Enter your password");
    }

    public void printLoginSucceedMessage(){
        System.out.println("You have successfully logged in.");
    }

    public void printLoginFailMessage(){
        System.out.println("Your username or password is incorrect.");
    }

    public void printRegisterSucceedMessage(){
        System.out.println("You have successfully registered. Please login to continue.");
    }

    public void printRegisterFailMessage(){
        System.out.println("Your username or password is invalid.");
        System.out.println("Please check that username or password is non-empty and username is not already taken.");
    }


}
