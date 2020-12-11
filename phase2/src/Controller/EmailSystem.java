package Controller;

import Gateway.Gateway;
import Presenter.Presenter;
import UseCases.UserManager;

import javax.mail.MessagingException;
import java.util.Random;
import java.util.Scanner;

/**
 * Manages user input for emailing methods.
 */
public class EmailSystem {

    Presenter presenter = new Presenter();
    Gateway gateway;
    UserManager userManager;

    /**
     * Constructs an <code>EmailSystem</code>.
     * @param gateway gateway that can send emails.
     * @param userManager manages <code>User</code> email.
     */
    public EmailSystem(Gateway gateway, UserManager userManager){
        this.gateway = gateway;
        this.userManager = userManager;
    }

    /**
     * Takes <code>username</code> to add their email(if it is valid: only emails ending with .com, .ca, .co.uk are supported)
     * @param username valid username of a <code>User</code> in <code>userManager</code>
     */
    public void addEmail(String username){
        Scanner input = new Scanner(System.in);
        boolean invInput;
        do {
            invInput = true;
            presenter.displayMessages("enterEmail");
            String email = input.nextLine();
            if(email.toUpperCase().equals("BACK") || email.toUpperCase().equals("B")){
                return;
            }else if(email.isEmpty()){
                userManager.setUserEmail(username, email);
            }
            if(!email.matches("[\\w.]+@\\w+(.com)|(.ca)|(.co.uk)}")){
                presenter.invalidInput();
            }else if(userManager.checkValidEmail(username, email) == 0){
                presenter.displayMessages("yourEmail");
                return;
            }else if(userManager.checkValidEmail(username, email) == -1){
                presenter.displayMessages("takenEmail");
            }else{
                userManager.setUserEmail(username, email);
                invInput = false;
            }
        }while(invInput);
        presenter.displayMessages("sendingEmail");
        String subject = "CSC207 Tech Conference!";
        String message = "Your email is now linked with CSC207 Tech Conference account " + username + " \n If you did " +
                "not do this or would like to unsubscribe to emails, login to CSC 207 Tech Conference go to Messages " +
                "-> Add email and enter nothing.";
        try {
            gateway.sendEmail(userManager.getUserEmail(username), subject, message);
            presenter.displayMessages("sentEmail");
        }catch (Exception e){
            presenter.displayMessages("emailError");
        }
    }

    /**
     * Sends an email to the <code>email</code> of <code>User</code> with username <code>username</code> with a password
     * reset code.
     * @param username valid username in <code>userManager</code>.
     * @return code that was sent to the email.
     */
    public String passwordReset(String username){
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        presenter.displayMessages("sendingEmail");
        String subject = "CSC207 Password Reset Request.";
        String message = "The account associated with this email: " + username + " has requested a password change." +
                "If you recognize this account then please enter the code below into the program, otherwise please"+
                " ignore this message." + "\n\n\n<b>" + code + "</b>";
        try {
            gateway.sendEmail(userManager.getUserEmail(username), subject, message);
            presenter.displayMessages("sentEmail");
        } catch (MessagingException e) {
            presenter.displayMessages("emailError");
        }
        return Integer.toString(code);
    }

}
