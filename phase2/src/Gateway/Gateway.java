package Gateway;

import Presenter.Presenter;
import UseCases.UserManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//mail imports
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.*;

/**
 * Manages file saving and persistence of information. Also manages sending emails.
 */
public class Gateway {

    private String filepath;
    private File file;
    private Presenter presenter;
    private UserManager userManager;
    Session session;
    String email;

    /**
     * Constructs a gateway path with <code>filepath</code>.
     * @param filepath  the name of the file to save and write to. Could also be changed to save file to a different
     *                  directory.
     */
    public Gateway(String filepath){
        this.filepath = filepath;
        this.file = new File(filepath);
        initEmail();
    }

    /**
     * Writes the objects to a file.
     * @param serializing list of objects to be saved.
     * @throws IOException error in writing to file
     */
    public void writeToFile(List<Serializable> serializing) throws IOException {
        OutputStream file = new FileOutputStream(filepath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput objOut = new ObjectOutputStream(buffer);

        for(Object obj: serializing){
            objOut.writeObject(obj);
        }

        objOut.close();
    }

    /**
     * Reads from the save file.
     * @param length the number of objects to load.
     * @return the list of the loaded objects.
     * @throws IOException error in reading file or file not found.
     * @throws ClassNotFoundException class does not exist.
     */
    public ArrayList<Serializable> readFromFile(int length) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(filepath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput objInput = new ObjectInputStream(buffer);
        ArrayList<Serializable> serialized = new ArrayList<>();

        for(int i = 0; i < length; i++){
            Object adding = objInput.readObject();
            if(adding != null) {
                serialized.add((Serializable) adding);
            }
        }

        return serialized;
    }

    private void initEmail(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        final String myEmail = "csc207Group0757@gmail.com";
        this.email = myEmail;
        final String myPassword = "T2uring07";
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        };
        this.session = Session.getDefaultInstance(properties, auth);
    }

    public void sendEmail(String username, String subject, String content) throws MessagingException {
        String receiverEmail = userManager.usernameToUserObject(username).get().getEmail();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=utf-8");
        Transport.send(message);
    }

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
                break;
            }else if(userManager.checkValidEmail(username, email) == -1){
                presenter.displayMessages("takenEmail");
            }else{
                userManager.setUserEmail(username, email);
                invInput = false;
            }
        }while(invInput);
        String subject = "CSC207 Tech Conference!";
        String message = "Your email is now linked with CSC207 Tech Conference account" + username + "\n If you did " +
                "not do this or would like to unsubscribe to emails, login to CSC 207 Tech Conference go to Messages " +
                "-> Add email and enter nothing.";
        try {
            sendEmail(username, subject, message);
        }catch (Exception e){
            presenter.invalidInput();
        }
    }

    public String passwordReset(String username){
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        String subject = "CSC207 Password Reset Request.";
        String message = "The account associated with this email:" + username + "has requested a password change." +
                "If you recognize this account then please enter the code below into the program, otherwise please"+
                " ignore this message." + "\n\n\n<b>" + code + "</b>";
        try {
            sendEmail(username, subject, message);
        } catch (MessagingException e) {
            System.out.println("something is wrong");
        }
        return Integer.toString(code);
    }

    public void setUserManager(UserManager userManager){ this.userManager = userManager; }
}
